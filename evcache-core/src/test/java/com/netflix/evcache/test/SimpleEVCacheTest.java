package com.netflix.evcache.test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.netflix.evcache.EVCache;
import com.netflix.evcache.EVCacheImpl;
import com.netflix.evcache.EVCacheLatch.Policy;
import com.netflix.evcache.pool.EVCacheClient;
import com.netflix.evcache.pool.EVCacheClientPool;
import com.netflix.evcache.pool.EVCacheClientPoolManager;

import rx.schedulers.Schedulers;


public class SimpleEVCacheTest extends Base {
    private static final Logger log = LogManager.getLogger(SimpleEVCacheTest.class);

    private ThreadPoolExecutor pool = null;

    public static void main(String args[]) {
        SimpleEVCacheTest test = new SimpleEVCacheTest();
        test.setProps();
        test.setupEnv();
        test.testAll();
    }

    @BeforeSuite
    public void setProps() {
        BasicConfigurator.resetConfiguration();
        BasicConfigurator.configure(new ConsoleAppender(new PatternLayout("%d{HH:mm:ss,SSS} [%t] %p %c %x - %m%n")));
        Logger.getRootLogger().setLevel(Level.INFO);
        Logger.getLogger(SimpleEVCacheTest.class).setLevel(Level.DEBUG);
        Logger.getLogger(Base.class).setLevel(Level.DEBUG);
        Logger.getLogger(EVCacheImpl.class).setLevel(Level.DEBUG);
        Logger.getLogger(EVCacheClient.class).setLevel(Level.DEBUG);
        Logger.getLogger(EVCacheClientPool.class).setLevel(Level.DEBUG);

        final Properties props = getProps();
        props.setProperty("EVCACHE_PERF_SP_HW.use.simple.node.list.provider", "true");
        props.setProperty("EVCACHE_PERF_SP_HW.EVCacheClientPool.readTimeout", "1000");
        props.setProperty("EVCACHE_PERF_SP_HW.EVCacheClientPool.bulkReadTimeout", "1000");
        props.setProperty("EVCACHE_PERF_SP_HW.max.read.queue.length", "100");
        props.setProperty("EVCACHE_PERF_SP_HW.operation.timeout", "10000");
        props.setProperty("EVCACHE_PERF_SP_HW.throw.exception", "false");

        // set nodes list
        props.setProperty("EVCACHE_PERF_SP_HW.EVCACHE_PERF_SP_HW-NODES", "evcache_test-useast1d-1581010900-00=100.66.202.227:11211");

        int maxThreads = 2;
        final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(100000);
        pool = new ThreadPoolExecutor(maxThreads * 4, maxThreads * 4, 30, TimeUnit.SECONDS, queue);
        pool.prestartAllCoreThreads();
    }

    public SimpleEVCacheTest() {
    }

    @BeforeSuite(dependsOnMethods = { "setProps" })
    public void setupClusterDetails() {
        manager = EVCacheClientPoolManager.getInstance();
    }
    
    public void testAll() {
        try {
            EVCacheClientPoolManager.getInstance().initEVCache("EVCACHE_PERF_SP_HW");
            testEVCache();

            int i = 1;
            boolean flag = true;
            while (flag) {
                try {
//                    testAdd();
                    testInsert();
//                    testAppend();
                    testGet();
                    testGetWithPolicy();
//                    testGetObservable();
//                    testGetAndTouch();
//                    testBulk();
//                    testBulkAndTouch();
//                    testAppendOrAdd();
//                    if(i++ % 5 == 0) testDelete();
                    //Thread.sleep(3000);
                } catch (Exception e) {
                    log.error(e);
                }
                //Thread.sleep(3000);
            }
        } catch (Exception e) {
            log.error(e);
        }
    }
    
    public void testGetForKey(String key) throws Exception {
        String value = evCache.<String>get(key);
        if(log.isDebugEnabled()) log.debug("get : key : " + key + " val = " + value);
    }

    

    @BeforeSuite
    public void setupEnv() {
        super.setupEnv();
    }

    protected EVCache evCache = null;

    @Test
    public void testEVCache() {
        this.evCache = (new EVCache.Builder()).setAppName("EVCACHE_PERF_SP_HW").setCachePrefix(null).enableRetry().build();
        assertNotNull(evCache);
    }

    @Test(dependsOnMethods = { "testEVCache" })
    public void testAdd() throws Exception {
        for (int i = 0; i < 10; i++) {
            add(i, evCache);
        }
    }

    @Test(dependsOnMethods = { "testAdd"})
    public void testAllMix() throws Exception
    {
        allMixTest(evCache);
    }

    @Test(dependsOnMethods = { "testAllMix" })
    public void testInsert() throws Exception {
        for (int i = 0; i < 10; i++) {
            assertTrue(insert(i, evCache), "SET : Following Index failed - " + i + " for evcache - " + evCache);
            //insert(i, evCache);
        }
    }

    @Test(dependsOnMethods = { "testInsert" })
    public void testAppend() throws Exception {
        for (int i = 0; i < 10; i++) {
            assertTrue(append(i, evCache), "APPEND : Following Index failed - " + i + " for evcache - " + evCache);
        }
    }

