package com.weihua.newyorktimes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {

    private List<Article> articles;
    private Context context;

    public SearchResultAdapter(Context context, List<Article> articles) {
        this.articles = articles;
        this.context = context;
    }

    @Override public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        SearchResultViewHolder searchResultViewHolder = new SearchResultViewHolder(view);
        return searchResultViewHolder;
    }

    @Override public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        final Article article = articles.get(position);

        holder.headlineView.setText(article.getHeadline());

        if (article.getThumbnail() != "") {
            Picasso.with(context).load(article.getThumbnail()).placeholder(R.drawable.ic_placeholder_image_thumbnail).into(holder.thumbnailView);
        }

        return;
    }

    @Override public int getItemCount() {
        return articles.size();
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder {
        protected ImageView thumbnailView;
        protected TextView headlineView;

        public SearchResultViewHolder(View itemView) {
            super(itemView);
            thumbnailView = (ImageView) itemView.findViewById(R.id.thumbnail);
            headlineView = (TextView) itemView.findViewById(R.id.headline);
        }
    }
}
