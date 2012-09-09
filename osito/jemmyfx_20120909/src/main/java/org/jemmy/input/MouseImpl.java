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

import org.jemmy.Rectangle;
import org.jemmy.interfaces.MouseTarget;
import org.jemmy.Point;
import java.awt.event.InputEvent;
import java.util.Arrays;
import org.jemmy.action.Action;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.interfaces.Modifier;
import org.jemmy.interfaces.Mouse;
import org.jemmy.interfaces.Showable;

/**
 *
 * @author shura
 */
public class MouseImpl implements Mouse {

    private Wrap<?> target;
    private RobotDriver robotDriver;
    private boolean detached = false;
    private AWTMap awtMap;

    static {
        if (Environment.getEnvironment().getTimeout(CLICK) == null) {
            Environment.getEnvironment().setTimeout(MouseImpl.CLICK);
        }
    }

    /**
     *
     * @param target
     */
    public MouseImpl(Wrap<?> target, AWTMap awtMap) {
        this.target = target;
        this.robotDriver = new RobotDriver(new Timeout("", 10));
        this.awtMap = awtMap;
    }

    public Mouse detached() {
        this.detached = true;
        return this;
    }

    private void runAction(Action action) {
        if (detached) {
            target.getEnvironment().getExecutor().executeDetached(target.getEnvironment(), false, action);
        } else {
            target.getEnvironment().getExecutor().execute(target.getEnvironment(), false, action);
        }
    }

    /**
     *
     */
    public void press() {
        press(InputEvent.BUTTON1_MASK);
    }

    /**
     *
     * @param button
     */
    public void press(int button) {
        press(button, 0);
    }

    /**
     *
     * @param button
     * @param modifiers
     */
    public void press(final int button, final int modifiers) {
        runAction(new Action() {

            public void run(Object... parameters) {
                robotDriver.pressMouse(button, modifiers);
            }

            @Override
            public String toString() {
                return "pressing mouse button " + button + " with " + modifiers + " modifiers";
            }
        });
    }

    /**
     *
     */
    public void release() {
        release(InputEvent.BUTTON1_MASK);
    }

    /**
     *
     * @param button
     */
    public void release(int button) {
        release(button, 0);
    }

    /**
     *
     * @param button
     * @param modifiers
     */
    public void release(final int button, final int modifiers) {
        runAction(new Action() {

            public void run(Object... parameters) {
                robotDriver.releaseMouse(button, modifiers);
            }

            @Override
            public String toString() {
                return "releasing mouse button " + button + " with " + modifiers + " modifiers";
            }
        });
    }

    /**
     *
     */
    public void move() {
        move(target.getClickPoint());
    }

    /**
     *
     * @param p
     */
    public void move(final Point p) {
        runAction(new Action() {

            public void run(Object... parameters) {
                robotDriver.moveMouse(getAbsolute(target, p));
            }

            @Override
            public String toString() {
                return "moving mouse to " + p;
            }
        });
    }

    /**
     *
     */
    public void click() {
        this.click(1);
    }

    /**
     *
     * @param count
     */
    public void click(int count) {
        this.click(count, null);
    }

    /**
     *
     * @param count
     * @param p Point to click, if null {@linkplain Wrap#getClickPoint()
     * Wrap.getClickPoint()} method is invoked to get the point to click.
     */
    public void click(int count, Point p) {
        this.click(count, p, InputEvent.BUTTON1_MASK);
    }

    /**
     *
     * @param count
     * @param p Point to click, if null {@linkplain Wrap#getClickPoint()
     * Wrap.getClickPoint()} method is invoked to get the point to click.
     * @param button
     */
    public void click(int count, Point p, int button) {
        this.click(count, p, button, 0);
    }

    /**
     *
     * @param count
     * @param p Point to click, if null {@linkplain Wrap#getClickPoint()
     * Wrap.getClickPoint()} method is invoked to get the point to click.
     * @param button
     * @param modifiers
     */
    public void click(final int count, final Point p, final int button, final int modifiers) {
        runAction(new Action() {

            public void run(Object... parameters) {
                if (target.is(Showable.class)) {
                    target.as(Showable.class).shower().show();
                }
                robotDriver.clickMouse(getAbsolute(target,
                        p == null ? target.getClickPoint() : p),
                        count, button, modifiers,
                        target.getEnvironment().getTimeout(CLICK));
            }

            @Override
            public String toString() {
                return "clicking " + awtMap.convertMouseButton(button) + " mouse button " + count + " times at " + p + " with " + Arrays.toString(awtMap.convertModifiers(modifiers)) + " modifiers";
            }
        });
    }

    static Point getAbsolute(MouseTarget target, Point p) {
        Rectangle screenBounds = target.getScreenBounds();
        return new Point(p.x + screenBounds.x, p.y + screenBounds.y);
    }

    public void press(MouseButton button) {
        press(awtMap.convert(button));
    }

    public void press(MouseButton button, Modifier... modifiers) {
        press(awtMap.convert(button), awtMap.convert(modifiers));
    }

    public void release(MouseButton button) {
        release(awtMap.convert(button));
    }

    public void release(MouseButton button, Modifier... modifiers) {
        release(awtMap.convert(button), awtMap.convert(modifiers));
    }

    public void click(int count, Point p, MouseButton button) {
        click(count, p, awtMap.convert(button));
    }

    public void click(int count, Point p, MouseButton button, Modifier... modifiers) {
        click(count, p, awtMap.convert(button), awtMap.convert(modifiers));
    }

    private void turn(final Point p, final int amount, final int modifiers) {
        runAction(new Action() {

            public void run(Object... parameters) {
                if (target.is(Showable.class)) {
                    target.as(Showable.class).shower().show();
                }
                robotDriver.turnWheel(getAbsolute(target,
                        p == null ? target.getClickPoint() : p),
                        amount, modifiers);
            }

            @Override
            public String toString() {
                return "turning wheel to " + amount + " with " + Arrays.toString(awtMap.convertModifiers(modifiers)) + " modifiers";
            }
        });
    }

    public void turnWheel(Point point, final int amount, Modifier... modifiers) {
        turn(point, amount, awtMap.convert(modifiers));
    }

    public void turnWheel(Point point, final int amount) {
        turn(point, amount, 0);
    }

    public void turnWheel(final int amount) {
        turn(null, amount, 0);
    }
}
