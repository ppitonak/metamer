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
package org.richfaces.tests.metamer.ftest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jboss.test.selenium.listener.FailureLoggingTestListener;
import org.jboss.test.selenium.utils.testng.TestInfo;
import org.testng.ITestResult;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class MetamerFailureLoggingTestListener extends FailureLoggingTestListener {
    @Override
    protected String getFilenameIdentification(ITestResult result) {
        String packageName = TestInfo.getContainingPackageName(result);
        String className = TestInfo.getClassName(result);
        String methodName = TestInfo.getMethodName(result);

        String testInfo = MetamerTestInfo.getConfigurationInfo();
        testInfo = StringUtils.replaceChars(testInfo, "\\/*?\"<>|", "");
        testInfo = StringUtils.replaceChars(testInfo, "\r\n \t", "_");
        testInfo = StringUtils.replaceChars(testInfo, ":", "-");

        // derives template and sort it as sub-directory after other attributes
        Matcher matcher = Pattern.compile("^(template-[^;]+);(.*)$").matcher(testInfo);
        if (matcher.find()) {
            testInfo = matcher.group(2) + "/" + matcher.group(1);
        }

        return packageName + "/" + className + "/" + methodName + "/" + testInfo;
    }

    @Override
    protected String getSeleniumLogIdentification(ITestResult result) {
        String id = super.getSeleniumLogIdentification(result);
        return id + " " + MetamerTestInfo.getConfigurationInfoInParenthesses();
    }
}
