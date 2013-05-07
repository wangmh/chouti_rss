package com.gozap.chouti.RssSource;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.gozap.chouti.conf.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: saint
 * Date: 13-5-3
 * Time: 上午11:50
 * To change this template use File | Settings | File Templates.
 */
public class BloomFilterService {
    //调用的时候初始化，防止开辟内存
    private static BloomFilter<CharSequence> bloomFilter = null;

    public synchronized static boolean isContain(String url) {
        if(bloomFilter == null)  {
            bloomFilter = BloomFilter.create(Funnels.stringFunnel(), Configuration.getInstance().getBloomFilterInsertCount(), 0.000001);
        }
        if (bloomFilter.mightContain(url)) {
            return true;
        } else {
            bloomFilter.put(url);
            return false;
        }
    }
}
