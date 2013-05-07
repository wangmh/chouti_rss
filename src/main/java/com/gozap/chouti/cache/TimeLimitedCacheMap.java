package com.gozap.chouti.cache;


import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created with IntelliJ IDEA.
 * User: saint
 * Date: 13-5-2
 * Time: 下午2:56
 * To change this template use File | Settings | File Templates.
 */
public final class TimeLimitedCacheMap<K, V> {

    private static final Logger LOGGER = Logger.getLogger(TimeLimitedCacheMap.class);

    private final ConcurrentHashMap<K, V> objectMap = new ConcurrentHashMap<K, V>(10);

    private final ConcurrentHashMap<K, Long> timeMap = new ConcurrentHashMap<K, Long>();

    private final ReentrantReadWriteLock accessLock = new ReentrantReadWriteLock();

    private final Runnable evictor = new Runnable() {
        @Override
        public void run() {
            // avoid runs on empty maps
            if (timeMap.isEmpty()) {
                Thread.yield();
            }
            long currentTime = System.currentTimeMillis();
            accessLock.readLock().lock();
            Set<K> keys = new HashSet<K>(timeMap.keySet());
            accessLock.readLock().unlock();

            Set<K> markedForRemoval = new HashSet<K>(10);
            for (K key : keys) {
                long lastTime = timeMap.get(key);
                if (lastTime == 0) {
                    continue;
                }
                long interval = currentTime - lastTime;
                long elapsedTime = expiryTimeUnit.convert(interval, TimeUnit.MILLISECONDS);
                if (elapsedTime > expiryTime) {
                    markedForRemoval.add(key);
                }
            }

            for (K key : markedForRemoval) {
                long lastTime = timeMap.get(key);
                if (lastTime == 0) {
                    continue;
                }
                long interval = currentTime - lastTime;
                long elapsedTime = expiryTimeUnit.convert(interval, TimeUnit.MILLISECONDS);
                if (elapsedTime > expiryTime) {
                    V v = remove(key);
                    LOGGER.info(v.toString() + " delete from cache");
                }
            }
        }
    };

    private final ScheduledExecutorService timer = Executors
            .newSingleThreadScheduledExecutor(new MyThreadFactory(true));

    private final class MyThreadFactory implements ThreadFactory {

        private boolean isDaemon = false;

        public MyThreadFactory(boolean daemon) {
            isDaemon = daemon;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(isDaemon);
            return t;
        }

    }

    ;

    private final long expiryTime;
    private final TimeUnit expiryTimeUnit;


    public TimeLimitedCacheMap(long initialDelay, long evictionDelay, long expiryTime, TimeUnit unit) {
        timer.scheduleWithFixedDelay(evictor, initialDelay, evictionDelay, unit);
        this.expiryTime = expiryTime;
        this.expiryTimeUnit = unit;

    }


    public void close() {
        timer.shutdownNow();
        objectMap.clear();
        timeMap.clear();
    }


    public void put(K key, V value) {
        accessLock.writeLock().lock();
        long time = System.currentTimeMillis();
        timeMap.put(key, time);
        objectMap.put(key, value);
        accessLock.writeLock().unlock();
    }


    public void put(K key, V value, long time) {
        accessLock.writeLock().lock();
        timeMap.put(key, time);
        objectMap.put(key, value);
        accessLock.writeLock().unlock();
    }

    /**
     * Get object from map.
     *
     * @param key String
     * @return String
     * @author franciscolv
     * @date Apr 2, 2013
     */
    public V get(K key) {
        accessLock.readLock().lock();
        V v = objectMap.get(key);
        accessLock.readLock().unlock();
        return v;
    }




    /**
     * Read comments for put() they apply here as well.
     * If had not allowed remove(), life would have been zillion times simpler.
     * However, an undoable action is quite bad.
     *
     * @param key K
     * @return
     * @author franciscolv
     * @date Apr 2, 2013
     */
    public V remove(K key) {
        accessLock.writeLock().lock();
        // accessLock.lock();
        V value = objectMap.remove(key);
        timeMap.remove(key);
        // accessLock.unlock();
        accessLock.writeLock().unlock();
        return value;

    }

    /**
     * Clone requires locking, to prevent the edge case where
     * the map is updated with clone is in progress
     */
    public Map<K, V> getClonedMap() {
        accessLock.readLock().lock();
        HashMap<K, V> mapClone = new HashMap<K, V>(objectMap);
        accessLock.readLock().unlock();
        return Collections.unmodifiableMap(mapClone);
    }

}