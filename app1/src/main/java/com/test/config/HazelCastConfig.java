package com.test.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;

import java.util.Map;

public class HazelCastConfig {

    private Config config;

    public HazelCastConfig() {
        this.config = new Config();

        // scheduled config
        config.getScheduledExecutorConfig("myScheduledExecSvc")
                .setPoolSize(16)
                .setCapacity(100)
                .setDurability(1)
                .setQuorumName("quorumname");

        // networkConfig
        final NetworkConfig network = config.getNetworkConfig();
        final JoinConfig join = network.getJoin();

        network.setPort(5701).setPortAutoIncrement(true);

        join.getMulticastConfig().setEnabled(false);
        join.getTcpIpConfig()
                .addMember("localhost");

        //map configs
        final MapConfig mapConfig = config.getMapConfig("testMap");

        mapConfig.setBackupCount(0);
        mapConfig.setReadBackupData(true);
    }

    public Config getConfig() {
        return config;
    }
}
