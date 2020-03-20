package org.example.components;

import org.example.beans.Blogpost;
import org.example.beans.Comment;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.annotations.Persistable;
import org.hippoecm.hst.content.beans.ObjectBeanPersistenceException;
import org.hippoecm.hst.content.beans.manager.workflow.BaseWorkflowCallbackHandler;
import org.hippoecm.hst.content.beans.manager.workflow.WorkflowPersistenceManager;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocumentBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.utils.SimpleHtmlExtractor;
import org.onehippo.repository.documentworkflow.DocumentWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Calendar;



public class Detail extends BaseHstComponent {

    public static final Logger log = LoggerFactory.getLogger(Detail.class);


    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        log.warn("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% RENDER %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        super.doBeforeRender(request, response);
        HstRequestContext requestContext = request.getRequestContext();
        HippoBean crBean = requestContext.getContentBean();

        if (crBean != null) {
            // Put the document on the request
            request.setAttribute("document", crBean);
        }

    }

    @Persistable
    @Override
    public void doAction(HstRequest request, HstResponse response) throws HstComponentException {
        HstRequestContext requestContext = request.getRequestContext();

        log.warn("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% action %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        String title = request.getParameter("title");
        String comment = request.getParameter("comment");
        HippoBean commentTo = request.getRequestContext().getContentBean();
        if (!(commentTo instanceof HippoDocumentBean)) {
            log.warn("Cannot comment on non documents");
            return;
        }
        String commentToUuidOfHandle = ((HippoDocumentBean) commentTo).getCanonicalHandleUUID();
        if (title != null && !"".equals(title.trim()) && comment != null) {
            WorkflowPersistenceManager wpm = null;

            try {
                wpm = getWorkflowPersistenceManager(requestContext.getSession());
                wpm.setWorkflowCallbackHandler(new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
                    public void processWorkflow(DocumentWorkflow wf) throws Exception {
                        wf.requestPublication();
                    }
                });

                // it is not important where we store comments. WE just use some timestamp path below our project content
                String siteCanonicalBasePath = request.getRequestContext().getResolvedMount().getMount().getContentPath();
                Calendar currentDate = Calendar.getInstance();

                String commentsFolderPath = siteCanonicalBasePath + "/comment/" + currentDate.get(Calendar.YEAR) + "/"
                        + currentDate.get(Calendar.MONTH) + "/" + currentDate.get(Calendar.DAY_OF_MONTH);
                // comment node name is simply a concatenation of 'comment-' and current time millis.
                String commentNodeName = "comment-for-" + commentTo.getName() + "-" + System.currentTimeMillis();

                // create comment node now
                wpm.createAndReturn(commentsFolderPath, "gogreen:comment", commentNodeName, true);

                // retrieve the comment content to manipulate
                Comment commentBean = (Comment) wpm.getObject(commentsFolderPath + "/" + commentNodeName);
                // update content properties
                if (commentBean == null) {
                    throw new HstComponentException("Failed to add Comment");
                }
                commentBean.setTitle(SimpleHtmlExtractor.getText(title));

                commentBean.setHtml(SimpleHtmlExtractor.getText(comment));

                commentBean.setDate(currentDate);

                commentBean.setCommentTo(commentToUuidOfHandle);

                // update now
                wpm.update(commentBean);

            } catch (Exception e) {
                log.warn("Failed to create a comment: ", e);

                if (wpm != null) {
                    try {
                        wpm.refresh();
                    } catch (ObjectBeanPersistenceException e1) {
                        log.warn("Failed to refresh: ", e);
                    }
                }
            }
        }
    }
}