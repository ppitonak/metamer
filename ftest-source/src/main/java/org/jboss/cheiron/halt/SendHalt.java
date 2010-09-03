/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *******************************************************************************/
package org.jboss.cheiron.halt;

import static org.jboss.test.selenium.encapsulated.JavaScript.js;

import org.jboss.test.selenium.encapsulated.JavaScript;
import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.framework.AjaxSeleniumProxy;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class SendHalt {

    private static JavaScript isHaltAvailable = js("selenium.browserbot.getCurrentWindow().SendHalt.isHaltAvailable()");
    private static JavaScript getHalt = js("selenium.browserbot.getCurrentWindow().SendHalt.getHalt()");
    private static JavaScript unhalt = js("selenium.browserbot.getCurrentWindow().SendHalt.unhalt({0})");
    private static JavaScript setEnabled = js("selenium.browserbot.getCurrentWindow().SendHalt.setEnabled({0})");

    private static final AbstractPageExtensions sendHaltExtensions = new AbstractPageExtensions() {
        {
            loadFromResource("javascript/cheiron/SendHalt.js");
        }

        public JavaScript isExtensionInstalledScript() {
            return js("selenium.browserbot.getCurrentWindow().SendHalt != undefined");
        }
    };

    int handle;

    private SendHalt(int handle) {
        this.handle = handle;
    }

    static AjaxSelenium selenium = AjaxSeleniumProxy.getInstance();

    public static void enable() {
        selenium.getPageExtensions().install();
        sendHaltExtensions.install();
        selenium.getEval(setEnabled.parametrize(true));
    }

    public static void disable() {
        selenium.getEval(setEnabled.parametrize(false));
    }

    public static SendHalt getHalt() {
        selenium.waitForCondition(isHaltAvailable);
        return new SendHalt(Integer.valueOf(selenium.getEval(getHalt)));
    }

    public void unhalt() {
        selenium.getEval(unhalt.parametrize(handle));
    }

}
