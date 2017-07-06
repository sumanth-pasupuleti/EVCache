## Configuration

Property Name | Override Property Name | Default Value | Dynamic |
:----------- |:-------------:| -----------|-----------:
<CACHE\>.EVCacheClientPool.poolSize| | 1 | yes
<CACHE\>.EVCacheClientPool.readTimeout|default.read.timeout| 20 | yes
<CACHE\>.EVCacheClientPool.bulkReadTimeout|| <CACHE\>.EVCacheClientPool.readTimeout | yes
<CACHE\>.operation.timeout| | 2500 | yes
<CACHE\>.max.read.queue.length| | 5 | yes
<CACHE\>.max.queue.length| | 16384 | yes
<CACHE\>.max.retry.count| | 1 | yes
<CACHE\>.retry.all.copies| | false | yes
<CACHE\>.operation.QueueMaxBlockTime| | 10 | yes
<CACHE\>.EVCacheClientPool.refresh.connection.on.readQueueFull| EVCacheClientPool.refresh.connection.on.readQueueFull | false | yes
<CACHE\>.EVCacheClientPool.refresh.connection.on.readQueueFull.size| EVCacheClientPool.refresh.connection.on.readQueueFull.size | 100 | yes
<CACHE\>.disable.async.refresh| | false | yes
<CACHE\>.log.operation| | 0 | yes
<CACHE\>.log.operation.calls| | SET,DELETE,GMISS,TMISS,BMISS_ALL,TOUCH,REPLACE | yes
<CACHE\>.reconcile.interval| | 600000 | yes
<CACHE\>.clone.writes.to| | | yes
<CACHE\>.ping.servers| evcache.ping.servers | false | yes
<CACHE\>.<asg\>.EVCacheClientPool.writeOnly| <CACHE\>.<zone\>.EVCacheClientPool.writeOnly| | yes
EVCacheClientPoolManager.<CACHE\>.alias| | | no
evcache.use.simple.node.list.provider| | false | yes
evcache.appsToInit| | | no
<asg\>.chunk.data | <CACHE\>.chunk.data | false | no
<asg\>.chunk.size | <CACHE\>.chunk.size | 1180 | no
<CACHE\>.<asg\>.write.block.duration | <CACHE\>.write.block.duration | 25 | yes
<CACHE\>.<asg\>.ignore.touch | <CACHE\>.ignore.touch | false | yes
<CACHE\>.<asg\>.bucket.size | <CACHE\>.bucket.size | 160 | yes
EVCacheNodeLocator.<CACHE\>.<asg\>.hash.on.partial.key | EVCacheNodeLocator.<CACHE\>.hash.on.partial.key | false | yes
EVCacheNodeLocator.<CACHE\>.<asg\>.hash.delimiter | EVCacheNodeLocator.<CACHE\>.hash.delimiter | : | yes
EVCacheScheduledExecutor.<CACHE\>.max.size | | (set to processor count) | yes
EVCacheScheduledExecutor.<CACHE\>.core.size | | 1 | yes
evache.mutate.timeout | | <CACHE\>.operation.timeout | yes
<asg\>.failure.mode | <CACHE\>.failure.mode | Retry | yes
evcache.thread.daemon | | false | no
<CACHE\>.<prefix\>.throw.exception | <CACHE\>.throw.exception | false | yes
<CACHE\>.<prefix\>.fallback.zone | <CACHE\>.fallback.zone | true | yes
<CACHE\>.bulk.fallback.zone | | true | yes
<CACHE\>.bulk.partial.fallback.zone | | true | yes
<CACHE\>.events.using.latch | evcache.events.using.latch | true | yes
default.evcache.max.data.size | | 20971520 | no
default.evcache.compression.threshold | | 120 | no
<CACHE\>.use.batch.port | evcache.use.batch.port | false | no
<CACHE\>.ignore.hosts | | | no
evcache.request.expiry.optout | | true | yes

