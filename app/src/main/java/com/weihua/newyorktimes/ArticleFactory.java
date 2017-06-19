package com.weihua.newyorktimes;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArticleFactory {
    private static final String THUMBNAIL_PREFIX = "http://www.nytimes.com/";

    public static ArrayList<Article> createArticles(JSONObject response) throws JSONException {
        JSONArray articlesJson = response.getJSONObject("response").getJSONArray("docs");
        ArrayList<Article> articles = new ArrayList<>();
        for (int i = 0; i < articlesJson.length(); i++) {
            articles.add(createArticle(articlesJson.getJSONObject(i)));
        }
        return articles;
    }

    private static Article createArticle(JSONObject articleJson) throws JSONException {
        return new Article(getThumbnail(articleJson),
                         articleJson.getJSONObject("headline").getString("main"));
    }

    private static String getThumbnail(JSONObject articleJson) throws JSONException {
        JSONArray multimedia = articleJson.getJSONArray("multimedia");
        if (multimedia.length() != 0) {
            for (int i = 0; i < multimedia.length(); i++) {
                JSONObject currentMedia = multimedia.getJSONObject(i);
                if (currentMedia.getString("subtype").equals("thumbnail")) {
                    return THUMBNAIL_PREFIX + currentMedia.getJSONObject("legacy").getString("thumbnail");
                }
            }
        }
        return "";
    }
}
