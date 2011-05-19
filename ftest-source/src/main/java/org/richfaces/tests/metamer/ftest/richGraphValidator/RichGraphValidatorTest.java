/*******************************************************************************
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
 *******************************************************************************/
package org.richfaces.tests.metamer.ftest.richGraphValidator;

import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.utils.URLUtils.buildUrl;

import java.net.URL;

import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.option.OptionValueLocator;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.testng.annotations.Test;

/**
 * Test for page /faces/components/richGraphValidator/all.xhtml
 *
 * @author <a href="mailto:jjamrich@redhat.com">Jan Jamrich</a>
 * @version $Revision$
 */
public class RichGraphValidatorTest extends AbstractMetamerTest {

    private static final String SMILE = ":-)";
    private static final GraphValidatorAttributes attributes = new GraphValidatorAttributes();
    
    private static final String[] groups = {"", "javax.validation.groups.Default", 
        "org.richfaces.tests.metamer.validation.groups.ValidationGroupAllComponents",
        "org.richfaces.tests.metamer.validation.groups.ValidationGroupBooleanInputs", 
        "org.richfaces.tests.metamer.validation.groups.ValidationGroupNumericInputs"};
    
    private JQueryLocator autocomplete = pjq("input[id$=autocompleteInput]");
    private JQueryLocator inputSecret = pjq("input[id$=inputSecret]");
    private JQueryLocator inputText = pjq("input[id$=inputText]");
    private JQueryLocator calendar = pjq("input[id$=calendarInputDate]");
    private JQueryLocator inputTextArea = pjq("textarea[id$=inputTextarea]");
    private JQueryLocator inplaceSelect = pjq("span[id$=inplaceSelect] input[id$=inplaceSelectFocus]");
    private JQueryLocator inplaceInput = pjq("span[id$=inplaceInput] input[id$=inplaceInputFocus]");
    private JQueryLocator selectManyCheckbox = pjq("input[id$=selectManyCheckbox:4]");
    private JQueryLocator selectManyListbox = pjq("select[id$=selectManyListbox]");
    private JQueryLocator selectManyMenu = pjq("select[id$=selectManyMenu]");
    private JQueryLocator selectBooleanCheckbox = pjq("input[id$=selectBooleanCheckbox]");
    private JQueryLocator inputNumberSliderInput = pjq("span[id$=inputNumberSlider] input.rf-insl-inp");
    private JQueryLocator inputNumberSpinnerInput = pjq("span[id$=inputNumberSpinner] input.rf-insp-inp");
    
    private JQueryLocator globalMessagesContainer = pjq("span[id$=globalMessages]");
    private JQueryLocator header = pjq("div.rf-p-hdr[id$=gv1h_header]");
    
    private OptionValueLocator optionSmile = new OptionValueLocator(SMILE);
    
    private JQueryLocator applyChangesBtn = pjq("input[id$=applyChanges]");

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richGraphValidator/all.xhtml");
    }
    
    @Test
    public void testGroups() {
        for (int i=0; i<groups.length; ++i) {
            attributes.setGroups(groups[i]);
            
            setAllValidatedFields();
            
            if (i==3) {
                // only Boolean inputs validated
                allFieldsSetToWrong();
                selenium.check(selectBooleanCheckbox, true);
            } else if (i==4) {
                // only numeric inputs validated
                allFieldsSetToWrong();
                selenium.type(inputNumberSliderInput, "10");
                selenium.type(inputNumberSpinnerInput, "10");
            }
            
            // let's submit the form
            selenium.click(applyChangesBtn);
            
            // wait for success validation
            waitGui.until(textEquals
                .locator(globalMessagesContainer.getDescendant(jq("span.rf-msgs-sum")))
                .text("Action sucessfully done!"));            
        }
    }
    
    @Test
    public void testSummary() {
        setAllValidatedFields();
        // !!! Seems that there is bug - no content rendered when set summary attr
    }

    @Test
    public void testValue() {
        
        attributes.setValue("testValue");
        
        setAllValidatedFields();
        
        // let's submit the form
        selenium.click(applyChangesBtn);
        
        // wait for success validation
        waitGui.until(textEquals
            .locator(globalMessagesContainer.getDescendant(jq("span.rf-msgs-sum")))
            .text("Action sucessfully done!"));
    }
    
    @Test
    public void testRendered() {
        setAllValidatedFields();
        
        attributes.setRendered(Boolean.FALSE);
        
        waitGui.until(isNotDisplayed.locator(header));
    }
    
    private void setAllValidatedFields(){
        // inputSecret don't keed entered value after submit
        selenium.type(inputSecret, SMILE);
        
        // input returning List/Set are by default without any element checked
        selenium.check(selectManyCheckbox, true);
        selenium.select(selectManyListbox, optionSmile);        
        selenium.select(selectManyMenu, optionSmile);        
    }
    
    private void allFieldsSetToWrong() {
        selenium.type(inplaceSelect, "---");
        selenium.type(inplaceInput, "---");
        selenium.type(inputNumberSpinnerInput, "10");
        selenium.check(selectBooleanCheckbox, false);
        selenium.type(inputSecret, "---");
        
        selenium.type(inputNumberSliderInput, "15");
        selenium.type(inputNumberSpinnerInput, "15");
        selenium.type(autocomplete, "---");
        selenium.type(inputText, "---");
        selenium.type(calendar, "");        
        selenium.type(inputTextArea, "---");
    }
}
