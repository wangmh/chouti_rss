package com.gozap.chouti.model;

import java.util.List;


public class RssFeed {

    private RssChannel channel;
    private RssImage image;
    private List<RssItem> items;


    /**
     * Returns the feed channel element
     *
     * @return the feed channel element
     */
    public RssChannel getChannel() {
        return channel;
    }

    /**
     * Sets the feed channel element
     *
     * @param channel The feed channel element
     */
    public void setChannel(RssChannel channel) {
        this.channel = channel;
    }

    /**
     * Returns the feed image element
     *
     * @return the feed image element
     */
    public RssImage getImage() {
        return image;
    }

    /**
     * Sets the feed image element
     *
     * @param image The feed image element
     */
    public void setImage(RssImage image) {
        this.image = image;
    }

    /**
     * Returns the feed items list
     *
     * @return List of RssItem
     */
    public List<RssItem> getItems() {
        return items;
    }

    /**
     * Sets the feed items list
     *
     * @param items The feed item list
     */
    public void setItems(List<RssItem> items) {
        this.items = items;
    }

}
