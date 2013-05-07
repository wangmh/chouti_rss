package com.gozap.chouti.RssSource;

import com.gozap.chouti.Common;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: saint
 * Date: 13-4-28
 * Time: 下午4:21
 * 该类用来更新RssJobQueue队列
 */
public final class RssSourceManager implements  Runnable {
    private static final Logger LOGGER = Logger.getLogger(RssSourceManager.class);

    private Set<RssSource> manager;


    public RssSourceManager() {
       this.manager  = new CopyOnWriteArraySet<RssSource>();
    }




    public void loadRssSources(Set<RssSource> addSets, Set<RssSource> reduceSets  ) {
        manager.addAll(addSets);
        manager.removeAll(reduceSets);
    }


    public boolean contains(RssSource rssSource) {
        return manager.contains(rssSource);
    }


    @Override
    public void run() {
        Date date = new Date();
        long time = date.getTime();
        LOGGER.info("manager size: " + manager.size());
        for (RssSource rssSource : manager) {
                /*
                 * 将超时未更新的放置到队里中 ，如果队列满，会抛出异常,此时不更新动作。
                 */
            if (rssSource.getTtl() * 60 * 1000 + rssSource.getUpdatedAt() <= time) {
                try {
                    Common.RssJobQueue.push(rssSource);
                    rssSource.setUpdatedAt(new Date());
                } catch (Throwable e) {
                    LOGGER.error("写入队列不成功", e);
                }
            }
        }
    }



}
