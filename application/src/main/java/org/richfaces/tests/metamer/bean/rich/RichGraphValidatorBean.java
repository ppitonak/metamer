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
package org.richfaces.tests.metamer.bean.rich;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.richfaces.component.UIGraphValidator;
import org.richfaces.tests.metamer.Attributes;
import org.richfaces.tests.metamer.validation.groups.ValidationGroup1;
import org.richfaces.tests.metamer.validation.groups.ValidationGroup2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Managed Bean for rich:graphValidator
 *
 * @author <a href="mailto:jjamrich@redhat.com">Jan Jamrich</a>
 * @version $Revision$
 */
@ManagedBean(name="richGraphValidatorBean")
@ViewScoped
public class RichGraphValidatorBean implements Serializable, Cloneable {
    
    /** Generated UID */
    private static final long serialVersionUID = -960575870621302059L;
    private static Logger logger;
    private Attributes attributes;
    
    @Size(min = 5, max = 15, message = "Wrong size for password")
    private String password;
    @Size(min = 5, max = 15, message = "Wrong size for password")
    private String passwordConfirm;
    
    @PostConstruct
    public void init(){
        logger = LoggerFactory.getLogger(getClass());
        logger.info("initializing bean " + getClass().getName());
        attributes = Attributes.getComponentAttributesFromFacesConfig(UIGraphValidator.class, getClass());
        
        attributes.setAttribute("rendered", true);
        attributes.setAttribute("type", "org.richfaces.BeanValidator");
    }
    
    @AssertTrue(message = "Different passwords entered! [Default Group]")
    public boolean isPasswordsEquals() {
        return password.equals(passwordConfirm);
    }
    
    @AssertTrue(message = "Different passwords entered! [G1]", groups={ValidationGroup1.class})
    public boolean isRovnakeHesla() {
        return password.equals(passwordConfirm);
    }
    
    @AssertTrue(message = "Different passwords entered! [G2 + Default Group]", groups = {ValidationGroup2.class,Default.class})
    public boolean isPokusUspesny() {
        return password.equals(passwordConfirm);
    }
    
    public void storeNewPassword() {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesfully changed!", "Succesfully changed!"));
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
