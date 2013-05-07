package com.gozap.chouti.RssSource;

import com.gozap.chouti.Common;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: saint
 * Date: 13-4-28
 * Time: 下午4:22
 * To change this template use File | Settings | File Templates.
 */
public class RssSource {
    /**
     * 抓取的间隔时间，单位s
     */
    private long ttl;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * feed的url
     */
    private String url;

    /**
     * rss站点的名称
     */
    private String name;
    /**
     * 当前是否可用
     */
    private boolean error;


    /**
     * rss 源地址
     */

    private String type;

    /**
     * 最后更新时间
     */
    private long updatedAt;

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt.getTime();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setType(int type) {
        this.type = Common.rssSourceType.getName(type);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RssSource rssSource = (RssSource) o;
        return !(url != null ? !url.equals(rssSource.url) : rssSource.url != null);
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RssSource{" +
                "ttl=" + ttl +
                ", userName='" + userName + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", error=" + error +
                ", type='" + type + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
