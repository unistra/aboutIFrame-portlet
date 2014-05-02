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
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractController;

/**
 * This portlet renders content identified by a URL within an inline browser
 * frame but only if maximized, an about message otherwise.
 * 
 * @see org.jasig.portal.AboutIFramePortletController.iframe.IFramePortletController
 * @author Léa Raya Décornod <decornod@unistra.fr>
 */
public class AboutIFramePortletController extends AbstractController {

	/** non-exclusive windowStates */
	private static final Set<WindowState> aboutWindowStates = Collections
			.unmodifiableSet(new HashSet<WindowState>(Arrays.asList(
					WindowState.MINIMIZED, WindowState.NORMAL)));
	
	@Override
	protected ModelAndView handleRenderRequestInternal(RenderRequest request,
			RenderResponse response) throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
		
		// get the IFrame target URL and the configured height of the IFrame
		// window from the portlet preferences
		PortletPreferences preferences = request.getPreferences();
		model.put("url", preferences.getValue("url", "#"));
		model.put("height", preferences.getValue("height", ""));
		model.put("about", preferences.getValue("about", ""));
		String uniqueID = response.getNamespace() + "frame";
		model.put("iFrameName", preferences.getValue("iFrameName", uniqueID));
		model.put("isOpenExternal", preferences.getValue("openExternal", "false").equalsIgnoreCase("true"));
		model.put("isAbout", aboutWindowStates.contains(request.getWindowState()));
		
		return new ModelAndView("/jsp/IFrame/aboutIframePortlet", model);
	}

}
