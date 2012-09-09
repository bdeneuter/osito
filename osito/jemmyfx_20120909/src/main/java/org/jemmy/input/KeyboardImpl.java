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
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Keyboard.KeyboardModifier;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.interfaces.Focusable;
import org.jemmy.interfaces.Modifier;

/**
 * KeyDriver
 *
 * @author Alexandre Iline(alexandre.iline@sun.com)
 */
public class KeyboardImpl implements Keyboard {

    CharBindingMap<KeyboardButton, KeyboardModifier> map;
    Environment env;
    Wrap<?> target;
    RobotDriver robotDriver;
    private boolean detached;
    private AWTMap awtMap;
    /**
     * Constructs a KeyRobotDriver object.
     * @param target
     */
    public KeyboardImpl(Wrap<?> target, AWTMap awtMap) {
        //TODO: super(target.getEnvironment().getTimeout(RobotDriver.ROBOT_DELAY_TIMEOUT_NAME));
        robotDriver = new RobotDriver(target.getEnvironment());
        this.env = target.getEnvironment();
        this.map = target.getEnvironment().getBindingMap();
        this.target = target;
        this.awtMap = awtMap;
    }

    static {
        //TODO: Environment.getEnvironment().setTimeout(new Timeout(RobotDriver.ROBOT_DELAY_TIMEOUT_NAME, 10));
        Environment.getEnvironment().setTimeout(new Timeout(PUSH.getName(), 100));
        Environment.getEnvironment().setBindingMap(new DefaultCharBindingMap());
    }

    private void runAction(Action action) {
        if(detached) {
            target.getEnvironment().getExecutor().executeDetached(target.getEnvironment(), false, action);
        } else {
            target.getEnvironment().getExecutor().execute(target.getEnvironment(), false, action);
        }
    }

    /**
     *
     * @return Environment
     */
    public Environment getEnvironment() {
        return env;
    }

    /**
     *
     * @param keyCode
     * @param modifiers
     * @param pushTime
     */
    public void pushKey(final int keyCode, final int modifiers, final Timeout pushTime) {
        runAction(new Action() {
            public void run(Object... parameters) {
                if(target.is(Focusable.class)) target.as(Focusable.class).focuser().focus();
                pressKey(keyCode, modifiers);
                pushTime.sleep();
                releaseKey(keyCode, modifiers);
            }
            @Override
            public String toString() {
                return "push " + keyCode + " key with " + modifiers + " modifiers";
            }
        });
    }

    /**
     *
     * @param keyChar
     * @param pushTime
     */
    public void typeChar(char keyChar, Timeout pushTime) {
        pushKey(pushTime, map.getCharKey(keyChar), map.getCharModifiers(keyChar));
    }

    /**
     * Press the keyboard key specified by keyCode preceding with
     * pressing of modifier buttons specified by modifiers
     * @param keyCode one of InputEvent.VK_* constants
     * @param modifiers combination of InputEvent.*_DOWN_MASK constants
     * @see java.awt.event.InputEvent
     */
    public void pressKey(final int keyCode, final int modifiers) {
        runAction(new Action() {
            public void run(Object... parameters) {
                robotDriver.pressKey(keyCode, modifiers);
            }
            @Override
            public String toString() {
                return "press " + keyCode + " key with " + modifiers + " modifiers";
            }
        });
    }

    /**
     * Release the keyboard key specified by keyCode and then release
     * all the modifier keys specified by modifiers
     * @param keyCode one of InputEvent.VK_* constants
     * @param modifiers combination of InputEvent.*_DOWN_MASK constants
     * @see java.awt.event.InputEvent
     */
    public void releaseKey(final int keyCode, final int modifiers) {
        runAction(new Action() {
            public void run(Object... parameters) {
                robotDriver.releaseKey(keyCode, modifiers);
            }
            @Override
            public String toString() {
                return "press " + keyCode + " key with " + modifiers + " modifiers";
            }
        });
    }

    /**
     *
     * @param keyCode
     */
    public void pressKey(int keyCode) {
        pressKey(keyCode, 0);
    }

    /**
     *
     * @param keyCode
     */
    public void releaseKey(int keyCode) {
        releaseKey(keyCode, 0);
    }

    /**
     *
     * @param keyCode
     * @param modifiers
     */
    public void pushKey(int keyCode, int modifiers) {
        pushKey(keyCode, modifiers, getEnvironment().getTimeout(PUSH.getName()));
    }

    /**
     *
     * @param keyCode
     */
    public void pushKey(int keyCode) {
        pushKey(keyCode, 0);
    }

    /**
     *
     * @param keyChar
     */
    public void typeChar(char keyChar) {
        typeChar(keyChar, getEnvironment().getTimeout(PUSH.getName()));
    }

    public Keyboard detached() {
        detached = true;
        return this;
    }

    public void pressKey(KeyboardButton key, Modifier... modifiers) {
        pressKey(awtMap.convert(key), awtMap.convert(modifiers));
    }

    public void pressKey(KeyboardButton key) {
        pressKey(awtMap.convert(key));
    }

    public void releaseKey(KeyboardButton key, Modifier... modifiers) {
        releaseKey(awtMap.convert(key), awtMap.convert(modifiers));
    }

    public void releaseKey(KeyboardButton key) {
        releaseKey(awtMap.convert(key));
    }

    public void pushKey(Timeout pushTime, KeyboardButton key, Modifier... modifiers) {
        pushKey(awtMap.convert(key), awtMap.convert(modifiers), pushTime);
    }

    public void pushKey(KeyboardButton key, Modifier... modifiers) {
        pushKey(awtMap.convert(key), awtMap.convert(modifiers));
    }

    public void pushKey(KeyboardButton key) {
        pushKey(awtMap.convert(key));
    }
}
