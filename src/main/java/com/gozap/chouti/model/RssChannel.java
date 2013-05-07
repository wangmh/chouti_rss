

package com.gozap.chouti.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class RssChannel {
    //For RSS 2.0, RDF y ATOM
    private String title;
    private String link;
    private String description;
    private Date pubDate;
    private int ttl;
    private static final int DEFAULT_TTL = 10;

    private Map<String, Object> additionalInfo;

    /**
     * Create a new RssChannel.
     */
    public RssChannel() {
        this.title = "";
        this.link = "";
        this.description = "";
        this.ttl = DEFAULT_TTL * 60;
    }

    /**
     * Create a new RssChannel.
     *
     * @param title       the title of the feed
     * @param link        the URL to the feed web page
     * @param description the description of the feed
     */
    public RssChannel(String title, String link, String description, int ttl) {
        this.title = title;
        this.link = link;
        this.ttl = ttl * 60;
        this.description = description;
    }

    /**
     * Adds an object with additional info identified by the string name
     *
     * @param name   The string identifying the object
     * @param object The object to add
     */
    public void putAdditionalInfo(String name, Object object) {
        if (this.additionalInfo == null) this.additionalInfo = new HashMap<String, Object>();
        this.additionalInfo.put(name, object);
    }

    /**
     * Get an object with additional info identified by the string name
     *
     * @param name The string identifying the object
     * @return An object with additional info
     */
    public Object getAdditionalInfo(String name) {
        return this.additionalInfo.get(name);
    }

    /**
     * Sets the title of the feed
     *
     * @param title the title of the feed
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the description of the feed
     *
     * @param description the description of the feed
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the URL to the feed's web page
     *
     * @param link The URL to the feed's web page
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Sets the publication date of the feed
     *
     * @param pubDate the publication date of the feed
     */
    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    /**
     * Returns the title of the feed
     *
     * @return Title of the feed
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the description of the feed
     *
     * @return Description of the feed
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the URL of the feed's web page
     *
     * @return URL of the feed's web page
     */
    public String getLink() {
        return this.link;
    }

    /**
     * Returns the last modification date of the feed
     *
     * @return The last modification date of the feed
     */
    public Date getPubDate() {
        return this.pubDate;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public void setTtl(String ttlStr) {
        if (ttlStr == null) {
            ttl  = DEFAULT_TTL * 60;
        } else {
            try {
                ttl = Integer.valueOf(ttlStr) * 60;
            } catch (Throwable e) {
                ttl = DEFAULT_TTL * 60;
            }
        }

    }
}