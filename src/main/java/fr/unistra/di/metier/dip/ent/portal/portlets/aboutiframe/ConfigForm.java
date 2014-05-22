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

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Simple web form for AboutIFrame PortletPreferences
 * @author Léa Raya Décornod <decornod@unistra.fr>
 */
public class ConfigForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String,Attr> attrs = new HashMap<String, Attr>();

    private String url;
    private String about;
    private String help;
    private boolean openExternal;

    /**
     * Get IFrame src URL attribute
     * @return target URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set IFrame src URL attribute
     * @param url target URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the HTML content to be displayed as “About”
     * @return the HTML “about” content
     */
    public String getAbout() {
        return about;
    }

    /**
     * Set the HTML content to be displayed as “About”
     * @param about the HTML “about” content
     */
    public void setAbout(String about) {
        this.about = about;
    }

    /**
     * Get the HTML content to be displayed as “Help”
     * @return the HTML “help” content
     */
    public String getHelp() {
        return help;
    }
    
    /**
     * Set the HTML content to be displayed as “Help”
     * @param about the HTML “help” content
     */
    public void setHelp(String help) {
        this.help = help;
    }
    
    /**
     * True if the content has to be opened in a external browser window
     */
    public boolean isOpenExternal() {
        return openExternal;
    }

    /**
     * Set if the content has to be opened in a external browser window
     */
    public void setOpenExternal(boolean openExternal) {
        this.openExternal = openExternal;
    }

    /**
     * @return the prefKey
     */
    public Set<String> getPrefKeys() {
        Set<String> freeAttrs = new HashSet<String>();
        freeAttrs.addAll(AboutIFramePortletController.IFRAME_ATTRS.keySet());
        freeAttrs.removeAll(attrs.keySet());
        return freeAttrs;
    }

    /**
     * Get a read-only view on IFrame Attrs
     * @return Attrs as a R-O {@link Map}&lt;{@link String},{@link Attr}&gt;}
     */
    public Map<String, Attr> getAttrs() {
        return Collections.unmodifiableMap(attrs);
    }

    /**
     * bean setter for attrs
     * @param attrs
     */
    public void setAttrs(Map<String, Attr> attrs) {
        this.attrs = attrs;
    }

    /**
     * delegate setter on attrs[key]
     * @param key
     * @param value
     */
    public void setAttr(String key, String value) {
        if(value == null)
            delAttr(key);
        else
            this.attrs.put(key, new Attr(value));
    }

    /**
     * delegate setter on attrs[key].setEnabled(false)
     * @param key
     * @param value
     */
    public void delAttr(String key) {
        if (attrs.containsKey(key))
            attrs.get(key).setEnabled(false);
    }

    /**
     * delegate setter on attrs[key].setEnabled(true)
     * possibly adding the default value if missing
     * @param key
     * @param value
     */
    public void restoreAttr(String key) {
        if (attrs.containsKey(key))
            attrs.get(key).setEnabled(true);
        else
            attrs.put(key, new Attr(
                    AboutIFramePortletController.IFRAME_ATTRS.get(key)
                    )
            );
    }

    /**
     * Represent a tuple (value, enabled) as &lt;String, boolean&gt; 
     * @author Léa Рая DÉCORNOD <decornod@unistra.fr>
     */
    public static class Attr implements Serializable {

        private static final long serialVersionUID = 1L;
        private String value;
        private boolean isEnabled;

        public Attr() { }
        private Attr(String value)
        {
            this.value = value;
            this.isEnabled = true;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }

        /**
         * @param value the value to set
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * @return the isDeleted
         */
        public boolean isEnabled() {
            return isEnabled;
        }

        /**
         * @param isDeleted the isDeleted to set
         */
        public void setEnabled(boolean isDeleted) {
            this.isEnabled = isDeleted;
        }
    }
}
