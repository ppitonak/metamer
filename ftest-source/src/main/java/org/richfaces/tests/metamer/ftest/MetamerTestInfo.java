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

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author <a href="mailto:ppitonak@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public final class MetamerTestInfo {
    private MetamerTestInfo() {
    }

    public static String getConfigurationInfo() {
        Map<Field, Object> configuration = MatrixConfigurator.getCurrentConfiguration();
        StringBuffer info = new StringBuffer();
        if (!configuration.isEmpty()) {
            for (Entry<Field, Object> entry : configuration.entrySet()) {
                final String name = entry.getKey().getName();
                final Object value = entry.getValue();

                if (value != null) {
                    if (info.length() > 0) {
                        info.append(", ");
                    }
                    info.append(name + ": " + value);
                }
            }
        }

        return info.toString();
    }

    public static String getConfigurationInfoInParenthesses() {
        return "{ " + getConfigurationInfo() + " }";
    }
}
