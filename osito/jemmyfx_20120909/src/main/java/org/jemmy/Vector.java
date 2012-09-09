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

package org.jemmy;

/**
 * The class for easy computations.
 * @author shura
 */
public class Vector {

    private double x;
    private double y;

    /**
     *
     * @param x
     * @param y
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param from
     * @param to
     */
    public Vector(Point from, Point to) {
        x = to.x - from.x;
        y = to.y - from.y;
    }

    /**
     *
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     *
     * @return
     */
    public double lenght() {
        return Math.sqrt(x*x + y*y);
    }

    /**
     *
     * @param newLenght
     * @return self
     */
    public Vector setLenght(double newLenght) {
        double lenght = lenght();
        x = x * newLenght / lenght;
        y = y * newLenght / lenght;
        return this;
    }

    /**
     * @param multiplier
     * @return self
     */
    public Vector multiply(double multiplier) {
        x*=multiplier;
        y*=multiplier;
        return this;
    }

    /**
     *
     * @return a clone
     */
    @Override
    public Vector clone() {
        return new Vector(x, y);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * Adds another vector <code>(x1 + x2, y1 + y2)</code>
     * @param v
     * @return self
     */
    public Vector add(Vector v) {
        x+=v.x;
        y+=v.y;
        return this;
    }


}
