<#include "../../include/imports.ftl">
<#-- @ftlvariable name="pageable" type="org.onehippo.cms7.essentials.components.paging.Pageable" -->
<@hst.setBundle basename="essentials.blog"/>
  <#if pageable??>
    <#list pageable.items as item>
      <@hst.link var="link" hippobean=item />
        <div class="blog-post has-edit-button">
          <@hst.manageContent hippobean=item/>
          <div class="blog-post-type">
            <i class="icon-news"> </i>
          </div>
          <div class="blog-span">
          <#if item.image?? && item.image.large??>
                  <@hst.link var="img" hippobean=item.image.large/>
                  <div class="blog-post-featured-img">
                    <img src="${img}" alt="${item.title?html}" />
                  </div>
          </#if>
          <h2>
            <a href="${link}">${item.title?html}</a>
          </h2>
          <div class="blog-post-body">
            <p>${item.introduction?html}</p>
          </div>
           <div class="has-new-content-button">
            <@hst.manageContent documentTemplateQuery="new-blog-document" folderTemplateQuery="new-blog-folder" rootPath="blog" defaultPath="${currentYear}/${currentMonth}"/>
          </div>
          <div class="blog-post-details">
            <div class="blog-post-details-item blog-post-details-item-left icon-calendar">
              <#if item.publicationDate?? && item.publicationDate.time??>
                 <p><@fmt.formatDate value=item.publicationDate.time type="both" dateStyle="medium" timeStyle="short"/></p>
             </#if>
            </div>
            <div class="blog-post-details-item blog-post-details-item-right">
              <a href="${link}"> Read more<i class="fa fa-chevron-right"></i></a>
            </div>
          </div>
        </div>
      </div>
    </#list>
    <#if cparam.showPagination>
      <#include "../../include/pagination.ftl">
    </#if>
  <#elseif editMode>
<div>
  <img src="<@hst.link path='/images/essentials/catalog-component-icons/blog-list.svg'/>"> Click to edit Blog List
</div>
  </#if>
