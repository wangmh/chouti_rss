package com.gozap.chouti.utils;

import com.gozap.chouti.RssSource.RssSourceManager;
import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * Created with IntelliJ IDEA.
 * User: saint
 * Date: 13-5-2
 * Time: 上午11:59
 * To change this template use File | Settings | File Templates.
 */
public class SignalHandlerImpl implements SignalHandler {
    private RssSourceManager rssSourceManager;
    public SignalHandlerImpl(RssSourceManager rssSourceManager) {
        this.rssSourceManager = rssSourceManager;
    }

    @Override
    public void handle(Signal signal) {
        System.out.println(signal);
        System.exit(1);
    }
}
