/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2011, Red Hat, Inc. and individual contributors
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
 */
package org.jboss.test.selenium.waiting;

import static org.jboss.test.selenium.SystemProperties.getSeleniumTimeout;
import static org.jboss.test.selenium.waiting.Wait.waitAjax;
import static org.jboss.test.selenium.waiting.Wait.waitSelenium;
import static org.jboss.test.selenium.AbstractTestCase.WAIT_AJAX_INTERVAL;
import static org.jboss.test.selenium.AbstractTestCase.WAIT_GUI_INTERVAL;
import static org.jboss.test.selenium.AbstractTestCase.WAIT_MODEL_INTERVAL;
import static org.jboss.test.selenium.SystemProperties.SeleniumTimeoutType.AJAX;
import static org.jboss.test.selenium.SystemProperties.SeleniumTimeoutType.GUI;
import static org.jboss.test.selenium.SystemProperties.SeleniumTimeoutType.MODEL;

import org.jboss.test.selenium.waiting.ajax.AjaxWaiting;
import org.jboss.test.selenium.waiting.conditions.AlertEquals;
import org.jboss.test.selenium.waiting.conditions.AlertPresent;
import org.jboss.test.selenium.waiting.conditions.AttributeEquals;
import org.jboss.test.selenium.waiting.conditions.AttributePresent;
import org.jboss.test.selenium.waiting.conditions.CountEquals;
import org.jboss.test.selenium.waiting.conditions.ElementPresent;
import org.jboss.test.selenium.waiting.conditions.StyleEquals;
import org.jboss.test.selenium.waiting.conditions.TextEquals;
import org.jboss.test.selenium.waiting.selenium.SeleniumWaiting;

/** 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public final class WaitFactory {
    /** elementPresent waiting */
    public static final ElementPresent ELEMENT_PRESENT = ElementPresent.getInstance();
    
    /**
     * Waits for GUI interaction, such as rendering
     */
    public static final AjaxWaiting WAIT_GUI = waitAjax().interval(WAIT_GUI_INTERVAL).timeout(getSeleniumTimeout(GUI));
    /**
     * Waits for AJAX interaction with server - not computationally difficult
     */
    public static final AjaxWaiting WAIT_AJAX = waitAjax().interval(WAIT_AJAX_INTERVAL).timeout(getSeleniumTimeout(AJAX));
    /**
     * Waits for computationally difficult requests
     */
    public static final SeleniumWaiting WAIT_MODEL = waitSelenium().interval(WAIT_MODEL_INTERVAL).timeout(
        getSeleniumTimeout(MODEL));
    
    public static final TextEquals TEXT_EQUALS = TextEquals.getInstance();
    public static final StyleEquals STYLE_EQUALS = StyleEquals.getInstance();
    public static final AttributePresent ATTRIBUTE_PRESENT = AttributePresent.getInstance();
    public static final AttributeEquals ATTRIBUTE_EQUALS = AttributeEquals.getInstance();
    public static final AlertPresent ALERT_PRESENT = AlertPresent.getInstance();
    public static final AlertEquals ALERT_EQUALS = AlertEquals.getInstance();
    public static final CountEquals COUNT_EQUALS = CountEquals.getInstance();
    
    private WaitFactory() { };
    
}
