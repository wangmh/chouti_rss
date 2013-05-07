package com.gozap.chouti.RssSource;

import au.com.bytecode.opencsv.CSVReader;
import com.gozap.chouti.conf.Configuration;
import com.gozap.chouti.conf.RssType;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: saint
 * Date: 13-5-2
 * Time: 下午2:30
 * To change this template use File | Settings | File Templates.
 */
public class DefaultRssSourceLoadService implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(DefaultRssSourceLoadService.class);
    private Set<RssSource> set;
    private RssSourceManager rssSourceManager;

    public DefaultRssSourceLoadService(RssSourceManager rssSourceManager) {
        set = new HashSet<RssSource>();
        this.rssSourceManager = rssSourceManager;
    }


    @Override
    public void run() {
        //当发生改变时才重新加载
        Set<RssSource> rssSources = loadRssSource();
        LOGGER.info("rssSource size: " + rssSources.size());
        Set<RssSource> addSets = addSet(set, rssSources);
        Set<RssSource> reduceSets = reduceSet(set, rssSources);
        for (RssSource rssSource : addSets) {
            LOGGER.info("添加了rssSource: " + rssSource);
        }
        for (RssSource rssSource : reduceSets) {
            LOGGER.info("删除了rssSource: " + rssSource);
        }

        if (addSets.size() > 0 || reduceSets.size() > 0) {
            set = rssSources;
            rssSourceManager.loadRssSources(addSets, reduceSets);
        }
    }

    private Set<RssSource> addSet(Set<RssSource> set, Set<RssSource> rssSources) {
        Set<RssSource> result = new HashSet<RssSource>(rssSources);
        result.removeAll(set);
        return result;
    }

    private Set<RssSource> reduceSet(Set<RssSource> set, Set<RssSource> rssSources) {
        Set<RssSource> result = new HashSet<RssSource>(set);
        result.removeAll(rssSources);
        return result;
    }


    protected Set<RssSource> loadRssSource() {
        Set<RssSource> rssSources = new HashSet<RssSource>();
        Configuration conf = Configuration.getInstance();
        if (conf.getRssType().equals(RssType.CSV)) {
            try {
                FileInputStream inputStream = new FileInputStream(conf.getPath());
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                CSVReader reader = new CSVReader(bufferedReader);
                List<String[]> lists = reader.readAll();
                System.out.println(lists.size());
                for (String[] record : lists) {
                    RssSource rssSource = new RssSource();
                    rssSource.setUpdatedAt(-1L);
                    rssSource.setUrl(record[3]);
                    rssSource.setUserName(record[2]);
                    rssSource.setName(record[1]);
                    rssSource.setType(Integer.valueOf(record[10]));
                    rssSource.setTtl(Integer.valueOf(record[5]));
                    rssSource.setError(false);
                    rssSources.add(rssSource);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
            } catch (FileNotFoundException e) {
                LOGGER.error(e);
            } catch (IOException e) {
                LOGGER.error(e);
            }

        } else if (conf.getRssType().equals(RssType.MYSQL)) {
//
        } else {

        }
        return rssSources;
    }

}
