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

package org.jemmy.input;

import org.jemmy.Dimension;
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Window;

/**
 *
 * @author shura
 */
public class WindowImpl implements Window {

    private Wrap control;
    private int offset;

    /**
     *
     * @param control
     * @param offset
     */
    public WindowImpl(Wrap control, int offset) {
        this.control = control;
        this.offset = offset;
    }

    /**
     *
     * @param dest
     */
    public void move(Point dest) {
        Point start = control.getClickPoint();
        Point target = new Point(start.x + dest.x, start.y + dest.y);
        control.drag().dnd(start, control, target);
    }

    /**
     *
     * @param size
     * @param direction
     */
    public void resize(Dimension size, Direction direction) {
        Point start = null, target = null;
        Dimension sizeDiff = new Dimension(size.width - control.getScreenBounds().width, size.height - control.getScreenBounds().height);
        switch(direction) {
            case NORTHWEST:
                start = new Point(offset, offset);
                target = new Point(start.x - sizeDiff.width, start.y - sizeDiff.height);
                break;
            case NORTHEAST:
                start = new Point(control.getScreenBounds().width - offset - 1, offset);
                target = new Point(start.x + sizeDiff.width, start.y - sizeDiff.height);
                break;
            case SOUTHEAST:
                start = new Point(control.getScreenBounds().width - offset - 1, control.getScreenBounds().height - offset - 1);
                target = new Point(start.x + sizeDiff.width, start.y + sizeDiff.height);
                break;
            case SOUTHWEST:
                start = new Point(offset, control.getScreenBounds().height - offset - 1);
                target = new Point(start.x - sizeDiff.width, start.y + sizeDiff.height);
                break;
        }
        control.drag().dnd(start, control, target);
    }

}
