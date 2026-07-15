package com.siberanka.twicosmetics.menu;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PurchaseDataTest {
    @Test
    void purchaseSessionCanOnlyBeConsumedOnce() throws InterruptedException {
        PurchaseData data = new PurchaseData();
        AtomicInteger winners = new AtomicInteger();
        int attempts = 32;
        CountDownLatch done = new CountDownLatch(attempts);
        var executor = Executors.newFixedThreadPool(8);
        try {
            for (int i = 0; i < attempts; i++) {
                executor.execute(() -> {
                    if (data.tryBeginPurchase()) winners.incrementAndGet();
                    done.countDown();
                });
            }
            assertTrue(done.await(5, TimeUnit.SECONDS));
        } finally {
            executor.shutdownNow();
        }
        assertEquals(1, winners.get());
    }
}
