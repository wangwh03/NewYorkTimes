package com.weihua.newyorktimes.search;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import com.weihua.newyorktimes.R;
import com.weihua.newyorktimes.activities.BaseActivity;
import com.weihua.newyorktimes.activities.BaseActivity2;
import com.weihua.newyorktimes.models.Article;
import com.weihua.newyorktimes.models.ArticleFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import static com.weihua.newyorktimes.search.SearchUrlBuilder.SORT_TYPE.NEWEST;

public class SearchActivity extends BaseActivity {
    private static final String DEFAULT_CATEGORY = "Movie";
    private static final String DEFAULT_SEARCH_BEGIN_DATE = "20170101";

    @BindView(R.id.search_query) EditText searchQuery;
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
            URL url = new SearchUrlBuilder()
                .setBeginDate(DEFAULT_SEARCH_BEGIN_DATE)
                .setQuery(searchQuery.getText().toString().equals("") ? DEFAULT_CATEGORY : searchQuery.getText().toString())
                .setSort(NEWEST)
                .build();
            fetchData(url);
        } catch (MalformedURLException e) {
            Log.e("Search", "search error");
            e.printStackTrace();
        }
    }

    @Override
    protected List<Article> parseArticles(JSONObject response) throws JSONException {
        return ArticleFactory.createArticlesForSearch(response);
    }
}
