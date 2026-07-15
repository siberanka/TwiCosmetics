package com.siberanka.twicosmetics.util;

import com.siberanka.twicosmetics.TwiCosmetics;
import com.siberanka.twicosmetics.TwiCosmeticsData;
import com.siberanka.twicosmetics.config.SettingsManager;
import com.siberanka.twicosmetics.task.UltraTask;
import com.siberanka.twicosmetics.util.SmartLogger.LogLevel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

/**
 * Manages update checking.
 * <p>
 * Package: com.siberanka.twicosmetics.util
 * Created by: sachalewin
 * Date: 5/08/16
 * Project: TwiCosmetics
 */
public class UpdateManager extends UltraTask {
    private static final String RESOURCE_URL = "https://api.github.com/repos/siberanka/TwiCosmetics/";
    private final TwiCosmetics ultraCosmetics;
    /**
     * Current UC version.
     */
    private final Version currentVersion;
    private final String userAgent;

    private boolean apiGone = false;

    /**
     * Best available version available on modrinth
     */
    private Version latestVersion;
    private String expectedHash;
    private String hashAlgorithm;
    private String updateUrl;
    private String updateFilename;

    /**
     * Whether the plugin is outdated or not.
     */
    private boolean outdated = false;

    private String status = "no update check performed";

    public UpdateManager(TwiCosmetics ultraCosmetics) {
        this.ultraCosmetics = ultraCosmetics;
        Reader reader = TwiCosmeticsData.get().getPlugin().getFileReader("build_info.yml");
        YamlConfiguration buildInfo = YamlConfiguration.loadConfiguration(reader);
        String gitHash = buildInfo.getString("git-hash");
        this.currentVersion = new Version(ultraCosmetics.getDescription().getVersion(), gitHash);
        // email broken up to hopefully confuse dumb scrapers
        this.userAgent = "siberanka/TwiCosmetics/" + currentVersion;

    }

    /**
     * Checks for new update.
     */
    @Override
    public void run() {
        status = fetchLatestVersion();
        ultraCosmetics.getSmartLogger().write(status);
        if (outdated && SettingsManager.getConfig().getBoolean("Auto-Update")) {
            update();
        }
    }

    @Override
    public void schedule() {
        task = getScheduler().runLaterAsync(this::run, 1);
    }

    public boolean update() {
        if (!download()) {
            ultraCosmetics.getSmartLogger().write("Failed to download update");
            return false;
        }
        outdated = false;
        status = "Successfully downloaded new version, restart server to apply update.";
        ultraCosmetics.getSmartLogger().write(status);
        return true;
    }

    /**
     * Get the latest version info from Modrinth
     *
     * @return Information about the update status (error, update available, etc.)
     */
    private String fetchLatestVersion() {
        JsonElement element = apiRequest("releases/latest", Map.of());
        if (element == null || !element.isJsonObject()) {
            return "Failed to check for updates: invalid or no response from API";
        }
        JsonObject release = element.getAsJsonObject();
        String versionString = release.get("tag_name").getAsString();
        if (versionString == null || versionString.isBlank()) {
            return "Cannot update, unknown version";
        }
        if (versionString.startsWith("v")) versionString = versionString.substring(1);
        latestVersion = new Version(versionString);
        if (currentVersion.compareTo(latestVersion) == 0) {
            return "You are running the latest TwiCosmetics release.";
        }
        if (currentVersion.compareTo(latestVersion) > 0) {
            return "You are running a version newer than the latest TwiCosmetics release.";
        }

        updateFilename = null;
        JsonElement el = release.get("assets");
        if (el != null && el.isJsonArray()) {
            JsonArray files = el.getAsJsonArray();
            for (JsonElement fileEl : files) {
                JsonObject file = fileEl.getAsJsonObject();
                String name = file.get("name").getAsString();
                JsonElement digestElement = file.get("digest");
                if (name.startsWith("TwiCosmetics-") && name.endsWith(".jar") && digestElement != null
                        && !digestElement.isJsonNull()) {
                    String[] digest = digestElement.getAsString().split(":", 2);
                    if (digest.length != 2 || !digest[0].equalsIgnoreCase("sha256")) continue;
                    updateFilename = name;
                    updateUrl = file.get("browser_download_url").getAsString();
                    hashAlgorithm = "SHA-256";
                    expectedHash = digest[1];
                    break;
                }
            }
        }
        if (updateFilename == null) {
            return "Cannot update: no SHA-256 verified TwiCosmetics JAR is attached to the latest release.";
        }

        outdated = true;
        return "New TwiCosmetics release available: " + latestVersion.versionWithClassifier();
    }

