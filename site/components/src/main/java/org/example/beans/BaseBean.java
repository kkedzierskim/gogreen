package org.example.beans;

import org.apache.commons.lang.ArrayUtils;
import org.hippoecm.hst.content.beans.ContentNodeBinder;
import org.hippoecm.hst.content.beans.ContentNodeBindingException;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.index.IndexField;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSetBean;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.util.Calendar;

@Node(jcrType="gogreen:basedocument")
public class BaseBean extends HippoDocument implements ContentNodeBinder {

    public static final Logger log = LoggerFactory.getLogger(BaseBean.class);

    private String title;
    private String summary;
    private String html;

    protected final static String HTML_NODEPATH = "gogreen:body";

    @IndexField
    public String getTitle() {
        return title == null ? getSingleProperty("gogreen:title"): title ;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @IndexField(name="summary")
    public String getSummary() {
        return summary == null ? getSingleProperty("gogreen:summary"): summary ;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @IndexField
    public HippoHtml getHtml(){
        return getHippoHtml(HTML_NODEPATH);
    }

    public void setHtml(String html) throws ContentNodeBindingException {
        this.html = html;
    }

    public void addHtml(String body) throws RepositoryException {
        javax.jcr.Node n = this.getNode().addNode(HTML_NODEPATH, "hippostd:html");
        n.setProperty("hippostd:content", body);
    }

    /**
     * to be overridden by beans having a date. By having it in the basebean as well, 
     * the jsp el can always try a var.date without getting an expression language exception
     * @return Calendar obj of the bean or <code>null</code>
     */
    public Calendar getDate() {
        return null;
    }

    public boolean isPublished() {
        String[] availability = getMultipleProperty("hippo:availability");
        return ArrayUtils.contains(availability, "live");
    }

    /**
     * to be overridden by beans having an gallery image. By having it in the basebean as well, 
     * the jsp el can always try a var.image without getting an expression language exception
     * @return
     */
    public HippoGalleryImageSetBean getImage(){
        return null;
    }

    public boolean bind(Object content, javax.jcr.Node node) throws ContentNodeBindingException {
        try {
            BaseBean bean =  (BaseBean) content;
            node.setProperty("gogreen:title", bean.getTitle());
            node.setProperty("gogreen:summary", bean.getSummary());

            if(this.html != null) {
                if(node.hasNode(HTML_NODEPATH)) {
                    javax.jcr.Node htmlNode = node.getNode(HTML_NODEPATH);
                    if(!htmlNode.isNodeType("hippostd:html")) {
                        throw new ContentNodeBindingException("Expected html node of type 'hippostd:html' but was '"+htmlNode.getPrimaryNodeType().getName()+"'");
                    }
                    htmlNode.setProperty("hippostd:content", html);
                } else {
                    javax.jcr.Node html =  node.addNode(HTML_NODEPATH, "hippostd:html");
                    html.setProperty("hippostd:content", html);
                }
            }
        } catch (Exception e) {
            throw new ContentNodeBindingException(e);
        }

        return true;
    }
}
        
        
        
/*
        
        
        extends HippoDocument {

}
*/
