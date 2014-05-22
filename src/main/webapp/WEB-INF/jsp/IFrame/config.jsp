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
<rs:aggregatedResources path="skin-config.xml"/>
<portlet:actionURL var="formUrl"><portlet:param name="action" value="update" /></portlet:actionURL>
<portlet:actionURL var="cancelUrl"><portlet:param name="action" value="cancel" /></portlet:actionURL>
<div class="fl-widget portlet" role="section">
  <form:form id="${n}form" commandName="form" action="${formUrl}" method="POST">
	  <div class="fl-widget-titlebar titlebar portlet-titlebar" role="sectionhead">
	    <h2 role="heading"><spring:message code="config.title"/></h2>
	    <p><spring:message code="config.subTitle"/></p>
	  </div>
	  <div class="fl-widget-content content portlet-content" role="main">
		  <div class="panel portlet-section-body" role="region">
	      <form:label path="url"><spring:message code="config.url"/></form:label>
	      <form:input path="url" type="url" /><br/>
	      <form:label path="openExternal"><spring:message code="config.openExternal"/></form:label>
		    <form:checkbox path="openExternal" /><br/>
          <div class="fl-widget up-portlet-wrapper">
            <div class="up-portlet-wrapper-inner">
              <div class="fl-widget-titlebar up-portlet-titlebar">
                <h2><a href="#"><form:label path="about"><spring:message code="config.about"/></form:label></a></h2>
              </div>
              <div class="fl-widget-content fl-fix up-portlet-content-wrapper">
                <div class="up-portlet-content-wrapper-inner">
                  <div class="fl-widget portlet view" role="section">
                    <div class="fl-widget-content portlet-section-body flc-inlineEdit-text" role="region">
                      <div id="${n}about" class="portlet-section-note text" role="note">${form.about}</div>
                    </div>
                    <div class="fl-widget-content portlet-section-footer" role="region">
                      <a href='#_' class='flc-inlineEdit-textEditButton'>Modify...</a>
                    </div>
          <div class="fl-widget-content flc-inlineEdit-editContainer">
            <form:textarea path="about" cssClass="fl-widget-content" />
          </div>
                  </div>
                </div>
              </div>
               <div class="up-portlet-wrapper-bottom">
                 <div class="up-portlet-wrapper-bottom-inner"></div>
               </div>
             </div>
          </div>
          <div class="fl-widget up-portlet-wrapper">
            <div class="up-portlet-wrapper-inner">
              <div class="fl-widget-titlebar up-portlet-titlebar">
                <h2><a href="#"><form:label path="help"><spring:message code="config.help"/></form:label></a></h2>
              </div>
              <div class="fl-widget-content fl-fix up-portlet-content-wrapper">
                <div class="up-portlet-content-wrapper-inner">
                  <div class="fl-widget portlet view" role="section">
                    <div class="fl-widget-content portlet-section-body " role="region">
                      <div id="${n}help" class="portlet-section-note " role="note">${form.help}</div>
                    </div>
                    <div class="fl-widget-content portlet-section-footer" role="region">
                      <a href='#_' class=''>Modify...</a>
                    </div>
          <div class="fl-widget-content ">
            <form:textarea path="help" cssClass="fl-widget-content" />
          </div>
                  </div>
                </div>
              </div>
               <div class="up-portlet-wrapper-bottom">
                 <div class="up-portlet-wrapper-bottom-inner"></div>
               </div>
             </div>
          </div>
		  </div>
		  <div class="panel portlet-section-body" role="region">
		  <fieldset style="border: thin solid black; border-radius: 1em;">
		    <legend><spring:message code="config.attrs"/></legend>
		    <c:forEach var="attr" items="${form.attrs}">
			    <form:checkbox path="attrs[${attr.key}].enabled" />
		      <c:choose>
		        <c:when test="${attr.value.enabled}">
				      <form:label path="attrs[${attr.key}].value"><spring:message code="config.${attr.key}"/></form:label>
				      <form:input path="attrs[${attr.key}].value" />
		        </c:when>
		        <c:otherwise>
		          <del><spring:message code="config.${attr.key}"/></del>
				      <form:input path="attrs[${attr.key}].value" disabled="true" />
		        </c:otherwise>
		      </c:choose>
		      <br/>
		    </c:forEach>
		    <hr/>
		      <label for="prefKey"><spring:message code="config.addPref"/></label>
		      <select name="prefKey" >
		        <option value="" selected="selected">&nbsp;&mdash;</option>
		        <c:forEach var="key" items="${form.prefKeys}">
			        <option value="${key}">${key}</option>
		        </c:forEach>
		      </select>
		  </fieldset>
          <input class="portlet-form-button portlet-button button portlet-button-primary primary"
             type="submit" name="update" value="<spring:message code="config.update" />" />
		  </div>
	  </div>
	  <div class="fl-widget-content portlet-section-footer" role="region">
	    <input class="portlet-form-button portlet-button button portlet-button-primary primary"
	       type="submit" name="submit" value="<spring:message code="config.submit" />" />
	    <a class="portlet-form-button portlet-button button portlet-button-secondary secondary"
	       href="${cancelUrl}"><spring:message code="config.cancel" /></a>
	  </div>
  </form:form>
</div>
<script type="text/javascript">
<rs:compressJs>
  //<![CDATA[
  var ${n} = ${n} || {};
  ${n}.jQuery = jQuery.noConflict(true);
  ${n}.fluid = fluid;

  ${n}.innerAccessor = function (element) {
    return {
      value: function (newValue) {
          return ${n}.jQuery(element).find(".text").html(newValue)
      }
    };
  };

  (function($,fluid){
      $(document).ready(function(){
          // Create an CKEditor 3.x-based Rich Inline Edit component.
          var ckEditor = fluid.inlineEdit.CKEditor("#${n}form", {
            useTooltip: false,
            submitOnEnter: false,
            displayAccessor: {
              type: "${n}.innerAccessor"
            },
            listeners: {
              afterInitEdit: function (ckObj) {
                // remaps 'save' button
                ckObj.getCommand('save').exec = function (editor) {
                        ckEditor.finish();
                };
              }
            },
            CKEditor: {
              // CKEditor prefs
              //removePlugins = 'save'
            }
          });
      });
  })(${n}.jQuery, ${n}.fluid);
  //]]>
</rs:compressJs>
</script>
