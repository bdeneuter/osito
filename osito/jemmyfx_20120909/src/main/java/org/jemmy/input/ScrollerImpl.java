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
import org.jemmy.Rectangle;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.interfaces.Caret;
import org.jemmy.interfaces.CaretOwner;
import org.jemmy.interfaces.Scroll;
import org.jemmy.interfaces.Scroller;

/**
 * Performs scrolling by clicking at a certain position.
 * @author shura
 */
public abstract class ScrollerImpl extends CaretImpl implements Scroller {

    /**
     * @deprecated Use AdvancedScroller.SCROLL_TIMEOUT
     */
    public static final Timeout SCROLL_TIMEOUT = CaretImpl.SCROLL_TIMEOUT;

    Scroll scroll;

    /**
     *
     * @param target
     * @param caret
     */
    public ScrollerImpl(Wrap target, CaretOwner caret) {
        super(target, caret);
        scroll = new CaretScroll(caret);
        addScrollAction(new ScrollAction() {

            public void scrollTo(int direction) {
                getWrap().mouse().click(1, getScrollClickPoint(direction > 0));
            }
        });
    }

    /**
     * @param increase
     * @return  a point to click in order to decrease/increase the value
     */
    protected abstract Point getScrollClickPoint(boolean increase);

    /**
     * An auxiliary function to calculate click point, on the appropriate side
     * of the control depending on the parameters.
     * @param c
     * @param horizontal - horizontal or vertical
     * @param increase - increase or decrease
     * @param offset distance from the border
     * @return
     */
    public static Point createScrollPoint(Wrap c, boolean horizontal, boolean increase, int offset) {
        return createScrollPoint(c.getScreenBounds(), horizontal, increase, offset);
    }

    /**
     *
     * @param bounds
     * @param horizontal
     * @param increase
     * @param offset
     * @return
     */
    public static Point createScrollPoint(Rectangle bounds, boolean horizontal, boolean increase, int offset) {
        if(horizontal) {
            return new Point(increase ? (bounds.width - 1 - offset) : offset, bounds.height / 2);
        } else {
            return new Point(bounds.width / 2, increase ? (bounds.height - 1 - offset) : offset);
        }
    }

    /**
     * @deprecated Use to(double)
     */
    public void scrollTo(double value) {
        to(value);
    }

    /**
     * @deprecated Use to(Caret.Direction)
     * @param condition
     */
    public void scrollTo(ScrollCondition condition) {
        to(new ConditionDirection(condition, scroll));
    }

    /**
     *
     */
    public static class ConditionDirection implements Direction {
        ScrollCondition condition;
        Scroll scroll;

        /**
         *
         * @param condition
         * @param scroll
         */
        public ConditionDirection(ScrollCondition condition, Scroll scroll) {
            this.condition = condition;
            this.scroll = scroll;
        }

        public int to() {
            return condition.scrollToward(scroll);
        }
    }

    //only the value is used from it
    /**
     *
     */
    public static class CaretScroll implements Scroll {

        CaretOwner co;

        /**
         *
         * @param co
         */
        public CaretScroll(CaretOwner co) {
            this.co = co;
        }

        public double maximum() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public double minimum() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public double value() {
            return co.position();
        }

        public Scroller scroller() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public double position() {
            return co.position();
        }

        public Caret caret() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void to(double position) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

}
