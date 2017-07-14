package com.weihua.newyorktimes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import com.weihua.newyorktimes.R;
import com.weihua.newyorktimes.models.Article;
import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {

    private List<Article> articles;
    private Context context;

    public ArticlesAdapter(Context context, List<Article> articles) {
        this.articles = articles;
        this.context = context;
    }

    @Override public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        ArticleViewHolder articleViewHolder = new ArticleViewHolder(view);
        return articleViewHolder;
    }

    @Override public void onBindViewHolder(ArticleViewHolder holder, int position) {
        final Article article = articles.get(position);

        holder.headlineView.setText(article.getHeadline());
        holder.titleTextView.setText(article.getTitle());
        holder.abstractContentTextView.setText(article.getAbstractContent());

        if (article.getThumbnail() != "") {
            Picasso.with(context).load(article.getThumbnail()).placeholder(R.drawable.ic_placeholder_image_thumbnail).into(holder.thumbnailView);
        } else {
            holder.thumbnailView.setVisibility(View.GONE);
        }

        return;
    }

    @Override public int getItemCount() {
        return articles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail) ImageView thumbnailView;
        @BindView(R.id.headline) TextView headlineView;
        @BindView(R.id.title) TextView titleTextView;
        @BindView(R.id.abstract_content) TextView abstractContentTextView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
