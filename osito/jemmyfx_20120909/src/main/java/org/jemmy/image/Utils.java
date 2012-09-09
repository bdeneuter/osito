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
package org.jemmy.image;


import org.jemmy.Dimension;
import org.jemmy.Point;
import org.jemmy.Rectangle;


/**
 *
 * @author Alexander Kouznetsov <mrkam@mail.ru>
 */
public class Utils {

    /**
     *
     * @param r
     * @return java.awt.Rectangle
     */
    public static java.awt.Rectangle convert(Rectangle r) {
        return new java.awt.Rectangle(r.x, r.y, r.width, r.height);
    }

    /**
     *
     * @param r
     * @return org.jemmy.Rectangle
     */
    public static Rectangle convert(java.awt.Rectangle r) {
        return new Rectangle(r.x, r.y, r.width, r.height);
    }

    /**
     * 
     * @param p
     * @return java.awt.Point
     */
    public static java.awt.Point convert(Point p) {
        return new java.awt.Point(p.x, p.y);
    }

    /**
     *
     * @param p
     * @return org.jemmy.Point
     */
    public static Point convert(java.awt.Point p) {
        return new Point(p.x, p.y);
    }

    /**
     *
     * @param d
     * @return java.awt.Dimension
     */
    public static java.awt.Dimension convert(Dimension d) {
        return new java.awt.Dimension(d.width, d.height);
    }

    /**
     *
     * @param d
     * @return org.jemmy.Dimension
     */
    public static Dimension convert(java.awt.Dimension d) {
        return new Dimension(d.width, d.height);
    }

}
