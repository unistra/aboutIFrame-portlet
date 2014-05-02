<%--
    Copyright (C) 2012 Université de Strasbourg (di-dip@unistra.fr)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
--%>

<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:set var="n"><portlet:namespace/></c:set>
<c:set var="iframeAttrs">
  <c:forEach var="attrEntry" items="${attrs}">
    <c:if test="${not empty attrEntry.value and 'src' != attrEntry.key}">${attrEntry.key}="${attrEntry.value}" </c:if>
  </c:forEach>
</c:set>

<iframe src="${attrs.src}" ${iframeAttrs}>
  <div class="fl-widget portlet" role="section">
    <div class="fl-widget-content portlet-section-body" role="region">
      <div class="portlet-section-note" role="note"><spring:message code="noBrowserIFrameSupport"/></div>
    </div>
    <div class="fl-widget-content portlet-section-footer" role="region">
      <a href="${attrs.src}" target="${attrs.id}"><spring:message code="getToContent"/></a> <spring:message code="inNewWindow"/>
    </div>
  </div>
</iframe>
<script type="text/javascript">
  //<![CDATA[
  /*********************************************
  Resize iframe
  *********************************************/
  (function($){
    $(document).ready(function(){

          var resizeFrames = function() {
              $('iframe#${attrs.id}').each(function(){
                  var height_window = $(window).height();
                  var extra_height = $('#portalPageHeader').height()
                                   + $('#portalNavigation').height()
                                   + 100 // spaces and up-portlet-titlebar
                                   + $('#portalPageFooter').height();

                  // do not open other iframe
                  // direct child of up-portlet-content-wrapper-inner
                  if($(this).parent().get(0).className == 'up-portlet-content-wrapper-inner') {
                      newHeight = height_window-extra_height;
                      if (newHeight > 100) {
                        this.height=newHeight+"px";
                      }

                      //$('#portalPageFooter').hide();
                  }
              });
          }

          $(window).resize(function() {
              resizeFrames();
          });

          resizeFrames();
      });
    })(up.jQuery);
  //]]>
</script>
