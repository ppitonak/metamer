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
package org.richfaces.tests.metamer.ftest.richCalendar;

import org.jboss.test.selenium.locator.JQueryLocator;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;

/**
 * Abstract test case for calendar.
 * 
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
public abstract class AbstractCalendarTest extends AbstractMetamerTest {

    public enum Month {

        January, February, March, April, May, June, July, August, September, October, November, December;

        public Month previous() {
            if (ordinal() == 0) {
                return December;
            }
            return Month.values()[ordinal() - 1];
        }

        public Month next() {
            if (ordinal() == 11) {
                return January;
            }
            return Month.values()[ordinal() + 1];
        }
    }
    // basic parts
    protected JQueryLocator calendar = pjq("span[id$=calendar]");
    protected JQueryLocator inputs = pjq("span[id$=calendarPopup]");
    protected JQueryLocator input = pjq("input.rf-cal-inp");
    protected JQueryLocator image = pjq("img.rf-cal-btn");
    protected JQueryLocator button = pjq("button.rf-cal-btn");
    // popup
    protected JQueryLocator popup = pjq("table[id$=calendarContent]");
    protected JQueryLocator prevYearButton = pjq("td.rf-cal-tl:eq(0) > div");
    protected JQueryLocator nextYearButton = pjq("td.rf-cal-tl:eq(3) > div");
    protected JQueryLocator prevMonthButton = pjq("td.rf-cal-tl:eq(1) > div");
    protected JQueryLocator nextMonthButton = pjq("td.rf-cal-tl:eq(2) > div");
    protected JQueryLocator closeButton = pjq("td.rf-cal-tl:eq(4) > div");
    protected JQueryLocator monthLabel = pjq("td.rf-cal-hdr-month > div");
    // 0 = blank, 1 = Sun, 2 = Mon, 3 = Tue ...
    protected JQueryLocator weekDayLabel = pjq("td.rf-cal-day-lbl:eq({0})");
    // week = 1..6, day = 0..6
    protected JQueryLocator cellWeekDay = pjq("tr[id$=calendarWeekNum{0}] > td:eq({1})");
    // day = 0..41
    protected JQueryLocator cellDay = pjq("td.rf-cal-c:eq({0})");
    // 0..6
    protected JQueryLocator week = pjq("td.rf-cal-week:eq({0})");
    protected JQueryLocator cleanButton = pjq("td.rf-cal-tl-ftr:eq(1) > div");
    protected JQueryLocator timeButton = pjq("td.rf-cal-tl-ftr:eq(2) > div");
    protected JQueryLocator todayButton = pjq("td.rf-cal-tl-ftr:eq(4) > div");
    protected JQueryLocator applyButton = pjq("td.rf-cal-tl-ftr:eq(5) > div");
    // time panel
    protected JQueryLocator timePanel = pjq("table[id$=calendarEditor]");
    protected JQueryLocator hoursInput = pjq("input[id$=calendarTimeHours]");
    protected JQueryLocator hoursInputUp = pjq("div[id$=calendarTimeHoursBtnUp]");
    protected JQueryLocator hoursInputDown = pjq("div[id$=calendarTimeHoursBtnDown]");
    protected JQueryLocator minutesInput = pjq("input[id$=calendarTimeMinutes]");
    protected JQueryLocator minutesInputUp = pjq("div[id$=calendarTimeMinutesBtnUp]");
    protected JQueryLocator minutesInputDown = pjq("div[id$=calendarTimeMinutesBtnDown]");
    protected JQueryLocator okButton = pjq("td.rf-cal-time-layout-ok > div");
    protected JQueryLocator cancelButton = pjq("td.rf-cal-time-layout-cancel > div");

    protected JQueryLocator output = pjq("span[id$=output]");
}
