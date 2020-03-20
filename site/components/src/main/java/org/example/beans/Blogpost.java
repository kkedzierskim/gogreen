package org.example.beans;

import java.util.Calendar;
import java.util.List;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.components.model.Authors;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.example.beans.Imageset;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@HippoEssentialsGenerated(internalName = "gogreen:blogpost")
@Node(jcrType = "gogreen:blogpost")
public class Blogpost extends TextBean implements Authors {
    public static final String TITLE = "gogreen:title";
    public static final String INTRODUCTION = "gogreen:introduction";
    public static final String CONTENT = "gogreen:content";
    public static final String PUBLICATION_DATE = "gogreen:publicationdate";
    public static final String CATEGORIES = "gogreen:categories";
    public static final String AUTHOR = "gogreen:author";
    public static final String AUTHOR_NAMES = "gogreen:authornames";
    public static final String LINK = "gogreen:link";
    public static final String AUTHORS = "gogreen:authors";
    public static final String TAGS = "hippostd:tags";

    @HippoEssentialsGenerated(internalName = "gogreen:publicationdate")
    public Calendar getPublicationDate() {
        return getSingleProperty(PUBLICATION_DATE);
    }

    @HippoEssentialsGenerated(internalName = "gogreen:authornames")
    public String[] getAuthorNames() {
        return getMultipleProperty(AUTHOR_NAMES);
    }

    public String getAuthor() {
        final String[] authorNames = getAuthorNames();
        if (authorNames != null && authorNames.length > 0) {
            return authorNames[0];
        }
        return null;
    }

    @HippoEssentialsGenerated(internalName = "gogreen:title")
    public String getTitle() {
        return getSingleProperty(TITLE);
    }

    @HippoEssentialsGenerated(internalName = "gogreen:content")
    public HippoHtml getContent() {
        return getHippoHtml(CONTENT);
    }

    @HippoEssentialsGenerated(internalName = "gogreen:introduction")
    public String getIntroduction() {
        return getSingleProperty(INTRODUCTION);
    }

    @HippoEssentialsGenerated(internalName = "gogreen:categories")
    public String[] getCategories() {
        return getMultipleProperty(CATEGORIES);
    }

    @Override
    @HippoEssentialsGenerated(internalName = "gogreen:authors")
    public List<Author> getAuthors() {
        return getLinkedBeans(AUTHORS, Author.class);
    }

    @HippoEssentialsGenerated(internalName = "gogreen:image")
    public Imageset getImage() {
        return getLinkedBean("gogreen:image", Imageset.class);
    }

    @HippoEssentialsGenerated(internalName = "gogreen:comments")
    public List<HippoBean> getComments() {
        return getLinkedBeans("gogreen:comments", HippoBean.class);
    }
}
