/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at LICENSE.html or
 * http://www.sun.com/cddl.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this License Header
 * Notice in each file.
 *
 * If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com)
 *
 * The Original Software is the Jemmy library.
 * The Initial Developer of the Original Software is Alexandre Iline.
 * All Rights Reserved.
 *
 */

package org.jemmy.resources;

/**
 *
 * @author shura
 */
public interface StringComparePolicy {
    /**
     *
     */
    public static final StringComparePolicy EXACT = new ComparePolicy(true, true);
    /**
     *
     */
    public static final StringComparePolicy SUBSTRING = new ComparePolicy(false, true);
    /**
     *
     * @param golden
     * @param value
     * @return
     */
    public boolean compare(String golden, String value);
    /**
     *
     */
    static class ComparePolicy implements StringComparePolicy {
        boolean ce;
        boolean cc;
        /**
         *
         * @param ce if true then entire strings are compared, if false golden could be a substring of a value
         * @param cc case sensitive comparison policy
         */
        public ComparePolicy(boolean ce, boolean cc) {
            this.cc = cc;
            this.ce = ce;
        }

        /**
         *
         * @param golden
         * @param value
         * @return
         */
        public boolean compare(String golden, String value) {
            if (value == null) {
                return golden == null;
            } else if (golden == null) {
                return !ce;
            }
            String g, v;
            if(cc) {
                g = golden;
                v = value;
            } else {
                g = golden.toUpperCase();
                v = value.toUpperCase();
            }
            if(ce) {
                return v.equals(g);
            } else {
                return v.contains(g);
            }
        }
    }
}
