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

import java.io.IOException;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import fr.unistra.di.metier.dip.ent.portal.portlets.aboutiframe.ConfigForm.Attr;

/**
 * Persist Web Form from/into {@link PortletPreferences}
 * @author Léa Raya Décornod <decornod@unistra.fr>
 */
@Repository
public class ConfigDAO {

    protected final Logger log = Logger.getLogger(getClass());

    /**
     * Read Web Form from {@link PortletPreferences}
     * @param preferences
     * @return
     * @see PortletPreferences#getValue(String, String)
     */
    protected ConfigForm getForm(PortletPreferences preferences) {

        log.debug("Get PortletPreferences");

        ConfigForm form = new ConfigForm();
        Map<String, String> attrs = AboutIFramePortletController.IFRAME_ATTRS;
        for (String key : attrs.keySet()) {
            if ( "url".equalsIgnoreCase(key)
               ||"src".equalsIgnoreCase(key))
                continue;
            String value = preferences.getValue(key, null);
            if (value != null)
                form.setAttr(key, value);
        }
        form.setUrl(preferences.getValue("url", ""));
        form.setAuthCas(Boolean.valueOf(preferences.getValue("authCAS", "false")));
        form.setAbout(preferences.getValue("about", ""));
        form.setHelp(preferences.getValue("help", ""));
        form.setDisplayStyle(preferences.getValue("displayStyle", "About"));

        // retro-compatibility (move iFrameName → id)
        String iFrameName = preferences.getValue("iFrameName", null);
        if (iFrameName != null)
        {
            form.setAttr("iFrameName", iFrameName);
            form.delAttr("iFrameName");
            form.setAttr("id", iFrameName);
        }

        return form;
    }

    /**
     * Save Web Form into {@link PortletPreferences}
     * @param form
     * @param preferences
     * @see PortletPreferences#setValue(String, String)
     * @see PortletPreferences#reset(String)
     */
    protected void saveFormData(ConfigForm form, PortletPreferences preferences) {

        log.debug("Save PortletPreferences");

        try {
            preferences.setValue("url", form.getUrl());
            if (form.isAuthCas())
                preferences.setValue("authCAS", Boolean.toString(true));
            else
                preferences.reset("authCAS");
            preferences.setValue("about", form.getAbout());
            String help = form.getHelp();
            if (! help.isEmpty())
                preferences.setValue("help", help);
            else
                preferences.reset("help");
            preferences.setValue("displayStyle", form.getDisplayStyle());
            for (Map.Entry<String, Attr> attr : form.getAttrs().entrySet())
                if (attr.getValue().isEnabled())
                    preferences.setValue(attr.getKey(), attr.getValue().getValue());
                else
                    preferences.reset(attr.getKey());
            preferences.store();
        } catch (ReadOnlyException  ex) {
            log.error(ex);
        } catch (ValidatorException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        }
    }

}
