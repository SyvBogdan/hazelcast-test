package com.test;

import com.hazelcast.config.Config;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MapEvent;
import com.hazelcast.map.listener.MapListener;
import com.hazelcast.scheduledexecutor.DuplicateTaskException;
import com.hazelcast.scheduledexecutor.IScheduledExecutorService;
import com.test.config.HazelCastConfig;
import com.test.map.HazelCastMapService;
import com.test.map.impl.StringHazelCastMapService;
import com.test.task.PrintTask;
import node.ServerNode;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class App1 {

    public static void main(String[] args) throws InterruptedException {

        final String mapName = "testMap";

        final Config config = new HazelCastConfig().getConfig();

        final ServerNode serverNode = new ServerNode(config);

        final HazelCastMapService<String> hazelCastMapService = new StringHazelCastMapService(serverNode.getHzInstance())
                .addListenerToMap(mapName, new EntryListener() {
                    @Override
                    public void mapEvicted(MapEvent event) {
                        System.out.println("Map was evicted");
                    }

                    @Override
                    public void mapCleared(MapEvent event) {

                        System.out.println("Map was cleared");
                    }

                    @Override
                    public void entryUpdated(EntryEvent event) {

                        System.out.println("Entry was updated: " + event.getValue());
                    }

                    @Override
                    public void entryRemoved(EntryEvent event) {
                        System.out.println("Entry was removed: " + event.getValue());
                    }

                    @Override
                    public void entryEvicted(EntryEvent event) {
                        System.out.println("Entry was evicted: " + event.getValue());
                    }

                    @Override
                    public void entryAdded(EntryEvent event) {
                        final String value = (String) event.getValue();
                        System.out.println("new Entry was added: " + value);
                    }
                });


        final UUID uuid_entry = hazelCastMapService.addCash("new Entry", mapName);

        Thread.sleep(500);


        try {
            IScheduledExecutorService scheduledExecutorService =
                    serverNode.getHzInstance().getScheduledExecutorService("printTask");

            scheduledExecutorService.scheduleAtFixedRate(new PrintTask(), 1000l, 1000l, TimeUnit.MILLISECONDS);

            hazelCastMapService.changeCash(uuid_entry, "new Entry val", mapName);

            hazelCastMapService.removeCash(uuid_entry, mapName);
        } catch (DuplicateTaskException e) {

            System.out.println("===========> Task Dublication was occured");
        }
    }
}
