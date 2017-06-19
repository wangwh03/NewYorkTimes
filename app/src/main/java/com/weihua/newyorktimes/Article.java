package com.weihua.newyorktimes;

import java.io.Serializable;

public class Article implements Serializable {
    private String thumbnail;
    private String headline;

    public Article(String thumbnail, String headline) {
        this.thumbnail = thumbnail;
        this.headline = headline;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }
}
