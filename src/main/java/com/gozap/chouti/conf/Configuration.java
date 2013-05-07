package com.gozap.chouti.conf;


import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: saint
 * Date: 13-5-2
 * Time: 下午2:56
 * To change this template use File | Settings | File Templates.
 */
public class Configuration {
    private static final Logger LOGGER = Logger.getLogger(Configuration.class);
    private RssType rssType;
    private String path;
    private String userName;
    private String password;
    private String removeDup;
    private int bloomFilterInsertCount;
    private long cacheTime;
    private String baseUri;
    private String source;
    private String secretKey;

    private Integer threadNum = 50;
    private static Configuration conf = new Configuration();


    public static Configuration getInstance() {
        return conf;
    }

    private Configuration() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/configuration.properties"));
            LOGGER.info(properties);
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        this.removeDup = properties.getProperty("removeDup", "BLOOM_FILTER");
        this.cacheTime =  Integer.valueOf(properties.getProperty("cacheTime", 60 * 24 * 3 + ""));
        this.bloomFilterInsertCount = Integer.valueOf(properties.getProperty("bloomFilterInsertCount", 10000000 + ""));
        this.threadNum = Integer.valueOf(properties.getProperty("threadNum", "10"));
        this.baseUri = properties.getProperty("baseUri", "http://service.chouti.com/");
        this.secretKey =  properties.getProperty("secretKey");
        this.source =  properties.getProperty("source");
        if (properties.getProperty("rssType").equals(RssType.CSV.getName())) {
            this.rssType = RssType.CSV;
            this.path = properties.getProperty("rssSource");
        } else if (properties.getProperty("rssType").equals(RssType.MYSQL.getName())) {
            this.rssType = RssType.MYSQL;
            this.path =  properties.getProperty("rssSource");
            this.userName = properties.getProperty("rssUserName");
            this.password = properties.getProperty("rssPassword");
        }
    }

    public Integer getThreadNum() {
        return threadNum;
    }

    public RssType getRssType() {
        return rssType;
    }

    public String getPath() {
        return path;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }


    public String getRemoveDup() {
        return removeDup;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public int getBloomFilterInsertCount() {
        return bloomFilterInsertCount;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public String getSource() {
        return source;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
