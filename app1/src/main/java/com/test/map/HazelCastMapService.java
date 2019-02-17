package com.test.map;

import java.util.UUID;
import java.util.function.Consumer;

public interface HazelCastMapService<T> {

    UUID addCash(final T value, final String destination);

    void changeCash(final UUID key, final T newValue, final String mapName);

    void syncEditCash(final UUID key, final String mapName, final Consumer<T> updater);

    void removeCash(final UUID key, final String mapName);
}
