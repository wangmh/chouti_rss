
package com.gozap.chouti.model;

import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class RssItem {
    //For all specifications
    private String type;
    private String title;
    private String link;
    private String description;
    private String author;
    private Date pubDate;

    //Only for RSS 2.0
    private String category;

    private Map<String, Object> additionalInfo;

    /**
     * Create a new RssItem.
     */
    public RssItem() {
        title = "";
        link = "";
        description = "";
        author = "";
        category = "";
        pubDate = new Date();
    }

    /**
     * Create a new RssItem.
     *
     * @param title       Title of the item
     * @param link        The link of the item
     * @param description Description of the item
     */
    public RssItem(String title, String link, String description) {
        this.title = title;
        this.link = link;
        this.description = description;
        author = "";
        category = "";
        pubDate = new Date();
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
     * Sets the title of the item
     *
     * @param title Title of the item
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the link of the item
     *
     * @param link Link of the item
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Sets the description of the item
     *
     * @param description Description of the item
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the author of the item
     *
     * @param author Author of the item
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Sets the publication date of the item
     *
     * @param pubDate Publication date of the item
     */
    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    /**
     * Sets the category of the item
     *
     * @param category Category of the item
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns the title of the item
     *
     * @return Title of the item
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the link of the item
     *
     * @return Link of the item
     */
    public String getLink() {
        return link;
    }

    /**
     * Returns the description of the item
     *
     * @return Description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the author of the item
     *
     * @return Author of the item
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * Returns the publication date of the item
     *
     * @return Publication date of the item
     */
    public Date getPubDate() {
        return this.pubDate;
    }

    public boolean isValidate() {
        return ((!StringUtils.isBlank(getTitle())) && (!StringUtils.isBlank(getLink())) );
    }

    /**
     * Returns the category of the item
     *
     * @return Category of the item
     */
    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "RssItem{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", pubDate=" + pubDate +
                ", category='" + category + '\'' +
                ", additionalInfo=" + additionalInfo +
                '}';
    }
}