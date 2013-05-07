package com.gozap.chouti.thread;

import com.gozap.chouti.Common;
import com.gozap.chouti.RssSource.BloomFilterService;
import com.gozap.chouti.RssSource.RssSource;
import com.gozap.chouti.conf.Configuration;
import com.gozap.chouti.model.RssFeed;
import com.gozap.chouti.model.RssItem;
import com.gozap.chouti.model.RssParser;
import com.gozap.chouti.service.ChoutiService;
import com.gozap.chouti.service.HttpService;
import com.gozap.chouti.utils.MD5Utils;
import org.apache.log4j.Logger;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: saint
 * Date: 13-4-28
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
public class Worker implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Worker.class);

    private static final Pattern pattern = Pattern.compile("encoding=\"(.+)\"");

    private static final byte t = '1';

    @Override
    public void run() {
        while (true) {
            try {
                RssSource rssSource = Common.RssJobQueue.take();
                String path = rssSource.getUrl();
                byte[] bytes = HttpService.get(path);
                if (bytes != null) {
                    RssParser rssParser = new RssParser();
                    try {
                        RssFeed rssFeed = rssParser.loadString(new String(bytes, detectEncode(bytes)));
                        rssSource.setTtl(rssFeed.getChannel().getTtl());
                        List<RssItem> rssItems = rssFeed.getItems();
                        for (RssItem rssItem : rssItems) {
                            if (!isDup(rssItem)) {
                                sendRssItem(rssItem, rssSource);
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error(e);
                    }
                }
                LOGGER.info(rssSource);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String detectEncode(byte[] bytes) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        byte[] buf = new byte[200];
        String encode;
        inputStream.read(buf);
        inputStream.close();
        String t = new String(buf);
//        Matcher matcher = pattern.matcher(t);
//        if (matcher.find()) {
//            encode = matcher.group(1);
//        } else {
        UniversalDetector detector = new UniversalDetector(null);
        inputStream = new ByteArrayInputStream(bytes);
        int nRead = 0;
        while ((nRead = inputStream.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nRead);
        }
        detector.dataEnd();
        encode = detector.getDetectedCharset();
        if (encode != null) {
            LOGGER.info("Detected encoding = " + encode);
        } else {
            LOGGER.error("No encoding detected.");
            encode = "UTF-8";
        }
        detector.reset();
//        }
        return encode;

    }

    private void sendRssItem(RssItem rssItem, RssSource rssSource) {
        try {
            ChoutiService.save(rssItem, rssSource);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
        }
    }

    private boolean isDup(RssItem rssItem) throws NoSuchAlgorithmException {
        if (Configuration.getInstance().getRemoveDup().equals("BLOOM_FILTER")) {
            if (BloomFilterService.isContain(rssItem.getLink())) {
                LOGGER.info(rssItem.getTitle() + ":" + rssItem.getLink() + " exist");
                return true;
            }

        } else {
            String md5Str = MD5Utils.encoderByMD5(rssItem.getLink());
            if (Common.titleFilterMap.get(md5Str) != null) {
                LOGGER.info(rssItem.getTitle() + ":" + rssItem.getLink() + " exist");
                return true;
            } else {
                Common.titleFilterMap.put(md5Str, t);
            }
        }
        return false;
    }


}