    public Version getLatestVersion() {
        return latestVersion;
    }

    public Version getCurrentVersion() {
        return currentVersion;
    }

    private JsonElement apiRequest(String path, Map<String, String> queryParams) {
        if (apiGone) {
            return null;
        }
        StringBuilder builder = new StringBuilder(path);
        boolean first = true;
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            builder.append(first ? '?' : '&');
            first = false;
            builder.append(entry.getKey()).append('=')
                    .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        URL url = null;
        try {
            url = new URL(RESOURCE_URL + builder);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", userAgent);
            if (connection.getResponseCode() == 410) {
                // no further contact with API if it returns 410
                apiGone = true;
                return null;
            }

            InputStream inputStream = connection.getInputStream();
            try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                // Earlier versions of GSON don't have the static
                // parsing methods present in recent versions.
                @SuppressWarnings("deprecation")
                JsonElement response = new JsonParser().parse(reader);
                return response;
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private static byte[] hexStringToByteArray(String s) {
        // https://stackoverflow.com/a/19119453
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * Downloads the file
     * <p>
     * Adapted from <a href="https://github.com/Stipess1/AutoUpdater/blob/master/src/main/java/com/stipess1/updater/Updater.java">AutoUpdater</a>
     */
    private boolean download() {
        URL url;
        MessageDigest hash;
        try {
            url = new URL(updateUrl);
            hash = MessageDigest.getInstance(hashAlgorithm);
        } catch (MalformedURLException | NoSuchAlgorithmException e) {
            // should not happen
            throw new RuntimeException(e);
        }
        File outputFile = new File(Bukkit.getUpdateFolderFile(), updateFilename);
        File temporaryFile = new File(Bukkit.getUpdateFolderFile(), updateFilename + ".part");
        outputFile.getParentFile().mkdirs();

        long total = 0;
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10_000);
            connection.setReadTimeout(30_000);
            connection.setInstanceFollowRedirects(true);
            connection.addRequestProperty("User-Agent", userAgent);
            try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
                 FileOutputStream fout = new FileOutputStream(temporaryFile)) {

                final byte[] data = new byte[8192];
                int count;
                while ((count = in.read(data)) != -1) {
                    total += count;
                    if (total > 50L * 1024 * 1024) throw new IOException("Update exceeds 50 MiB limit");
                    fout.write(data, 0, count);
                    hash.update(data, 0, count);
                }
            }
        } catch (Exception e) {
            ultraCosmetics.getLogger().log(Level.SEVERE, null, e);
            temporaryFile.delete();
            return false;
        }
        if (expectedHash != null) {
            if (!Arrays.equals(hexStringToByteArray(expectedHash), hash.digest())) {
                ultraCosmetics.getSmartLogger().write(LogLevel.ERROR, "Hash check failed, discarding update!");
                temporaryFile.delete();
                return false;
            }
        }
        try {
            Files.move(temporaryFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException exception) {
            ultraCosmetics.getLogger().log(Level.SEVERE, "Failed to install verified update", exception);
            temporaryFile.delete();
            return false;
        }
    }

    public boolean isOutdated() {
        return outdated;
    }

    public String getStatus() {
        return status;
    }
}
