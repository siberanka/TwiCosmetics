package com.siberanka.twicosmetics;

/**
 * Classes that implement this and register with {@link TwiCosmetics#registerAddon(UCAddon)} will be notified when UC is reloaded.
 * Addons will not be reloaded when first registering, only when UC is reloaded using `/uc reload`.
 * In cases where UC is reloaded externally (i.e. PlugMan,) all bets are off.
 */
public interface UCAddon {
    public void reload(TwiCosmetics ultraCosmetics);
}
