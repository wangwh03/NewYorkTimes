package com.weihua.newyorktimes.search;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import com.weihua.newyorktimes.R;
import com.weihua.newyorktimes.models.Article;
import com.weihua.newyorktimes.models.ArticleFactory;
import com.weihua.newyorktimes.activities.BaseActivity;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.search_button) Button searchButton;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_search);
    }

    @Override protected void customizedSetUp() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                fetchData();
            }
        });
    }

    protected void fetchData() {
        try {
            URL url = new SearchUrlBuilder().setBeginDate("20170101").setQuery("education").setSort(SearchUrlBuilder.SORT_TYPE.NEWEST).build();
            fetchData(url);
        } catch (MalformedURLException e) {
            Log.e("Search", "search error");
            e.printStackTrace();
        }
    }

    protected List<Article> parseArticles(JSONObject response) throws JSONException {
        return ArticleFactory.createArticlesForSearch(response);
    }
}
