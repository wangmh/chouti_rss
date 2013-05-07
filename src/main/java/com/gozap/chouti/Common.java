package com.gozap.chouti;

import com.gozap.chouti.RssSource.RssSource;
import com.gozap.chouti.cache.TimeLimitedCacheMap;
import com.gozap.chouti.conf.Configuration;
import com.gozap.chouti.conf.RssSourceType;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: saint
 * Date: 13-4-28
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
public class Common {
    /**
     * 默认间隔20s
     */
    public static long TTL  = 20 * 60 * 1000L;

    public static LinkedBlockingDeque<RssSource> RssJobQueue = new LinkedBlockingDeque<RssSource>(256);

    public static RssSourceType rssSourceType =  new RssSourceType();

    /**
     * 每隔3天更新title文件
     */
    public static TimeLimitedCacheMap<String, Byte> titleFilterMap = new TimeLimitedCacheMap<String, Byte>(10, 20, Configuration.getInstance().getCacheTime(), TimeUnit.MINUTES);
}
