<#include "../../include/imports.ftl">
<#-- @ftlvariable name="document" type="org.example.beans.Blogpost" -->
<@hst.setBundle basename="essentials.blog"/>
<#if document??>
  <@hst.link var="link" hippobean=document/>
  <div class="blog-post has-edit-button">
    <@hst.manageContent hippobean=document/>
    <div class="blog-post-type">
      <i class="icon-news"> </i>
    </div>
    <div class="blog-span">
      <#if document.image?? && document.image.large??>
        <@hst.link var="img" hippobean=document.image.large/>
        <div class="blog-post-featured-img">
          <img src="${img}" alt="${document.title?html}" />
        </div>
      </#if>
      <h2>${document.title?html}</h2>
      <div class="blog-post-body">
        <p>${document.introduction?html}</p>
        <@hst.html hippohtml=document.content/>
      </div>
      <div class="blog-post-details">
        <div class="blog-post-details-item blog-post-details-item-left icon-calendar">
            <strong>
            <#if document.publicationDate??>
             <@fmt.formatDate type="date" pattern="yyyy-MM-dd" value=document.publicationDate.time/>
             </#if>
             </strong>
        </div>
        <div>
        <h2>Comments</h2>
            <@hst.actionURL var="actionLink"/>
               <form method="post" action="${actionLink}">
                 <h4>Enter your comment here:</h4>
                 <table>
                   <tr>
                     <th><label for="title">Title:</label></th>
                     <td><input type="text" id="title" name="title" value="" /></td>
                   </tr>
                   <tr>
                     <th valign="top"><label for="comment">Comment:</label></th>
                     <td><textarea name="comment" id="comment" rows="4" cols="40"></textarea></td>
                   </tr>
                   <tr>
                     <td colspan="2">
                       <input type="submit" value="Submit"/>
                       <input type="reset" value="Reset"/>
                     </td>
                    </tr>
                 </table>
               </form>
        </div>
      </div>
    </div>
  </div>
</#if>
