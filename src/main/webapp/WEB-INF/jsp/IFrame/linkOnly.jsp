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
  <div class="fl-widget-content portlet-section-footer" role="region">
    <c:choose>
      <c:when test="${isOpenExternal}">
        <a href="${attrs.src}" target="${attrs.id}"><spring:message code="getToContent"/></a> <spring:message code="inNewWindow"/>
      </c:when>
      <c:otherwise>
        <a href="<portlet:renderURL windowState="maximized" portletMode="view" />"><spring:message code="getToContent"/></a>
      </c:otherwise>
    </c:choose>
  </div>