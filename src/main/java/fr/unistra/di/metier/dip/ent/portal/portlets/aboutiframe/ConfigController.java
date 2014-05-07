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

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Allow to define {@link PortletPreferences} through CONFIG portlet mode
 * @author Léa Raya Décornod <decornod@unistra.fr>
 */
@Controller
@RequestMapping("CONFIG")
@SessionAttributes("form")
public class ConfigController {

    private ConfigDAO dao;

    @Autowired(required=true)
    public void setDao(ConfigDAO dao) {
        this.dao = dao;
    }

    /**
     * Shows config form
     */
    @RequestMapping
    public String showForm(@ModelAttribute("form") ConfigForm form) {
        return "/jsp/IFrame/config";
    }

    /**
     * Get the form object
     * <p/>
     * (Populated with {@link PortletPreferences}
     * and backed in {@link PortletSession})
     * @see ConfigDAO#getForm(PortletPreferences)
     */
    @ModelAttribute("form")
    public ConfigForm getForm(PortletRequest request) {
        return dao.getForm(request.getPreferences());
    }

    /**
     * on Form submit → update preferences
     * @param request
     * @param response
     * @param form
     * @see ConfigDAO#saveFormData(ConfigForm, PortletPreferences)
     */
    @RequestMapping(params={"action=update", "submit"})
    public void update(ActionRequest request, ActionResponse response,
            @ModelAttribute("form") ConfigForm form,
            SessionStatus status) {

        dao.saveFormData(form, request.getPreferences());

        status.setComplete();
        // exit CONFIG PortletMode to return to VIEW PortletMode
        try {
            response.setPortletMode(PortletConstants.PORTLET_MODE_VIEW);
        } catch (PortletModeException e) {
            // VIEW is always an allowed PortletMode
        }
    }

    /**
     * on Form update, the backing command object has been updated,
     * just handle if a new attribute is requested
     * @param request
     * @param response
     */
    @RequestMapping(params={"action=update", "update"})
    public void addPreference(ActionRequest request, ActionResponse response,
            @ModelAttribute("form") ConfigForm form,
            @RequestParam("prefKey") String key) {
        if (key != "")
            form.restoreAttr(key);
    }

    /**
     * on Form cancel → leave CONFIG
     * @param request
     * @param response
     */
    @RequestMapping(params="action=cancel")
    public void cancel(ActionRequest request, ActionResponse response,
            SessionStatus status) {
        // reset From data
        status.setComplete();
        // exit CONFIG PortletMode to go to VIEW PortletMode
        try {
            response.setPortletMode(PortletConstants.PORTLET_MODE_VIEW);
        } catch (PortletModeException e) {
            // VIEW is always an allowed PortletMode
        }
    }

}
