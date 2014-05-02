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

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

/**
 * Portlet Constants values like {@link WindowState Window States} or {@link PortletMode Portlet Modes}
 * 
 * @author Léa Рая DÉCORNOD <decornod@unistra.fr>
 * @see WindowState
 * @see PortletMode
 */
public final class PortletConstants {

    /* Window States */

    /** @see javax.portlet.WindowState#NORMAL */
    public static final WindowState WINDOW_STATE_NORMAL = WindowState.NORMAL;

    /** @see javax.portlet.WindowState#MAXIMIZED */
    public static final WindowState WINDOW_STATE_MAXIMIZED = WindowState.MAXIMIZED;

    /** @see javax.portlet.WindowState#MINIMIZED */
    public static final WindowState WINDOW_STATE_MINIMIZED = WindowState.MINIMIZED;

    /** @see org.jasig.portal.portlet.rendering.IPortletRenderer#EXCLUSIVE */
    public static final WindowState WINDOW_STATE_EXCLUSIVE = new WindowState("EXCLUSIVE");

    /** @see org.jasig.portal.portlet.rendering.IPortletRenderer#DETACHED */
    public static final WindowState WINDOW_STATE_DETACHED = new WindowState("DETACHED");

    /** @see org.jasig.portal.portlet.rendering.IPortletRenderer#DASHBOARD */
    public static final WindowState WINDOW_STATE_DASHBOARD = new WindowState("DASHBOARD");

    /* Portlet Modes */

    /** @see javax.portlet.PortletMode#VIEW */
    public static final PortletMode PORTLET_MODE_VIEW = PortletMode.VIEW;

    /** @see javax.portlet.PortletMode#HELP */
    public static final PortletMode PORTLET_MODE_HELP = PortletMode.HELP;

    /** @see javax.portlet.PortletMode#EDIT */
    public static final PortletMode PORTLET_MODE_EDIT = PortletMode.EDIT;

    /** @see org.jasig.portal.portlet.rendering.IPortletRenderer#ABOUT */
    public static final PortletMode PORTLET_MODE_ABOUT = new PortletMode("ABOUT");

    /** @see org.jasig.portal.portlet.rendering.IPortletRenderer#CONFIG */
    public static final PortletMode PORTLET_MODE_CONFIG = new PortletMode("CONFIG");

}
