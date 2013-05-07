package com.gozap.chouti.conf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: saint
 * Date: 13-5-2
 * Time: 下午5:05
 * To change this template use File | Settings | File Templates.
 */
public class RssSourceType {

    private Map<Integer, String> rssSourceTypeMap = new ConcurrentHashMap<Integer, String>();
    private Map<Integer, String> lastMap = new HashMap<Integer, String>();


    public RssSourceType() {
        initFromConfig();
        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(new RefreshThread(), 10, 10, TimeUnit.SECONDS);
    }

    private void initFromConfig() {

        try {

            Properties properties = new Properties();
            properties.load(this.getClass().getResourceAsStream("/rssSourceType.properties"));
            Map<Integer,String> hashMap = new HashMap<Integer, String>();
            for(String key : properties.stringPropertyNames()) {
                hashMap.put( Integer.valueOf(properties.getProperty(key)),key);
            }
            if (!hashMap.equals(lastMap)) {
                rssSourceTypeMap.putAll(lastMap);
                lastMap = hashMap;
            }

        } catch (IOException e) {
            initDefault();
        }

    }

    private void initDefault() {
        Map<Integer,String> hashMap = new HashMap<Integer, String>();
        hashMap.put(1, "news");
        hashMap.put(2, "scoff");
        hashMap.put(3, "rumor");
        hashMap.put(4, "pic");
        hashMap.put(100, "tech");
        if (!hashMap.equals(lastMap)) {
           rssSourceTypeMap.putAll(lastMap);
           lastMap = hashMap;
        }

    }

    private class RefreshThread  implements  Runnable {
        @Override
        public void run() {
            initFromConfig();
        }
    }

    public String getName(Integer i) {
        String type = rssSourceTypeMap.get(i);
        return type == null ? "news" : type;
    }



    private final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());

    private class DaemonThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }
    }
}
