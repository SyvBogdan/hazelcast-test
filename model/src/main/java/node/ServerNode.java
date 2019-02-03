package node;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class ServerNode {

  final HazelcastInstance hzInstance;

    public ServerNode() {
         hzInstance = Hazelcast.newHazelcastInstance();
    }
}
