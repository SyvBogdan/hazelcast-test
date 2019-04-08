package node;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class ServerNode {

  final HazelcastInstance hzInstance;

    public ServerNode(Config config) {
         hzInstance = Hazelcast.newHazelcastInstance(config);
    }

    public ServerNode() {
        this.hzInstance = Hazelcast.newHazelcastInstance();
    }

    public HazelcastInstance getHzInstance() {
        return hzInstance;
    }
}
