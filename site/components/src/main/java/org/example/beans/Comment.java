package org.example.beans;

import org.hippoecm.hst.content.beans.ContentNodeBindingException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import java.util.Calendar;

@HippoEssentialsGenerated(internalName = "gogreen:Comment")
@Node(jcrType = "gogreen:Comment")
public class Comment extends TextBean {

    private Calendar date;
    private String commentToUuidOfHandle;

    @Override
    public Calendar getDate() {
        return date == null ? getSingleProperty("gogreen:date"): date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setCommentTo(String commentToUuidOfHandle) {
        this.commentToUuidOfHandle = commentToUuidOfHandle;
    }

    public BaseBean getCommentTo(){
        HippoBean bean = getBean("gogreen:commentlink");
        if(!(bean instanceof CommentLinkBean)) {
            return null;
        }
        CommentLinkBean commentLinkBean = (CommentLinkBean)bean;
        if(commentLinkBean == null) {
            return null;
        }
        HippoBean b = commentLinkBean.getReferencedBean();
        if(b == null || !(b instanceof BaseBean)) {
            return null;
        }
        return (BaseBean)b;
    }

    public boolean bind(Object content, javax.jcr.Node node) throws ContentNodeBindingException {
        super.bind(content, node);
        try {
            BaseBean bean =  (BaseBean) content;
            node.setProperty("gogreen:date", bean.getDate());
            javax.jcr.Node commentLink = null;
            if(node.hasNode("gogreen:commentlink")) {
                commentLink = node.getNode("gogreen:commentlink");
            } else {
                commentLink = node.addNode("gogreen:commentlink", "gogreen:commentlink");
            }
            commentLink.setProperty("hippo:docbase", commentToUuidOfHandle);
            commentLink.setProperty("hippo:values", new String[0]);
            commentLink.setProperty("hippo:modes", new String[0]);
            commentLink.setProperty("hippo:facets", new String[0]);


        } catch (Exception e) {
            throw new ContentNodeBindingException(e);
        }
        return true;
    }

}




/*

    @HippoEssentialsGenerated(internalName = "gogreen:date")
    public Calendar getDate() {
        return getSingleProperty("gogreen:date");
    }

    @HippoEssentialsGenerated(internalName = "gogreen:name")
    public String getName() {
        return getSingleProperty("gogreen:name");
    }

    @HippoEssentialsGenerated(internalName = "gogreen:comment")
    public HippoHtml getComment() {
        return getHippoHtml("gogreen:comment");
    }
}
*/


