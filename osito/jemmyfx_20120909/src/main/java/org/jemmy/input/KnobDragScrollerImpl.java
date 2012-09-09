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

import org.jemmy.JemmyException;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.Vector;
import org.jemmy.env.Timeout;
import org.jemmy.action.Action;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Drag;
import org.jemmy.interfaces.Scroll;
import org.jemmy.interfaces.Scroller;

/**
 * Performs scrolling by doing d'n'd of a "knob" wrap. To be used for controls
 * like scroll bars, sliders.
 * @author shura
 */
public abstract class KnobDragScrollerImpl implements Scroller {

    public static final int MAX_SCROLL_ATTEMPTS = 5;

    Wrap<?> wrap;
    Scroll scroll;
    float dragDelta = 1;
    boolean reverse;

    /**
     *
     * @param wrap
     * @param scroll
     */
    public KnobDragScrollerImpl(Wrap<?> wrap, Scroll scroll) {
        this.wrap = wrap;
        this.scroll = scroll;
    }

    public KnobDragScrollerImpl(Wrap<?> wrap, Scroll scroll, boolean reverse) {
        this(wrap, scroll);
        this.reverse = reverse;
    }

    /**
     *
     * @return
     */
    public Wrap<?> getWrap() {
        return wrap;
    }

    /**
     *
     * @return
     */
    public abstract Vector getScrollVector();

    /**
     *
     * @param dragDelta
     */
    public void setDragDelta(float dragDelta) {
        this.dragDelta = dragDelta;
    }

    private void toKnob(Wrap<?> knob, Point inWrap) {
        inWrap.translate(wrap.getScreenBounds().x, wrap.getScreenBounds().y);
        inWrap.translate(-knob.getScreenBounds().x, -knob.getScreenBounds().y);
    }

    private void toWrap(Wrap<?> knob, Point inWrap) {
        inWrap.translate(knob.getScreenBounds().x, knob.getScreenBounds().y);
        inWrap.translate(-wrap.getScreenBounds().x, -wrap.getScreenBounds().y);
    }

    public void to(double value) {
        scroll.to(value);
    }

    public void to(final Direction condition) {
        wrap.getEnvironment().getExecutor().execute(wrap.getEnvironment(), false, new Action() {

            @Override
            public void run(Object... parameters) {
                int toOrig;
                int dragAttempt = 0;
                Timeout moveTimeout = wrap.getEnvironment().getTimeout(Drag.IN_DRAG_TIMEOUT).clone();
                while((toOrig = condition.to()) != 0) {
                    Vector axis = getScrollVector().
                            multiply(toOrig).setLenght(dragDelta);
                    if (reverse) {
                        axis.multiply(-1);
                    }
                    Vector shift = axis.clone();
                    Wrap<?> knob = getKnob();
                    Point orig = new Point(knob.getScreenBounds().getX() + knob.getScreenBounds().getWidth()/2, knob.getScreenBounds().getY() + knob.getScreenBounds().getHeight()/2);
                    knob.mouse().move(knob.toLocal(orig.getLocation()));
                    knob.mouse().press();
                    wrap.getEnvironment().getTimeout(Drag.BEFORE_DRAG_TIMEOUT).sleep();
                    try {
                        while (condition.to() == toOrig) {
                            wrap.getEnvironment().getTimeout(Drag.IN_DRAG_TIMEOUT).sleep();
//                            Rectangle old_pos = knob.getScreenBounds();
                            knob.mouse().move(knob.toLocal(orig.getLocation().translate(shift)));
//                            if (old_pos.equals(knob.getScreenBounds())) { // TODO: it would be better to check if we achieve maximum position
//                                break;
//                            }
                            if(scroll.position() == scroll.minimum() || scroll.position() == scroll.maximum()) {
                                break;
                            }
                            shift.add(axis);
                        }
                    } finally {
                        wrap.getEnvironment().getTimeout(Drag.BEFORE_DROP_TIMEOUT).sleep();
                        knob.mouse().release();
                    }
                    dragAttempt++;
                    if(dragAttempt >= MAX_SCROLL_ATTEMPTS) {
                        //did what we could
                        return;
                    }
                    //slow dow the scrolling
                    moveTimeout.setValue((long) (moveTimeout.getValue() * 1.5));
                }
            }
        });
    }

    /*
     * Create another class for this perhaps
    private void initialDrag(double value) {
    Point hp = getEndPoint(false);
    Point lp = getEndPoint(true);
    double xdiff = hp.x - lp.x;
    double ydiff = hp.y - lp.y;
    double ratio = value / (scroll.maximum() - scroll.minimum());
    Wrap<?> knob = getKnob();
    Point p = new Point(xdiff * ratio, ydiff * ratio);
    toKnob(knob, p);
    Point clickPoint = knob.getClickPoint();
    p.translate(clickPoint.x, clickPoint.y);
    knob.drag().dnd(clickPoint, knob, p);
    }
     * 
     */
    /**
     *
     * @return
     */
    protected abstract Wrap<?> getKnob();
}
