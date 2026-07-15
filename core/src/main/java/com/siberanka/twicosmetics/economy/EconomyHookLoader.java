package com.siberanka.twicosmetics.economy;

import com.siberanka.twicosmetics.TwiCosmetics;

@FunctionalInterface
public interface EconomyHookLoader {
    public EconomyHook load(TwiCosmetics ultraCosmetics, String currency) throws ReflectiveOperationException;
}
