package com.weihua.newyorktimes.mostpopular;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.weihua.newyorktimes.R;
import com.weihua.newyorktimes.client.NewYorkTimesClient;
import com.weihua.newyorktimes.models.Article;
import com.weihua.newyorktimes.models.ArticleFactory;
import com.weihua.newyorktimes.search.adapters.ArticlesAdapter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import static com.weihua.newyorktimes.client.NewYorkTimesClient.API_KEY;

public class MostPopularActivity extends AppCompatActivity {
    private static final int SPAN_COUNT = 2;
    private static final String URL = "https://api.nytimes.com/svc/mostpopular/v2/mostemailed/{section}/{time-period}.json";

    AsyncHttpClient client = new AsyncHttpClient();

    @BindView(R.id.articles) RecyclerView articlesView;
    private RecyclerView.Adapter articlesAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Article> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_popular);
        ButterKnife.bind(this);

        layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        articlesView.setLayoutManager(layoutManager);

        articlesAdapter = new ArticlesAdapter(this, articles);
        articlesView.setAdapter(articlesAdapter);

        fetchData("Sports", NewYorkTimesClient.TimePeriod.DAY);
    }

    private void fetchData(String section, NewYorkTimesClient.TimePeriod timePeriod) {
        try {
            String url = URL.replace("{section}", section)
                .replace("{time-period}", String.valueOf(timePeriod.getDaysValue()));
            url += "?api-key=" + API_KEY;
            fetchData(new URL(url));
        } catch (MalformedURLException e) {
            Log.e("Search", "search error");
            e.printStackTrace();
        }
    }

    protected void fetchData(URL url) throws MalformedURLException {
        Log.d("most popular", url.toString());
        client.get(url.toExternalForm(), new JsonHttpResponseHandler() {
            @Override public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    articles.addAll(ArticleFactory.createArticlesForMostPopular(response));
                    articlesAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("SearchActivity", responseString);
            }
        });
    }
}
