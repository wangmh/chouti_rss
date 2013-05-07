package com.gozap.chouti;

import au.com.bytecode.opencsv.CSVReader;
import com.gozap.chouti.RssSource.DefaultRssSourceLoadService;
import com.gozap.chouti.RssSource.RssSourceManager;
import com.gozap.chouti.conf.Configuration;
import com.gozap.chouti.thread.Worker;
import com.gozap.chouti.utils.SignalHandlerImpl;
import org.apache.log4j.Logger;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

/**
 * Hello world!
 */
public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class);


    public static void main(String[] args) throws IOException, InterruptedException {
        Configuration conf = Configuration.getInstance();
        final ScheduledExecutorService timer = Executors.newScheduledThreadPool(3);

        /**
         * add RssSourceManager
         */

        RssSourceManager rssSourceManager = new RssSourceManager();
        DefaultRssSourceLoadService defaultRssSourceLoadService = new DefaultRssSourceLoadService(rssSourceManager);

        timer.scheduleAtFixedRate(defaultRssSourceLoadService,1, 1 , TimeUnit.SECONDS);


        timer.scheduleAtFixedRate(rssSourceManager,1, 2 , TimeUnit.SECONDS);



        final ExecutorService executorService = Executors.newFixedThreadPool(conf.getThreadNum());

        for(int i = 0; i < conf.getThreadNum(); i ++) {
            executorService.execute(new Worker());
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                shutdownService(executorService);
                shutdownService(timer);
            }
        });
    }

    private static void shutdownService(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) { //optional *
                LOGGER.error("Executor did not terminate in the specified time."); //optional *
                List<Runnable> droppedTasks = executorService.shutdownNow(); //optional **
                LOGGER.error("Executor was abruptly shut down. " + droppedTasks.size() + " tasks will not be executed."); //optional **
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
