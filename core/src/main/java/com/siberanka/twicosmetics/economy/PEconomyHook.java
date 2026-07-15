package com.siberanka.twicosmetics.economy;

import com.siberanka.twicosmetics.TwiCosmetics;
import org.bukkit.entity.Player;
import ru.soknight.peconomy.api.PEconomyAPI;
import ru.soknight.peconomy.configuration.CurrencyInstance;
import ru.soknight.peconomy.database.model.WalletModel;

import java.util.Iterator;

public class PEconomyHook implements EconomyHook {
    private final TwiCosmetics ultraCosmetics;
    private final PEconomyAPI api = PEconomyAPI.get();
    private final CurrencyInstance currency;

    public PEconomyHook(TwiCosmetics ultraCosmetics, String currencyName) {
        this.ultraCosmetics = ultraCosmetics;
        if (currencyName == null) {
            Iterator<CurrencyInstance> iter = api.getLoadedCurrencies().iterator();
            if (!iter.hasNext()) {
                throw new IllegalStateException("No currencies available from PEconomy");
            }
            currency = iter.next();
        } else {
            currency = api.getCurrencyByID(currencyName);
            if (currency == null) {
                throw new IllegalArgumentException("Couldn't find PEconomy currency '" + currencyName + "'");
            }
        }
    }

    @Override
    public void withdraw(Player player, int amount, Runnable onSuccess, Runnable onFailure) {
        String playerName = player.getName();
        ultraCosmetics.getScheduler().runAsync((outer) -> {
            WalletModel wallet = api.getWallet(playerName);
            if (wallet.hasAmount(currency.getId(), amount)) {
                wallet.takeAmount(currency.getId(), amount);
                api.updateWallet(wallet);
                ultraCosmetics.getScheduler().runNextTick((inner) -> onSuccess.run());
            } else {
                ultraCosmetics.getScheduler().runNextTick((inner) -> onFailure.run());
            }
        });
    }

    @Override
    public void deposit(Player player, int amount) {
        String playerName = player.getName();
        ultraCosmetics.getScheduler().runAsync((task) -> api.updateWallet(api.addAmount(playerName, currency.getId(), amount)));
    }

    @Override
    public String getName() {
        return "PEconomy:" + currency.getId();
    }
}