## Cross Region Replication 
Property Name | Override Property Name | Default Value | Dynamic |
:----------- |:-------------:| -----------|-----------:
EVCacheReplicationManager.use.schlep | | false | yes
<CACHE\>.region.replication | | false | yes
<CACHE\>.use.kafka | | true | yes
<CACHE\>.use.sqs | | false | yes
<CACHE\>.max.replication.threadpool.size | | 10 | yes
<CACHE\>.max.queue.size | | 1000 | yes
<CACHE\>.replicate.set | | true | yes
<CACHE\>.replicate.delete | | true | yes
<CACHE\>.replicate.add | | true | yes
<CACHE\>.replicate.append | | true | yes
<CACHE\>.replicate.append_or_add | | true | yes
<CACHE\>.replicate.replace | | true | yes
<CACHE\>.replicate.touch | | false | yes
<CACHE\>.replicate.GetAndTouch | | false | yes
<CACHE\>.drop.messages.on.queue.full | | false | yes
<CACHE\>.replicate.set.data | | false | yes
<CACHE\>.set.timeout | | 20 | yes
EVCacheReplicationManager.<cache/>.bootstrap.servers | EVCacheReplicationManager.bootstrap.servers | evcachereplvpc.kafka.${EC2_REGION}.dynprod.netflix.net:7101 | yes
EVCacheReplicationManager.<cache/>.buffer.memory | EVCacheReplicationManager.buffer.memory | 8388608 | yes
EVCacheReplicationManager.<cache/>.compression.type |EVCacheReplicationManager.compression.type | gzip | yes
EVCacheReplicationManager.<cache/>.retries |EVCacheReplicationManager.retries | 1 | yes
EVCacheReplicationManager.<cache/>.linger.ms | EVCacheReplicationManager.linger.ms | 500 | yes
EVCacheReplicationManager.<cache/>.acks | EVCacheReplicationManager.acks | 1 | yes
EVCacheReplicationManager.<cache/>.stickyPartitioner.intervalMs | EVCacheReplicationManager.stickyPartitioner.intervalMs | 1000 | yes
EVCacheReplicationManager.<cache/>.partitioner.class | EVCacheReplicationManager.partitioner.class | com.netflix.nfkafka.StickyPartitioner | yes

## Fixing Write Failues 
Property Name | Override Property Name | Default Value | Dynamic |
:----------- |:-------------:| -----------|-----------:
<CACHE\>.WriteFailure.to.kafka | WriteFailure.to.kafka | false | yes
WriteFailureListener.bootstrap.servers | | evcachereplvpc.kafka.us-east-1.dynprod.netflix.net:7101 | yes
WriteFailureListener.retries | | 1 |  yes
WriteFailureListener.linger.ms | | 500 |  yes
WriteFailureListener.acks | | 1 |  yes
WriteFailureListener.retries | | 1 |  yes
WriteFailureListener.partitioner.class | | com.netflix.nfkafka.StickyPartitioner | yes
WriteFailureListener.stickyPartitioner.intervalMs | 1000 | yes

## In Memory
Property Name | Override Property Name | Default Value | Dynamic |
:----------- |:-------------:| -----------|-----------:
<CACHE\>.use.inmemory.cache | evcache.use.inmemory.cache | false | yes
<CACHE\>.inmemory.expire.after.write.duration.ms | <CACHE\>.inmemory.cache.duration.ms | 0 | yes
<CACHE\>.inmemory.expire.after.access.duration.ms | | 0 | yes
<CACHE\>.inmemory.refresh.after.write.duration.ms | | 0 | yes
<CACHE\>.inmemory.cache.size | | 100 | yes
<CACHE\>.thread.pool.size | | 5 | yes


## Throttling

Property Name | Override Property Name | Default Value | Dynamic |
:----------- |:-------------:| -----------|-----------:
<CACHE\>.enable.throttling | | false | yes
<CACHE\>.disable.writes | | false | yes
<CACHE\>.throttle.percent | | 0 | yes
<CACHE\>.throttle.time | | 0 | yes
EVCacheThrottler.throttle.operations | | false | yes
<CACHE\>.throttle.calls | | | yes |
EVCacheThrottler.<CACHE\>.throttle.hot.keys | EVCacheThrottler.throttle.hot.keys | | false | yes |
EVCacheThrottler.<CACHE\>.inmemory.expire.after.write.duration.ms | | 10000 | yes |
EVCacheThrottler.<CACHE\>.inmemory.expire.after.access.duration.ms | | 10000 | yes |
EVCacheThrottler.<CACHE\>.inmemory.cache.size | | 100 | yes |
<CACHE\>.throttle.keys | | (comma separated list of keys)| yes |
EVCacheThrottler.<CACHE\>.throttle.value | | 3 | yes |


## Simple Node List

System Property | Override Property Name | Default Value |
:----------- |:-------------:| -----------|
NETFLIX_ENVIRONMENT | @environment <br> or <br> eureka.environment | 
EC2_REGION | @region <br> or <br> eureka.region | 
<CACHE\>-NODES | | 
<CACHE\>.use.batch.port | evcache.use.batch.port | false 