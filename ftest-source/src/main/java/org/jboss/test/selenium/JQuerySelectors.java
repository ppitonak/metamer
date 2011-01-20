package org.jboss.test.selenium;

import static org.jboss.test.selenium.locator.LocatorFactory.jq;

import org.jboss.test.selenium.locator.ExtendedLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.utils.text.SimplifiedFormat;

public class JQuerySelectors {
    public static JQueryLocator not(ExtendedLocator<JQueryLocator> locator, String expression) {
        return jq(SimplifiedFormat.format("{0}:not({1})", locator.getRawLocator(), expression));
    }
    
    public static JQueryLocator append(ExtendedLocator<JQueryLocator> locator, String expression) {
        return jq(SimplifiedFormat.format("{0}{1}", locator.getRawLocator(), expression));
    }
}
