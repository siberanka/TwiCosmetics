package com.siberanka.twicosmetics.player.profile;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfileSaveServiceTest {

    @Test
    void closeWaitsForAcceptedSaveAndRejectsLateWrites() throws Exception {
        CountDownLatch writerEntered = new CountDownLatch(1);
        CountDownLatch releaseWriter = new CountDownLatch(1);
        List<Integer> savedKeys = new CopyOnWriteArrayList<>();
        ProfileSaveService service = new ProfileSaveService(null, data -> {
            writerEntered.countDown();
            try {
                assertTrue(releaseWriter.await(5, TimeUnit.SECONDS));
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(exception);
            }
            savedKeys.add(data.getKeys());
        });

        PlayerData data = new PlayerData(UUID.randomUUID());
        data.setKeys(7);
        assertTrue(service.queue(data));
        assertTrue(writerEntered.await(5, TimeUnit.SECONDS));
        assertFalse(service.flush(Duration.ofMillis(25)));

        Thread closer = new Thread(service::close);
        closer.start();
        releaseWriter.countDown();
        closer.join(5_000);

        assertFalse(closer.isAlive());
        assertEquals(List.of(7), savedKeys);
        assertFalse(service.queue(data));
    }

    @Test
    void coalescesPendingSnapshotsToNewestPlayerState() throws Exception {
        CountDownLatch firstWriterEntered = new CountDownLatch(1);
        CountDownLatch releaseFirstWriter = new CountDownLatch(1);
        List<Integer> savedKeys = new CopyOnWriteArrayList<>();
        ProfileSaveService service = new ProfileSaveService(null, data -> {
            if (savedKeys.isEmpty()) {
                firstWriterEntered.countDown();
                try {
                    assertTrue(releaseFirstWriter.await(5, TimeUnit.SECONDS));
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException(exception);
                }
            }
            savedKeys.add(data.getKeys());
        });

        PlayerData data = new PlayerData(UUID.randomUUID());
        data.setKeys(1);
        assertTrue(service.queue(data));
        assertTrue(firstWriterEntered.await(5, TimeUnit.SECONDS));

        data.setKeys(2);
        assertTrue(service.queue(data));
        data.setKeys(3);
        assertTrue(service.queue(data));
        releaseFirstWriter.countDown();

        assertTrue(service.flush(Duration.ofSeconds(5)));
        service.close();
        assertEquals(List.of(1, 3), savedKeys);
    }
}
