package com.weihua.newyorktimes.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArticleFactory {
    private static final String THUMBNAIL_PREFIX = "http://www.nytimes.com/";

    public static ArrayList<Article> createArticlesForMostPopular(JSONObject response) throws JSONException {
        JSONArray articlesJson = response.getJSONArray("results");
        ArrayList<Article> articles = new ArrayList<>();
        for (int i = 0; i < articlesJson.length(); i++) {
            articles.add(createArticleForMostPopular(articlesJson.getJSONObject(i)));
        }
        return articles;
    }

    private static Article createArticleForMostPopular(JSONObject jsonObject) throws JSONException {
        return Article.builder()
            .setTitle(jsonObject.getString("title"))
            .setAbstractContent(jsonObject.getString("abstract"))
            .build();
    }

    public static ArrayList<Article> createArticlesForSearch(JSONObject response) throws JSONException {
        JSONArray articlesJson = response.getJSONObject("response").getJSONArray("docs");
        ArrayList<Article> articles = new ArrayList<>();
        for (int i = 0; i < articlesJson.length(); i++) {
            articles.add(createArticleForSearch(articlesJson.getJSONObject(i)));
        }
        return articles;
    }

    private static Article createArticleForSearch(JSONObject articleJson) throws JSONException {
        String publishedDate = null;
        try {
            publishedDate = formatDate(articleJson.getString("pub_date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Article.builder()
            .setThumbnail(getThumbnail(articleJson))
            .setHeadline(articleJson.getJSONObject("headline").getString("main"))
            .setLeadParagraph(articleJson.getString("lead_paragraph"))
            .setByline(articleJson.getJSONObject("byline").getString("original").replace("By ", ""))
            .setPublishedDate(publishedDate)
            .build();
    }

    private static String formatDate(String dateString) throws ParseException {
        SimpleDateFormat sourceDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'H:mm:ssZ");
        Date time = sourceDateFormat.parse(dateString);

        SimpleDateFormat desiredDateFormat = new SimpleDateFormat("MMM d");
        return desiredDateFormat.format(time).toString();
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
