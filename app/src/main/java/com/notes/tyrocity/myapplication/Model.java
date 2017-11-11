package com.notes.tyrocity.myapplication;

/**
 * Created by root on 11/8/17.
 */

public class Model {

    String country;
    String url;

    public Model(String country, String url) {
        this.country = country;
        this.url = url;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
