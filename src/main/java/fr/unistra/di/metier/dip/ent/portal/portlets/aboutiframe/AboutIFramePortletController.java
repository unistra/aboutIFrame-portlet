/**
 * Copyright (C) 2012 Université de Strasbourg (di-dip@unistra.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.unistra.di.metier.dip.ent.portal.portlets.aboutiframe;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This portlet renders content identified by a URL within an inline browser
 * frame but only if maximized, an about message otherwise.
 * 
 * @see org.jasig.portal.AboutIFramePortletController.iframe.IFramePortletController
 * @author Léa Raya Décornod <decornod@unistra.fr>
 */
@Controller
@RequestMapping({"VIEW","HELP","ABOUT"})
public class AboutIFramePortletController {
    
    private CasTicketService casTicketService;
    
    @Autowired
    public void setCasTicketService(CasTicketService casTicketService) {
        this.casTicketService = casTicketService;
    }
    
	/** non-exclusive windowStates */
	private static final Set<WindowState> aboutWindowStates = Collections
			.unmodifiableSet(new HashSet<WindowState>(Arrays.asList(
			        PortletConstants.WINDOW_STATE_MINIMIZED,
			        PortletConstants.WINDOW_STATE_NORMAL
			)));
	
    @SuppressWarnings("serial")
    protected static final Map<String, String> IFRAME_ATTRS = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {{
        /** document-wide unique id */
        put("id", null);
        
        /** space-separated list of classes */
        put("cssClass", null);
        
        /** associated style info */
        put("style", null);
        
        /** advisory title */
        put("title", null);
        
        /** link to long description (complements title) */
        put("longDescription", null);
        
        /** name of frame for targetting */
        put("name", null);
        
        /** source of frame content */
        put("src", null);
        
        /** request frame borders? */
        put("frameBorder", "0");
        
        /** margin widths in pixels */
        put("marginWidth", null);
        
        /** margin height in pixels */
        put("marginHeight", null);
        
        /** scrollbar or none */
        put("scrolling", null);
        
        /** vertical or horizontal alignment */
        put("align", null);
        
        /** frame height */
        put("width", "100%");
        
        /** frame width */
        put("height", null);
    }});

    /**
     * Model Attribute holding HTML IFrame element's attributes
     * @param request used to access {@link PortletPreferences}
     * @param response used to get {@link RenderResponse#getNamespace() Portlet Namespace}
     * @return a map
     */
    @ModelAttribute("attrs")
    protected Map<String,Object> getAttrs(RenderRequest request,
            RenderResponse response) {

		Map<String,Object> model = new HashMap<String,Object>();
		
		// get the IFrame target URL and the configured height of the IFrame
		// window from the portlet preferences
		PortletPreferences preferences = request.getPreferences();
		
		for (final Map.Entry<String, String> attrEntry : IFRAME_ATTRS.entrySet()) {
    		final String attr = attrEntry.getKey();
            final String defaultValue = attrEntry.getValue();
            model.put(attr, preferences.getValue(attr, defaultValue));
		}
		
        //Legacy support for url attribute
		if (model.get("src") == null) {
	        model.put("src", preferences.getValue("url", IFRAME_ATTRS.get("src")));	        
		}
		// decorate URL if CAS needed
		if (Boolean.valueOf(preferences.getValue("authCAS", Boolean.toString(false))))
		    model.put("src", new CasUrlDecorator(request, (String) model.get("src")));
		
		// sets iframe id if undefined (using legacy iFrameName)
		if (model.get("id") == null) {
		    String uniqueID = response.getNamespace() + "frame";
		    model.put("id", preferences.getValue("iFrameName", uniqueID));
		}
		// copy @id to @name (if undefined) for compatibility
		if (model.get("name") == null)
		    model.put("name", model.get("id"));

		return model;
    }

    /**
     * about HTML text retrieved from {@link PortletPreferences Portlet preference}
     * @param request used to access {@link PortletPreferences}
     * @return HTML fragment
     */
    @ModelAttribute("about")
    protected String getAbout(RenderRequest request) {
        PortletPreferences preferences = request.getPreferences();
        if (PortletConstants.PORTLET_MODE_HELP.equals(request.getPortletMode()))
            return preferences.getValue("help", preferences.getValue("about", ""));
        return preferences.getValue("about", "");
    }

    /**
     * openExternal {@link PortletPreferences Portlet preference}
     * @param request used to access {@link PortletPreferences}
     * @return true if the url has to be opened in a new browser window
     */
    @ModelAttribute("isOpenExternal")
    protected boolean isOpenExternal(RenderRequest request) {
        PortletPreferences preferences = request.getPreferences();
        return PortletConstants.IFrameStyle.EXTERNAL.name()
                .equalsIgnoreCase(preferences.getValue("displayStyle", "About"));
    }

    /**
     * Decides if we render ‘iframe’ or ‘about’ view
     * @param request to get {@link WindowState} and {@link PortletMode}
     * @return view name
     */
    @RequestMapping
	protected String showView(RenderRequest request) {
        // style = "IFrame" → <iframe />
        PortletPreferences preferences = request.getPreferences();
        if (PortletConstants.IFrameStyle.IFRAME.name()
        .equalsIgnoreCase(preferences.getValue("displayStyle", "About")))
            return "/jsp/IFrame/iframePortlet";
        
        // window-state = "minimized" → <a href="…" />
        if (PortletConstants.WINDOW_STATE_MINIMIZED.equals(request.getWindowState()))
            return "/jsp/IFrame/linkOnly";
        
        // a-propos check → a-propos view
        boolean isAbout = aboutWindowStates.contains(request.getWindowState())
                       || PortletConstants.PORTLET_MODE_ABOUT.equals(request.getPortletMode())
                       || PortletConstants.PORTLET_MODE_HELP.equals(request.getPortletMode());
        if (isOpenExternal(request) || isAbout)
            return "/jsp/IFrame/aboutIframePortlet";
        
        // otherwise → <iframe />
        return "/jsp/IFrame/iframePortlet";
	}

    public class CasUrlDecorator {
        private String url;
        private String authUrl;
        private PortletRequest request;
        public CasUrlDecorator(PortletRequest request, String url) {
            this.url = url;
            this.request = request;
            this.authUrl = null;
        }
        @Override
        public String toString() {
            if (authUrl == null)
                authUrl = casTicketService.getCasAuthURL(request, url);
            return authUrl;
        }
    }
}
