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


import java.io.Serializable;


/**
 * Replacement for java.awt.Point
 * @author Alexander Kouznetsov <mrkam@mail.ru>
 */
public class Point implements Serializable{
    /**
     * The X coordinate of this <code>Point</code>.
     * If no X coordinate is set it will default to 0.
     *
     * @serial
     * @see #getLocation()
     * @see #move(int, int)
     */
    public int x;

    /**
     * The Y coordinate of this <code>Point</code>.
     * If no Y coordinate is set it will default to 0.
     *
     * @serial
     * @see #getLocation()
     * @see #move(int, int)
     */
    public int y;

    /*
     * JDK 1.1 serialVersionUID
     */
    private static final long serialVersionUID = -5276940640259749850L;

    /**
     * Constructs and initializes a point at the origin
     * (0,&nbsp;0) of the coordinate space.
     */
    public Point() {
        this(0, 0);
    }

    /**
     * Constructs and initializes a point with the same location as
     * the specified <code>Point</code> object.
     * @param       p a point
     */
    public Point(Point p) {
        this(p.x, p.y);
    }

    /**
     * Constructs and initializes a point at the specified
     * {@code (x,y)} location in the coordinate space.
     * @param x the X coordinate of the newly constructed <code>Point</code>
     * @param y the Y coordinate of the newly constructed <code>Point</code>
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs and initializes a point at the specified
     * {@code (x,y)} location in the coordinate space. All {@code double}
     * values are rounded and stored as {@code int} values.
     * @param x the X coordinate of the newly constructed <code>Point</code>
     * @param y the Y coordinate of the newly constructed <code>Point</code>
     */
    public Point(double x, double y) {
        this.x = (int) Math.round(x);
        this.y = (int) Math.round(y);
    }

    /**
     * {@inheritDoc}
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the location of this point.
     * @return      a copy of this point, at the same location
     * @see         org.jemmy.Point#setLocation(org.jemmy.Point)
     * @see         org.jemmy.Point#setLocation(int, int)
     */
    public Point getLocation() {
        return new Point(x, y);
    }

    /**
     * Sets the location of the point to the specified location.
     * @param       p  a point, the new location for this point
     * @return
     * @see         org.jemmy.Point#getLocation
     */
    public Point setLocation(Point p) {
        setLocation(p.x, p.y);
        return this;
    }

    /**
     * Changes the point to have the specified location.
     * <p>
     * Its behavior is identical with <code>move(int,&nbsp;int)</code>.
     * @param       x the X coordinate of the new location
     * @param       y the Y coordinate of the new location
     * @return self
     * @see         org.jemmy.Point#getLocation
     * @see         org.jemmy.Point#move(int, int)
     */
    public Point setLocation(int x, int y) {
        move(x, y);
        return this;
    }

    /**
     * Sets the location of this point to the specified double coordinates.
     * The double values will be rounded to integer values.
     * Any number smaller than <code>Integer.MIN_VALUE</code>
     * will be reset to <code>MIN_VALUE</code>, and any number
     * larger than <code>Integer.MAX_VALUE</code> will be
     * reset to <code>MAX_VALUE</code>.
     *
     * @param x the X coordinate of the new location
     * @param y the Y coordinate of the new location
     * @return self
     * @see #getLocation
     */
    public Point setLocation(double x, double y) {
        this.x = (int) Math.round(x);
        this.y = (int) Math.round(y);
        return this;
    }

    /**
     * Moves this point to the specified location in the
     * {@code (x,y)} coordinate plane. This method
     * is identical with <code>setLocation(int,&nbsp;int)</code>.
     * @param       x the X coordinate of the new location
     * @param       y the Y coordinate of the new location
     * @return self
     */
    public Point move(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Translates this point, at location {@code (x,y)},
     * by {@code dx} along the {@code x} axis and {@code dy}
     * along the {@code y} axis so that it now represents the point
     * {@code (x + dx,y + dy)}.
     *
     * @param       dx   the distance to move this point
     *                            along the X axis
     * @param       dy    the distance to move this point
     *                            along the Y axis
     * @return self
     */
    public Point translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        return this;
    }

    /**
     *
     * @param v
     * @return self
     */
    public Point translate(Vector v) {
        this.x = (int) Math.round(x + v.getX());
        this.y = (int) Math.round(y + v.getY());
        return this;
    }
    /**
     * Determines whether or not two points are equal. Two instances of
     * <code>Point</code> are equal if the values of their
     * <code>x</code> and <code>y</code> member fields, representing
     * their position in the coordinate space, are the same.
     * @param obj an object to be compared with this <code>Point</code>
     * @return <code>true</code> if the object to be compared is
     *         an instance of <code>Point</code> and has
     *         the same values; <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point pt = (Point)obj;
            return (x == pt.x) && (y == pt.y);
        }
        return super.equals(obj);
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.x;
        hash = 89 * hash + this.y;
        return hash;
    }

    /**
     * Returns a string representation of this point and its location
     * in the {@code (x,y)} coordinate space. This method is
     * intended to be used only for debugging purposes, and the content
     * and format of the returned string may vary between implementations.
     * The returned string may be empty but may not be <code>null</code>.
     *
     * @return  a string representation of this point
     */
    @Override
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }
}
