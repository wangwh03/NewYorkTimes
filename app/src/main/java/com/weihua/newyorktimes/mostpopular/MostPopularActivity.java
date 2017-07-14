package com.weihua.newyorktimes.mostpopular;

import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.weihua.newyorktimes.R;
import com.weihua.newyorktimes.activities.BaseActivity;
import com.weihua.newyorktimes.client.NewYorkTimesClient;
import com.weihua.newyorktimes.models.Article;
import com.weihua.newyorktimes.models.ArticleFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import static com.weihua.newyorktimes.client.NewYorkTimesClient.API_KEY;

public class MostPopularActivity extends BaseActivity {
    private static final String URL = "https://api.nytimes.com/svc/mostpopular/v2/mostemailed/{section}/{time-period}.json";

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_most_popular);
    }

    @Override
    protected void customizedSetUp() {
    }

    @Override
    protected void fetchData() {
        try {
            String url = URL.replace("{section}", "Sports")
                .replace("{time-period}", String.valueOf(NewYorkTimesClient.TimePeriod.DAY.getDaysValue()));
            url += "?api-key=" + API_KEY;
            fetchData(new URL(url));
        } catch (MalformedURLException e) {
            Log.e("Search", "search error");
            e.printStackTrace();
        }
    }

    protected void fetchData(URL url) throws MalformedURLException {
        client.get(url.toExternalForm(), new JsonHttpResponseHandler() {
            @Override public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    articles.addAll(parseArticles(response));
                    articlesAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(this.getClass().getSimpleName(), responseString);
            }
        });
    }

    protected List<Article> parseArticles(JSONObject response) throws JSONException {
        return ArticleFactory.createArticlesForMostPopular(response);
    }
}
