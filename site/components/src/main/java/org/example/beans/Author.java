package org.example.beans;

import java.util.List;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;
import org.onehippo.cms7.essentials.components.model.AuthorEntry;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.example.beans.Imageset;

@HippoEssentialsGenerated(internalName = "gogreen:author")
@Node(jcrType = "gogreen:author")
public class Author extends HippoDocument implements AuthorEntry {
    public static final String ROLE = "gogreen:role";
    public static final String ACCOUNTS = "gogreen:accounts";
    public static final String FULL_NAME = "gogreen:fullname";
    public static final String IMAGE = "gogreen:image";
    public static final String CONTENT = "gogreen:content";

    @HippoEssentialsGenerated(internalName = "gogreen:fullname")
    public String getFullName() {
        return getSingleProperty(FULL_NAME);
    }

    @HippoEssentialsGenerated(internalName = "gogreen:content")
    public HippoHtml getContent() {
        return getHippoHtml(CONTENT);
    }

    @HippoEssentialsGenerated(internalName = "gogreen:role")
    public String getRole() {
        return getSingleProperty(ROLE);
    }

    @HippoEssentialsGenerated(internalName = "gogreen:accounts")
    public List<Account> getAccounts() {
        return getChildBeansByName(ACCOUNTS, Account.class);
    }

    @HippoEssentialsGenerated(internalName = "gogreen:image")
    public Imageset getImage() {
        return getLinkedBean("gogreen:image", Imageset.class);
    }
}
