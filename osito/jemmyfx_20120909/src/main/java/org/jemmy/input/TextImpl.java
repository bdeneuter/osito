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

import org.jemmy.action.Action;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Focusable;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Text;
import org.jemmy.timing.State;
import static org.jemmy.interfaces.Keyboard.KeyboardButtons.*;

/**
 *
 * @author shura
 */
public abstract class TextImpl implements Text {

    private static final int DEFAULT_SELECT_ALL_CLICK_COUNT = 3;
    private Wrap<?> target;
    private int selectAllClickCount = DEFAULT_SELECT_ALL_CLICK_COUNT;
    boolean keyboardSelection;

    /**
     *
     * @param target
     * @param keyboardSelection
     */
    protected TextImpl(Wrap<?> target, boolean keyboardSelection) {
        this.target = target;
        this.keyboardSelection = keyboardSelection;
    }

    /**
     *
     * @param target
     */
    protected TextImpl(Wrap<?> target) {
        this(target, false);
    }

    /**
     *
     * @return
     */
    public Wrap<?> getWrap() {
        return target;
    }

    /**
     * Types text into the control. Wrap may implement Focusable.
     * @see Focusable
     * @param newText
     */
    public void type(final String newText) {
        target.getEnvironment().getExecutor().execute(target.getEnvironment(), false, new Action() {

            public void run(Object... parameters) {
                if (target.is(Focusable.class)) {
                    target.as(Focusable.class).focuser().focus();
                }
                char[] chars = newText.toCharArray();
                Keyboard kb = target.keyboard();
                for (char c : chars) {
                    kb.typeChar(c);
                }
                target.getEnvironment().getWaiter(Wrap.WAIT_STATE_TIMEOUT.getName()).ensureState(new State<Object>() {

                    public Object reached() {
                        return text().contains(newText) ? "" : null;
                    }

                    @Override
                    public String toString() {
                        return "text() equals '" + newText + "', text() = '" + text() + "'";
                    }
                });
            }

            @Override
            public String toString() {
                return "typing text \"" + newText + "\"";
            }
        });
    }

    /**
     * Selects all text within component by clicking 3 times on it or using
     * keyboard depending on the second argument passed to {@linkplain
     * #TextImpl(org.jemmy.control.Wrap, boolean) constructor}. Override if
     * needed.<p>
     *
     * <b>Warning!</b> In Java keyboard selection doesn't work with
     * NumLock turned On due to CR 4966137 'Robot presses Numpad del key
     * instead of normal Del key'.
     */
    protected void selectAll() {
        if (!keyboardSelection) {
            target.mouse().click(selectAllClickCount, target.getClickPoint());
        } else {
            Keyboard kbrd = target.keyboard();
            kbrd.pushKey(KeyboardButtons.HOME);
            kbrd.pushKey(KeyboardButtons.END, KeyboardModifiers.SHIFT_DOWN_MASK);
            kbrd.pushKey(KeyboardButtons.DELETE);
        }
    }

    /**
     * Clears text by pressing End and then Delete and Backspace until the text
     * is cleared.
     */
    public void clear() {
        target.getEnvironment().getExecutor().execute(target.getEnvironment(),
                false, new Action() {

            public void run(Object... parameters) {
                if (target.is(Focusable.class)) {
                    target.as(Focusable.class).focuser().focus();
                }
                target.keyboard().pushKey(END);
                while(text().length() > 0 && withinAllowedTime()) {
                    target.keyboard().pushKey(BACK_SPACE);
                    target.keyboard().pushKey(DELETE);
                }
            }

            @Override
            public String toString() {
                return "clearing text";
            }
        });
    }
}
