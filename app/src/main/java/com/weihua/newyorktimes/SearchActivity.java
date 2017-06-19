package com.weihua.newyorktimes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {

    private static final int SPAN_COUNT = 2;

    private RecyclerView searchResultView;
    private RecyclerView.Adapter searchResultAdapter;
    private RecyclerView.LayoutManager searchResultLayoutManager;

    private Button searchButton;

    private List<Article> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchButton = (Button) findViewById(R.id.search_button);

        searchResultView = (RecyclerView) findViewById(R.id.search_result);

        searchResultLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        searchResultView.setLayoutManager(searchResultLayoutManager);

        searchResultAdapter = new SearchResultAdapter(this, articles);
        searchResultView.setAdapter(searchResultAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                try {
                    fetchData();
                } catch (MalformedURLException e) {
                    Log.e("Search", "search error");
                    e.printStackTrace();
                }
            }
        });
    }

    private void fetchData() throws MalformedURLException {
        AsyncHttpClient client = new AsyncHttpClient();
        URL url = new SearchUrlBuilder().setBeginDate("20170101").setQuery("education").setSort(SearchUrlBuilder.SORT_TYPE.NEWEST).build();
        client.get(url.toExternalForm(), new JsonHttpResponseHandler() {
            @Override public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    articles.addAll(ArticleFactory.createArticles(response));
                    searchResultAdapter.notifyDataSetChanged();
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
