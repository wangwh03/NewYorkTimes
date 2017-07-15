package com.weihua.newyorktimes.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.weihua.newyorktimes.R;
import com.weihua.newyorktimes.models.Article;
import com.weihua.newyorktimes.models.ArticleFactory;
import com.weihua.newyorktimes.adapters.ArticlesAdapter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseActivity extends AppCompatActivity {
    @BindView(R.id.articles) RecyclerView articlesView;
    protected ArticlesAdapter articlesAdapter;
    protected RecyclerView.LayoutManager layoutManager;

    protected AsyncHttpClient client = new AsyncHttpClient();
    protected List<Article> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this);
        articlesView.setLayoutManager(layoutManager);

        articlesAdapter = new ArticlesAdapter(this, articles);
        articlesView.setAdapter(articlesAdapter);

        customizedSetUp();
        fetchData();
    }

    protected abstract void setContentView();
    protected abstract void customizedSetUp();
    protected abstract void fetchData();

    protected void fetchData(URL url) throws MalformedURLException {
        client.get(url.toExternalForm(), new JsonHttpResponseHandler() {
            @Override public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    articles.clear();
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

    protected abstract List<Article> parseArticles(JSONObject response) throws JSONException;
}
