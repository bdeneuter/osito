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

package org.jemmy.interfaces;


import org.jemmy.Point;
import org.jemmy.dock.Shortcut;
import org.jemmy.env.Timeout;

/**
 *
 * @author shura
 */
public interface Mouse extends ControlInterface {
    /**
     *
     */
    public static final Timeout CLICK = new Timeout("mouse.click", 100);
    /**
     *
     */
    @Shortcut
    public void press();
    /**
     *
     * @param button
     */
    @Shortcut
    public void press(MouseButton button);
    /**
     *
     * @param button 
     * @param modifiers
     */
    @Shortcut
    public void press(MouseButton button, Modifier... modifiers);
    /**
     *
     */
    @Shortcut
    public void release();
    /**
     *
     * @param button
     */
    @Shortcut
    public void release(MouseButton button);
    /**
     *
     * @param button
     * @param modifiers
     */
    @Shortcut
    public void release(MouseButton button, Modifier... modifiers);
    /**
     *
     */
    @Shortcut
    public void move();
    /**
     *
     * @param p
     */
    @Shortcut
    public void move(Point p);
    /**
     *
     */
    @Shortcut
    public void click();
    /**
     *
     * @param count
     */
    @Shortcut
    public void click(int count);
    /**
     *
     * @param count
     * @param p
     */
    @Shortcut
    public void click(int count, Point p);
    /**
     *
     * @param count
     * @param p
     * @param button
     */
    @Shortcut
    public void click(int count, Point p, MouseButton button);
    /**
     *
     * @param count
     * @param p
     * @param button
     * @param modifiers
     */
    @Shortcut
    public void click(int count, Point p, MouseButton button, Modifier... modifiers);

    /*
     * This method turns mouse wheel.
     * @parem amount Positive or negative
     */
    @Shortcut
    public void turnWheel(int amount);

    /*
     * This method turns mouse wheel.
     * @parem amount Positive or negative
     */
    @Shortcut
    public void turnWheel(Point point, int amount);

    /*
     * This method turns mouse wheel.
     * @parem amount Positive or negative
     */
    @Shortcut
    public void turnWheel(Point point, int amount, Modifier... modifiers);

    /**
     * Detaches the implementation so that all actions of it will be ran detached.
     * @see org.jemmy.action.ActionExecutor#executeDetached(org.jemmy.env.Environment, org.jemmy.action.Action, java.lang.Object[])
     * @return
     */
    public Mouse detached();

    /**
     * Mouse button interface (i. e. BUTTON1, BUTTON2, etc.)
     * created to left the possibility for extention as enums can't be extended
     */
    public static interface MouseButton extends Button {

    }

    /**
     * Mouse modifier interface (i. e. BUTTON1_DOWN_MASK)
     * created to left the possibility for extention as enums can't be extended
     */
    public static interface MouseModifier extends Modifier {

    }

    /**
     * Mouse modifiers enum (i. e. BUTTON1_DOWN_MASK)
     * to be used in Keyboard interface methods
     */
    public static enum MouseModifiers implements MouseModifier {

        /**
         *
         */
        BUTTON1_DOWN_MASK,
        /**
         * 
         */
        BUTTON2_DOWN_MASK,
        /**
         *
         */
        BUTTON3_DOWN_MASK
    }

    /**
     * Mouse Buttons
     */
    public static enum MouseButtons implements MouseButton {
        /**
         *
         */
        BUTTON1,
        /**
         *
         */
        BUTTON2,
        /**
         *
         */
        BUTTON3
    }

}
