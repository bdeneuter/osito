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
import org.jemmy.control.Wrap;

/**
 *
 * @param <CONTROL>
 * @author shura
 */
public abstract class RelativeCoordinateLookup<CONTROL> extends CoordinateLookup<CONTROL> {

    private static final int MAX_SCREEN_SIZE = 100000;

    private Wrap wrap;
    private boolean includeControl;
    private int hr;
    private int vr;

    /**
     *
     * @param wrap
     * @param includeControl
     * @param hr
     * @param vr
     */
    public RelativeCoordinateLookup(Wrap wrap, boolean includeControl, int hr, int vr) {
        super(wrap.getScreenBounds());
        this.wrap = wrap;
        this.includeControl = includeControl;
        this.hr = hr;
        this.vr = vr;
    }

    /**
     *
     * @return
     */
    @Override
    protected Rectangle getArea() {
        return constructArea(wrap, includeControl, hr, vr);
    }

    private static Rectangle constructArea(Wrap wrap, boolean includeControl, int hr, int vr) {
        Rectangle res = new Rectangle();
        res.width = MAX_SCREEN_SIZE;
        res.height = MAX_SCREEN_SIZE;
        Rectangle bounds = wrap.getScreenBounds();
        if (hr != 0) {
            if (hr < 0) {
                res.x = -MAX_SCREEN_SIZE + bounds.x + (includeControl ? bounds.width : 0);
            } else {
                res.x = bounds.x + (includeControl ? 0 : bounds.width);
            }
        } else {
            res.x = -MAX_SCREEN_SIZE / 2 + bounds.x + (includeControl ? bounds.width / 2 : 0);
        }
        if (vr != 0) {
            if (vr < 0) {
                res.y = -MAX_SCREEN_SIZE + bounds.y + (includeControl ? bounds.height : 0);
            } else {
                res.y = bounds.y + (includeControl ? 0 : bounds.height);
            }
        } else {
            res.y = -MAX_SCREEN_SIZE / 2 + bounds.y + (includeControl ? bounds.height / 2 : 0);
        }
        return res;
    }

}
