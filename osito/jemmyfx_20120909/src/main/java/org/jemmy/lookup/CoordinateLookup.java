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
package org.jemmy.lookup;

import org.jemmy.Rectangle;

/**
 * Lookup that matches all the controls with the bounds that lay inside 
 * the given rectangle. The way to determine bounds of any control is 
 * implemented by the child class in @{@linkplain #getBounds(java.lang.Object)} 
 * method.
 * @param <CONTROL> 
 * @author shura
 */
public abstract class CoordinateLookup<CONTROL> implements LookupCriteria<CONTROL>{

    private Rectangle area;

    /**
     *
     * @param area Rectangle area to lookup up for controls that reside inside it
     */
    public CoordinateLookup(Rectangle area) {
        this.area = area;
    }

    /**
     *
     * @return Rectangle area to lookup up for controls that reside inside it
     */
    protected Rectangle getArea() {
        return area;
    }

    /**
     *
     * @param control
     * @return logical bounds for the control that has to be inside the given
     * rectangle to match the lookup
     */
    protected abstract Rectangle getBounds(CONTROL control);

    public boolean check(CONTROL control) {
        Rectangle a = getArea();
        Rectangle bounds = getBounds(control);
        return bounds.x >= a.x && bounds.y >= a.y &&
                (bounds.x + bounds.width) <= (a.x + a.width) &&
                (bounds.y + bounds.height) <= (a.y + a.height);
    }

    @Override
    public String toString() {
        return "CoordinateLookup[area=" + area + "]";
    }
}
