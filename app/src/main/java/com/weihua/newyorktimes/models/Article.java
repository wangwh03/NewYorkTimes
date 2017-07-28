package com.weihua.newyorktimes.models;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import java.io.Serializable;

@AutoValue
public abstract class Article implements Serializable {
    @Nullable public abstract String getThumbnail();
    @Nullable public abstract String getHeadline();
    public abstract String getLeadParagraph();
    public abstract String getByline();
    @Nullable public abstract String getPublishedDate();
    @Nullable public abstract String getAbstractContent();
    @Nullable public abstract String getTitle();

    public static Builder builder() {
        return new AutoValue_Article.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setThumbnail(String thumbnail);
        public abstract Builder setHeadline(String headline);
        public abstract Builder setLeadParagraph(String  leadParagraph);
        public abstract Builder setByline(String byline);
        public abstract Builder setPublishedDate(String publishedDate);
        public abstract Builder setAbstractContent(String abstractContent);
        public abstract Builder setTitle(String title);
        public abstract Article build();
    }
}
