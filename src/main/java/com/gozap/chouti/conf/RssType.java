package com.gozap.chouti.conf;

/**
 * Created with IntelliJ IDEA.
 * User: saint
 * Date: 13-5-2
 * Time: 下午2:56
 * To change this template use File | Settings | File Templates.
 */
public enum RssType {

    CSV("CSV"), MYSQL("MYSQL");


    private String name;

    public String getName() {
        return name;
    }

    RssType(String csv) {
        this.name = csv;
    }

}
