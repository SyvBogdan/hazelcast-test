package com.test.map;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;
import com.hazelcast.core.IMap;
import com.hazelcast.map.listener.MapListener;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

public abstract class AbstractHazelCastMapService<T> implements HazelCastMapService<T> {

    private final HazelcastInstance hazelcastInstance;

    private final ConcurrentMap<String, IMap<UUID, T>> availableMaps = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, ILock> locks = new ConcurrentHashMap<>();

    public AbstractHazelCastMapService(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    public UUID addCash(final T value, final String destination) {

        final UUID cashId = UUID.randomUUID();
        getMap(destination).put(cashId, value);
        return cashId;
    }

    public void changeCash(final UUID key, final T newValue, final String mapName) {

        final IMap<UUID, T> map = getMap(mapName);
        Optional.ofNullable(getMap(mapName).get(key)).
                ifPresent(value -> map.replace(key, newValue));
    }

    public void syncEditCash(final UUID key, final String mapName, final Consumer<T> updater) {

        final ILock lock = getLock(mapName);
        lock.lock();
        try {
            final T value = getMap(mapName).get(key);
            Optional.ofNullable(value).ifPresent(updater);

        } finally {
            lock.unlock();
        }
    }

    public void removeCash(final UUID key, final String mapName) {
        getMap(mapName).remove(key);
    }

    private ILock getLock(final String mapName) {

        return Optional.ofNullable(locks.get(mapName))
                .orElseGet(() -> {
                    final ILock lock = hazelcastInstance.getLock(mapName);
                    locks.put(mapName, lock);
                    return lock;
                });
    }

    private IMap<UUID, T> getMap(final String mapName) {
        return Optional.ofNullable(availableMaps.get(mapName))
                .orElseGet(() -> {
                    final IMap<UUID, T> map = hazelcastInstance.getMap(mapName);
                    availableMaps.put(mapName, map);
                    return map;
                });

    }

    public AbstractHazelCastMapService<T> addListenerToMap(final String mapName, final MapListener listener) {
        getMap(mapName).addEntryListener(listener, true);
        return this;
    }
}
