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

import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Focus;

/**
 * Simple Focus implementation which clicks on a control to give focus.
 * @author shura
 */
public class ClickFocus<CONTROL> implements Focus {

    Wrap<? extends CONTROL> topControl;
    Point clickPoint;

    /**
     *
     * @param topControl a control to click on. Node that this could be
     * a control itself (the one we're giving the focus to) or a subcontrol
     * of it.
     */
    public ClickFocus(Wrap<? extends CONTROL> topControl, Point clickPoint) {
        this.topControl = topControl;
        this.clickPoint = clickPoint;
    }

    public ClickFocus(Wrap<? extends CONTROL> topControl) {
        this(topControl, null);
    }

    protected Wrap<? extends CONTROL> getTopControl() {
        return topControl;
    }

    /**
     * @{@inheritDoc}
     */
    public void focus() {
        if (clickPoint == null) {
            topControl.mouse().click();
        } else {
            topControl.mouse().click(1, clickPoint);
        }
    }
}
