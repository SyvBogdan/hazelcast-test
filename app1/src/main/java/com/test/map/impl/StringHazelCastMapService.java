package com.test.map.impl;

import com.hazelcast.core.HazelcastInstance;
import com.test.map.AbstractHazelCastMapService;

public class StringHazelCastMapService extends AbstractHazelCastMapService<String> {

    public StringHazelCastMapService(HazelcastInstance hazelcastInstance) {
        super(hazelcastInstance);
    }

}
