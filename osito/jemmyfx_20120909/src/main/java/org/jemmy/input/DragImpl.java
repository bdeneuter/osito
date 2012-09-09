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


import java.awt.event.InputEvent;
import org.jemmy.control.*;
import org.jemmy.Point;
import org.jemmy.action.Action;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.interfaces.Mouse.MouseButton;
import org.jemmy.interfaces.Drag;
import org.jemmy.interfaces.Modifier;
import org.jemmy.interfaces.Showable;


/**
 *
 * @author shura
 */
public class DragImpl implements Drag {

    /**
     *
     */
    public static final int DND_POINTS = 10;

    static {
        Environment.getEnvironment().setTimeout(BEFORE_DRAG_TIMEOUT);
        Environment.getEnvironment().setTimeout(BEFORE_DROP_TIMEOUT);
        Environment.getEnvironment().setTimeout(IN_DRAG_TIMEOUT);
    }

    private Wrap<?> source;
    private AWTMap map;

    /**
     *
     * @param source
     */
    public DragImpl(Wrap<?> source, AWTMap map) {
        this.source = source;
        this.map = map;
    }

    /**
     *
     * @param targetPoint
     */
    public void dnd(Point targetPoint) {
        dnd(source, targetPoint);
    }

    /**
     *
     * @param target
     * @param targetPoint
     */
    public void dnd(Wrap target, Point targetPoint) {
        dnd(source.getClickPoint(), target, targetPoint);
    }

    /**
     *
     * @param point
     * @param target
     * @param targetPoint
     */
    public void dnd(Point point, Wrap target, Point targetPoint) {
        dnd(point, target, targetPoint, InputEvent.BUTTON1_MASK);
    }

    /**
     *
     * @param point
     * @param target
     * @param targetPoint
     * @param button
     */
    public void dnd(Point point, Wrap target, Point targetPoint, int button) {
        dnd(point, target, targetPoint, button, 0);
    }

    /**
     *
     * @param point
     * @param target
     * @param targetPoint
     * @param button
     * @param modifiers
     */
    public void dnd(Point pointParam, final Wrap target, final Point targetPoint, final int button, final int modifiers) {
        final Point point = pointParam == null ? source.getClickPoint() : pointParam;
        source.getEnvironment().getExecutor().execute(target.getEnvironment(), false, new Action() {
            public void run(Object... parameters) {
                if(source.is(Showable.class)) ((Showable)source.as(Showable.class)).shower().show();
                source.mouse().move(point);
                source.mouse().press(map.convertMouseButton(button), map.convertModifiers(modifiers));
                source.getEnvironment().getTimeout(BEFORE_DRAG_TIMEOUT.getName()).sleep();
                Point intermediatePoint = new Point();
                int xDistance = target.getScreenBounds().x + targetPoint.x - source.getScreenBounds().x - point.x;
                int yDistance = target.getScreenBounds().y + targetPoint.y - source.getScreenBounds().y - point.y;
                int startX = point.x + source.getScreenBounds().x;
                int startY = point.y + source.getScreenBounds().y;
                int endX = startX + xDistance;
                int endY = startY + yDistance;
                for(int i = 0; i < DND_POINTS + 1; i++) {
                    intermediatePoint.x = startX + xDistance * i / DND_POINTS - source.getScreenBounds().x;
                    intermediatePoint.y = startY + yDistance * i / DND_POINTS - source.getScreenBounds().y;
                    source.mouse().move(intermediatePoint);
                    source.getEnvironment().getTimeout(IN_DRAG_TIMEOUT.getName()).sleep();
                }
                source.mouse().move(new Point(endX - source.getScreenBounds().x, endY - source.getScreenBounds().y));
                //target.mouse().move(targetPoint);
                source.getEnvironment().getTimeout(BEFORE_DROP_TIMEOUT.getName()).sleep();
                target.mouse().release(map.convertMouseButton(button), map.convertModifiers(modifiers));
            }

            @Override
            public String toString() {
                return "grag'n'drop from " + point + " to " + targetPoint + " of " + target.getClass() + " with mouse button " + button + " with " + modifiers + " modifiers";
            }

        }, button, modifiers);
    }

    public void dnd(Point point, Wrap target, Point targetPoint, MouseButton button) {
        dnd(point, target, targetPoint, map.convert(button));
    }

    public void dnd(Point point, Wrap target, Point targetPoint, MouseButton button, Modifier... modifiers) {
        dnd(point, target, targetPoint, map.convert(button), map.convert(modifiers));
    }

}