    @Test(dependsOnMethods = { "testAppend" })
    public void testGet() throws Exception {
        for (int i = 0; i < 10; i++) {
            final String val = get(i, evCache);
            assertNotNull(val);
        }
    }

    @Test(dependsOnMethods = { "testGet" })
    public void testGetWithPolicy() throws Exception {
        for (int i = 0; i < 10; i++) {
            final String val = getWithPolicy(i, evCache, Policy.QUORUM);
            assertNotNull(val);
        }
    }

    @Test(dependsOnMethods = { "testGetWithPolicy" })
    public void testGetAndTouch() throws Exception {
        for (int i = 0; i < 10; i++) {
            final String val = getAndTouch(i, evCache);
            assertNotNull(val);
        }
    }

    @Test(dependsOnMethods = { "testGetAndTouch" })
    public void testBulk() throws Exception {
        final String[] keys = new String[12];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = "key_" + i;
        }
        Map<String, String> vals = getBulk(keys, evCache);
        assertTrue(!vals.isEmpty());
        for (int i = 0; i < vals.size(); i++) {
            String key = "key_" + i;
            String val = vals.get(key);
        }
    }

    @Test(dependsOnMethods = { "testBulk" })
    public void testBulkAndTouch() throws Exception {
        final String[] keys = new String[10];
        for (int i = 0; i < 10; i++) {
            keys[i] = "key_" + i;
        }
        Map<String, String> vals = getBulkAndTouch(keys, evCache, 60 * 60);
        assertTrue(!vals.isEmpty());
        for (int i = 0; i < vals.size(); i++) {
            String key = "key_" + i;
            String val = vals.get(key);
        }
    }
    
    public void testAppendOrAdd() throws Exception {
        for (int i = 0; i < 10; i++) {
            assertTrue(appendOrAdd(i, evCache));
        }
    }

    @Test(dependsOnMethods = { "testBulkAndTouch" })
    public void testReplace() throws Exception {
        for (int i = 0; i < 10; i++) {
            replace(i, evCache);
        }
    }

    @Test(dependsOnMethods = { "testReplace" })
    public void testDelete() throws Exception {
        for (int i = 0; i < 10; i++) {
            assertTrue(delete(i, evCache), "DELETE : Following Index failed - " + i + " for evcache - " + evCache);
        }
    }

    @Test(dependsOnMethods = { "testDelete" })
    public void testInsertAsync() throws Exception {
        for (int i = 0; i < 10; i++) {
            boolean flag = insertAsync(i, evCache);
            assertTrue(flag, "SET ASYNC : Following Index failed - " + i + " for evcache - " + evCache);
        }
    }

    @Test(dependsOnMethods = { "testInsertAsync" })
    public void testTouch() throws Exception {
        for (int i = 0; i < 10; i++) {
            touch(i, evCache, 1000);
            String val = get(i, evCache);
            assertTrue(val != null);
        }
    }

    public boolean insertAsync(int i, EVCache gCache) throws Exception {
        // String val = "This is a very long value that should work well since we are going to use compression on it. This is a very long value that should work well since we are going to use compression on it. blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah.This is a very long value that should work well since we are going to use compression on it. This is a very long value that should work well since we are going to use compression on it. blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah .This is a very long value that should work well since we are going to use compression on it. This is a very long value that should work well since we are going to use compression on it. blah blah blah blah blah blah blah
        // blah blah blah blah blah blah blah blah blah blah blah val_"
        // + i;
        String val = "val_" + i;
        String key = "key_" + i;
        Future<Boolean>[] statuses = gCache.set(key, val, 24 * 60 * 60);
        for(Future<Boolean> status : statuses) {
            assertTrue(status.get(), "SET ASYNC : Following Index failed - " + i + " for evcache - " + evCache);
        }
        pool.submit(new StatusChecker(key, statuses));
        return true;
    }

    @Test(dependsOnMethods = { "testTouch" })
    public void testInsertLatch() throws Exception {
        for (int i = 0; i < 10; i++) {
            assertTrue(insertUsingLatch(i, "EVCACHE"));
        }
    }

    @Test(dependsOnMethods = { "testInsertLatch" })
    public void testDeleteLatch() throws Exception {
        for (int i = 0; i < 10; i++) {
            deleteLatch(i, "EVCACHE");
        }
    }
    
    public void testGetObservable() throws Exception {
        for (int i = 0; i < 10; i++) {
            final String val = getObservable(i, evCache, Schedulers.computation());
//            Observable<String> obs = evCache.<String> observeGet(key);
//            obs.doOnNext(new OnNextHandler(key)).doOnError(new OnErrorHandler(key)).subscribe();
        }
    }
    

    class StatusChecker implements Runnable {
        Future<Boolean>[] status;
        String key;

        public StatusChecker(String key, Future<Boolean>[] status) {
            this.status = status;
            this.key = key;
        }

        public void run() {
            try {
                for (Future<Boolean> s : status) {
                    if (log.isDebugEnabled()) log.debug("SET : key : " + key + "; success = " + s.get());
                }
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

}
