package com.gozap.chouti;


import au.com.bytecode.opencsv.CSVReader;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.google.common.primitives.Bytes;
import com.gozap.chouti.RssSource.RssSource;
import com.gozap.chouti.RssSource.RssSourceManager;
import com.gozap.chouti.conf.RssSourceType;
import com.gozap.chouti.model.RssFeed;
import com.gozap.chouti.model.RssItem;
import com.gozap.chouti.model.RssParser;
import com.gozap.chouti.service.HttpService;
import junit.framework.Assert;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Unit test for simple App.
 */
public class AppTest {
    private static final Logger LOGGER = Logger.getLogger(AppTest.class);

    @Test
    public void testRedirect() {
        byte[] bytes = HttpService.get("http://www.chouti.com/");
        LOGGER.info(new String(bytes));
    }

    @Test
    public void testRss() throws IOException, InterruptedException {
        ExecutorService executors = Executors.newFixedThreadPool(100);
        String filepath = Class.class.getClass().getResource("/").getPath() + "/rssfeeds.csv";
        File feeds = new File(filepath);
        FileReader fstream = new FileReader(feeds);
        CSVReader reader = new CSVReader(fstream);
        List<String[]> lists = reader.readAll();
        executors.shutdown();
    }

    @Test
    public void testEqual() {
        RssSource rss1 = new RssSource();
        rss1.setUrl("1111");
        Set<RssSource> list = new HashSet<RssSource>();
        list.add(rss1);
        RssSourceManager rssSourceManager = new RssSourceManager();
        rssSourceManager.loadRssSources(list, null);
        RssSource rss2 = new RssSource();
        rss2.setUrl("1111");
        Set<RssSource> list1 = new HashSet<RssSource>();
        list1.add(rss2);
        for (RssSource rssSource : list1) {
           System.out.print(!rssSourceManager.contains(rssSource));
        }
    }

    @Test
    public void testParse() throws Exception {
        byte[] xml = HttpService.get("http://blog.sina.com.cn/rss/1254597365.xml");
        RssParser parser = new RssParser();
        RssFeed rssFeed = parser.loadString(new String(xml));
        Assert.assertTrue(rssFeed.getChannel().getTtl() == 5 * 60);
    }

//    http://www.neihan8.com/rss.xml


    @Test
    public void testEncode() throws Exception {

        byte[] xml = HttpService.get("http://www.neihan8.com/data/rss/3.xml");

        InputStream inputStream = new ByteArrayInputStream(xml);
        byte[] buf = new byte[200];
        inputStream.read(buf);
        inputStream.close();
        String t = new String(buf);
        Pattern pattern = Pattern.compile("encoding=\"(.+)\"");
        Matcher matcher = pattern.matcher(t);
        String encode = null;
        if(matcher.find()) {
//            encode = matcher.group(1);
//        }
//        else {
            UniversalDetector detector = new UniversalDetector(null);
            inputStream = new ByteArrayInputStream(xml);
            // (2)
            int nread;
            while ((nread = inputStream.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            detector.dataEnd();
            encode = detector.getDetectedCharset();
            if (encode != null) {
                System.out.println("Detected encoding = " + encode);
            } else {
                System.out.println("No encoding detected.");
            }
            detector.reset();
        }

        RssParser parser = new RssParser();

        RssFeed rssFeed = parser.loadString(new String(xml, encode))  ;
        System.out.println(rssFeed.getChannel().getTitle());
        for (RssItem rssItem : rssFeed.getItems()) {

            System.out.println(StringUtils.substring(rssItem.getDescription(), 0, 150));
        }







    }

    @Test
    public void testApp() throws Exception {
        RssSourceType rssSourceType = new RssSourceType();
    }

    @Test
    public void testFilter() throws InterruptedException {
        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(), 100000000, 0.00001);
        bloomFilter.put("http://blog.sina.com.cn/s/blog_6151984a0100ekl6.html");
        boolean t = bloomFilter.mightContain("http://blog.sina.com.cn/s/blog_6151984a0100ekl6.html");
        Thread.sleep(10000000);
        Assert.assertEquals(t, true);

    }

    private static String removeHtml(String input) {
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "")
                .replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "")
                .replaceAll("\r\n", "").trim();
        return str;
    }


}
