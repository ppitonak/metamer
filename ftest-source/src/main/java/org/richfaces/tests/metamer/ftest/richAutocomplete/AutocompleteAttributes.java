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
package org.richfaces.tests.metamer.ftest.richAutocomplete;

import org.jboss.test.selenium.encapsulated.JavaScript;
import org.richfaces.tests.metamer.ftest.AbstractComponentAttributes;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class AutocompleteAttributes extends AbstractComponentAttributes {
    public void setAutofill(Boolean autofill) {
        setProperty("autofill", autofill);
    }

    public void setClientFilter(JavaScript clientFilter) {
        setProperty("clientFilter", clientFilter);
    }

    public void setDisabled(Boolean disabled) {
        setProperty("disabled", disabled);
    }

    public void setImmediate(Boolean immediate) {
        setProperty("immediate", immediate);
    }

    public void setMinChars(int minChars) {
        setProperty("minChars", minChars);
    }

    public void setMode(Mode mode) {
        setProperty("mode", mode);
    }

    public void setRendered(Boolean rendered) {
        setProperty("rendered", rendered);
    }

    public void setRequired(Boolean required) {
        setProperty("required", required);
    }

    public void setSelectFirst(Boolean selectFirst) {
        setProperty("selectFirst", selectFirst);
    }

    public void setShowButton(Boolean showButton) {
        setProperty("showButton", showButton);
    }

    public void setTokens(String tokens) {
        setProperty("tokens", tokens);
    }

    public enum Mode {
        AJAX, CACHED_AJAX, CLIENT
    }
}
