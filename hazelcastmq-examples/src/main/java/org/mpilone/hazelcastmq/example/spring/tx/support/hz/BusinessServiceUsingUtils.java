
package org.mpilone.hazelcastmq.example.spring.tx.support.hz;

import org.mpilone.hazelcastmq.spring.tx.HazelcastUtils;

import com.hazelcast.core.*;

/**
 * A business service that uses {@link HazelcastUtils} to get transactional
 * instances of distributed objects when appropriate.
 *
 * @author mpilone
 */
public class BusinessServiceUsingUtils extends BusinessService {

  public BusinessServiceUsingUtils() {
  }

  public BusinessServiceUsingUtils(HazelcastInstance hazelcastInstance) {
    super(hazelcastInstance);
  }

  @Override
  protected IQueue<String> getQueue(String name,
      HazelcastInstance hazelcastInstance) {
    IQueue<String> queue = HazelcastUtils.getTransactionalQueue(name,
        hazelcastInstance, true);

    if (queue == null) {
      // No active transaction. Get a non-transactional queue.
      queue = hazelcastInstance.getQueue(name);
    }

    return queue;
  }


}
