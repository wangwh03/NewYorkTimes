package com.weihua.newyorktimes.search;

import java.net.MalformedURLException;
import java.net.URL;

public class SearchUrlBuilder {
    public static final String BASE_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json?";
    private static final String API_KEY = "227c750bb7714fc39ef1559ef1bd8329";

    private String beginDate;
    private SORT_TYPE sort;
    private String fq;

    public enum SORT_TYPE {
        NEWEST,
        OLDEST
    }

    public SearchUrlBuilder setBeginDate(String beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public SearchUrlBuilder setSort(SORT_TYPE sort) {
        this.sort = sort;
        return this;
    }

    public SearchUrlBuilder setQuery(String query) {
        this.fq = query;
        return this;
    }

    public URL build() throws MalformedURLException {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL);

        if (beginDate != null) {
            urlBuilder.append("begin_date=").append(beginDate).append("&");
        }

        if (sort != null) {
            urlBuilder.append("sort=").append(sort.toString()).append("&");
        }

        if (fq != null) {
            urlBuilder.append("fq=").append(fq).append("&");
        }

        urlBuilder.append("api-key=").append(API_KEY);

        return new URL(urlBuilder.toString());
    }
}
