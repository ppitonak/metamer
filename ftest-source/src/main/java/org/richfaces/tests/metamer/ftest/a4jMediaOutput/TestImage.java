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
package org.richfaces.tests.metamer.ftest.a4jMediaOutput;

import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.jboss.test.selenium.dom.Event;
import org.jboss.test.selenium.locator.Attribute;
import org.jboss.test.selenium.locator.AttributeLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.testng.annotations.Test;

/**
 * Test case for page /faces/components/a4jMediaOutput/image.xhtml
 * 
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
public class TestImage extends AbstractMetamerTest {

    private JQueryLocator image = pjq("img[id$=mediaOutput]");

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/a4jMediaOutput/image.xhtml");
    }

    @Test
    public void testImage() {
        AttributeLocator imageURLAttr = image.getAttribute(Attribute.SRC);
        URL imageURL = buildUrl(contextRoot, selenium.getAttribute(imageURLAttr));

        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(imageURL);
        } catch (IOException ex) {
            fail("Could not download image from URL " + imageURL.getPath());
        }

        assertEquals(bufferedImage.getHeight(), 120, "Height of the image");
        assertEquals(bufferedImage.getWidth(), 300, "Width of the image");

        for (int x = 0; x < 150; x++) {
            for (int y = 0; y < 60; y++) {
                assertEquals(bufferedImage.getRGB(x, y), Color.YELLOW.getRGB(), "Top-left quadrant should be yellow [" + x + ", " + y + "].");
            }
        }

        for (int x = 151; x < 300; x++) {
            for (int y = 0; y < 60; y++) {
                assertEquals(bufferedImage.getRGB(x, y), Color.RED.getRGB(), "Top-right quadrant should be red [" + x + ", " + y + "].");
            }
        }

        for (int x = 0; x < 150; x++) {
            for (int y = 61; y < 120; y++) {
                assertEquals(bufferedImage.getRGB(x, y), Color.BLUE.getRGB(), "Bottom-left quadrant should be blue [" + x + ", " + y + "].");
            }
        }

        for (int x = 151; x < 300; x++) {
            for (int y = 61; y < 120; y++) {
                assertEquals(bufferedImage.getRGB(x, y), Color.GREEN.getRGB(), "Bottom-right quadrant should be yellow [" + x + ", " + y + "].");
            }
        }
    }

    @Test
    public void testAccesskey() {
        testHtmlAttribute(image, "accesskey", "r");
    }

    @Test
    public void testAlign() {
        testHtmlAttribute(image, "align", "left");
    }

    @Test
    public void testBorder() {
        testHtmlAttribute(image, "border", "3");
    }

    @Test
    public void testCharset() {
        testHtmlAttribute(image, "charset", "utf-8");
    }

    @Test
    public void testCoords() {
        testHtmlAttribute(image, "coords", "circle: 150, 60, 60");
    }

    @Test
    public void testDir() {
        testDir(image);
    }

    @Test
    public void testHreflang() {
        testHtmlAttribute(image, "hreflang", "sk");
    }

    @Test
    public void testIsmap() {
        AttributeLocator<?> attr = image.getAttribute(new Attribute("ismap"));
        assertFalse(selenium.isAttributePresent(attr), "Attribute ismap should not be present.");

        selenium.click(pjq("input[type=radio][name$=ismapInput][value=true]"));
        selenium.waitForPageToLoad();

        assertTrue(selenium.isAttributePresent(attr), "Attribute ismap should be present.");
        assertEquals(selenium.getAttribute(attr), "ismap", "Attribute dir");
    }

    @Test
    public void testOnblur() {
        testFireEvent(Event.BLUR, image);
    }

    @Test
    public void testOnclick() {
        testFireEvent(Event.CLICK, image);
    }

    @Test
    public void testOndblclick() {
        testFireEvent(Event.DBLCLICK, image);
    }

    @Test
    public void testOnfocus() {
        testFireEvent(Event.FOCUS, image);
    }

    @Test
    public void testOnkeydown() {
        testFireEvent(Event.KEYDOWN, image);
    }

    @Test
    public void testOnkeypress() {
        testFireEvent(Event.KEYPRESS, image);
    }

    @Test
    public void testOnkeyup() {
        testFireEvent(Event.KEYUP, image);
    }

    @Test
    public void testOnmousedown() {
        testFireEvent(Event.MOUSEDOWN, image);
    }

    @Test
    public void testOnmousemove() {
        testFireEvent(Event.MOUSEMOVE, image);
    }

    @Test
    public void testOnmouseout() {
        testFireEvent(Event.MOUSEOUT, image);
    }

    @Test
    public void testOnmouseover() {
        testFireEvent(Event.MOUSEOVER, image);
    }

    @Test
    public void testOnmouseup() {
        testFireEvent(Event.MOUSEUP, image);
    }

    @Test
    public void testRel() {
        testHtmlAttribute(image, "rel", "metamer");
    }

    @Test
    public void testRendered() {
        JQueryLocator input = pjq("input[type=radio][name$=renderedInput][value=false]");
        selenium.click(input);
        selenium.waitForPageToLoad();

        assertFalse(selenium.isElementPresent(image), "Image should not be rendered when rendered=false.");
    }

    @Test
    public void testRev() {
        testHtmlAttribute(image, "rev", "metamer");
    }

    @Test
    public void testShape() {
        testHtmlAttribute(image, "shape", "default");
    }

    @Test
    public void testStyle() {
        testStyle(image, "style");
    }

    @Test
    public void testStyleClass() {
        testStyleClass(image, "styleClass");
    }

    @Test
    public void testTabindex() {
        testHtmlAttribute(image, "tabindex", "50");
    }

    @Test
    public void testTarget() {
        testHtmlAttribute(image, "target", "_blank");
    }

    @Test
    public void testTitle() {
        testTitle(image);
    }

    @Test
    public void testUsemap() {
        testHtmlAttribute(image, "usemap", "metamer");
    }
}
