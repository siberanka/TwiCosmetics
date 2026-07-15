package com.siberanka.twicosmetics.menu;

import org.bukkit.inventory.ItemStack;

import java.util.function.BooleanSupplier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sacha on 04/04/2017.
 */
public class PurchaseData {

    private int price;
    private BooleanSupplier canPurchase;
    private Runnable onPurchase;
    private Runnable onCancel;
    private ItemStack showcaseItem;
    private final AtomicBoolean processing = new AtomicBoolean();
    private boolean finalPrice;

    public int getBasePrice() {
        return price;
    }

    public ItemStack getShowcaseItem() {
        return showcaseItem;
    }

    public boolean canPurchase() {
        if (canPurchase != null) return canPurchase.getAsBoolean();
        return true;
    }

    /**
     * Atomically consumes this purchase session. A confirmation inventory is single-use;
     * duplicate click packets must never start a second economy transaction.
     */
    public boolean tryBeginPurchase() {
        if (!processing.compareAndSet(false, true)) return false;
        if (canPurchase()) return true;
        processing.set(false);
        return false;
    }

    public void runOnPurchase() {
        onPurchase.run();
    }

    public void runOnCancel() {
        if (onCancel != null) onCancel.run();
    }

    public void setBasePrice(int price) {
        this.price = price;
    }

    public boolean isFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(boolean finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void setCanPurchase(BooleanSupplier canPurchase) {
        this.canPurchase = canPurchase;
    }

    public void setOnPurchase(Runnable onPurchase) {
        this.onPurchase = onPurchase;
    }

    public void setOnCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }

    public void setShowcaseItem(ItemStack showcaseItem) {
        this.showcaseItem = showcaseItem;
    }

}
