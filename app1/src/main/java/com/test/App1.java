package com.test;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.MapEvent;
import com.hazelcast.map.listener.MapListener;
import com.test.map.HazelCastMapService;
import com.test.map.impl.StringHazelCastMapService;
import node.ServerNode;

import java.util.UUID;

public class App1 {

    public static void main(String[] args) {
        final ServerNode serverNode = new ServerNode();

        final String mapName = "testMap";

        final HazelCastMapService<String> hazelCastMapService = new StringHazelCastMapService(serverNode.getHzInstance())
                .addListenerToMap(mapName, new EntryListener() {
                    @Override
                    public void mapEvicted(MapEvent event) {
                    }

                    @Override
                    public void mapCleared(MapEvent event) {
                    }

                    @Override
                    public void entryUpdated(EntryEvent event) {
                    }

                    @Override
                    public void entryRemoved(EntryEvent event) {
                    }

                    @Override
                    public void entryEvicted(EntryEvent event) {
                    }

                    @Override
                    public void entryAdded(EntryEvent event) {
                        final String value = (String) event.getValue();
                        System.out.println("new Entry was added: " + value);
                    }
                });

        hazelCastMapService.addCash("str1", mapName);
        hazelCastMapService.addCash("new Entry", mapName);
    }
}
