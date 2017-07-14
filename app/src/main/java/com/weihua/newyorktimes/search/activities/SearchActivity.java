package com.weihua.newyorktimes.search.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import com.weihua.newyorktimes.R;
import com.weihua.newyorktimes.mostpopular.MostPopularActivity;
import com.weihua.newyorktimes.search.utils.SearchUrlBuilder;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchActivity extends MostPopularActivity {
    @BindView(R.id.search_button) Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                fetchData();
            }
        });
    }

    private void fetchData() {
        try {
            URL url = new SearchUrlBuilder().setBeginDate("20170101").setQuery("education").setSort(SearchUrlBuilder.SORT_TYPE.NEWEST).build();
            fetchData(url);
        } catch (MalformedURLException e) {
            Log.e("Search", "search error");
            e.printStackTrace();
        }
    }

}
