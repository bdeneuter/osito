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
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jemmy.JemmyException;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Modifier;
import org.jemmy.interfaces.Mouse.MouseButton;
import org.jemmy.interfaces.Mouse.MouseButtons;
import org.jemmy.interfaces.Mouse.MouseModifiers;


/**
 * Converts
 * @author Alexander Kouznetsov <mrkam@mail.ru>
 */
public class AWTMap {

    private static Map<Integer, KeyboardButton> int2key = new HashMap<Integer, KeyboardButton>();
    private static Map<Integer, Modifier> int2modifier = new HashMap<Integer, Modifier>();
    private static Map<Integer, MouseButton> int2button = new HashMap<Integer, MouseButton>();
    private static Map<KeyboardButton, Integer> key2int = new HashMap<KeyboardButton, Integer>();
    private static Map<Modifier, Integer> modifier2int = new HashMap<Modifier, Integer>();
    private static Map<MouseButton, Integer> button2int = new HashMap<MouseButton, Integer>();

    static {
        for (KeyboardButtons button : KeyboardButtons.values()) {
            String name = button.name();
            try {
                int key = KeyEvent.VK_UNDEFINED;
                if (name.length() == 2 && name.startsWith("D")) {
                    // digit
                    key = KeyEvent.class.getDeclaredField("VK_" + name.substring(1)).getInt(null);
                } else {
                    key = KeyEvent.class.getDeclaredField("VK_" + name).getInt(null);
                }
                int2key.put(key, button);
                key2int.put(button, key);
            } catch (NoSuchFieldException ex) {
                throw new JemmyException("Unable to recognize key", ex, button);
            } catch (SecurityException ex) {
                throw new JemmyException("Unable to recognize key", ex, button);
            } catch (IllegalArgumentException ex) {
                throw new JemmyException("Unable to recognize key", ex, button);
            } catch (IllegalAccessException ex) {
                throw new JemmyException("Unable to recognize key", ex, button);
            }
        }
        for (KeyboardModifiers modifier : KeyboardModifiers.values()) {
            String name = modifier.name();
            try {
                int key = InputEvent.class.getDeclaredField(name).getInt(null);
                int2modifier.put(key, modifier);
                modifier2int.put(modifier, key);
            } catch (NoSuchFieldException ex) {
                throw new JemmyException("Unable to recognize modifier", ex, modifier);
            } catch (SecurityException ex) {
                throw new JemmyException("Unable to recognize modifier", ex, modifier);
            } catch (IllegalArgumentException ex) {
                throw new JemmyException("Unable to recognize modifier", ex, modifier);
            } catch (IllegalAccessException ex) {
                throw new JemmyException("Unable to recognize modifier", ex, modifier);
            }
        }
        for (MouseModifiers modifier : MouseModifiers.values()) {
            String name = modifier.name();
            try {
                int key = InputEvent.class.getDeclaredField(name).getInt(null);
                int2modifier.put(key, modifier);
                modifier2int.put(modifier, key);
            } catch (NoSuchFieldException ex) {
                throw new JemmyException("Unable to recognize modifier", ex, modifier);
            } catch (SecurityException ex) {
                throw new JemmyException("Unable to recognize modifier", ex, modifier);
            } catch (IllegalArgumentException ex) {
                throw new JemmyException("Unable to recognize modifier", ex, modifier);
            } catch (IllegalAccessException ex) {
                throw new JemmyException("Unable to recognize modifier", ex, modifier);
            }
        }
        for (MouseButtons button : MouseButtons.values()) {
            String name = button.name();
            try {
                int key = InputEvent.class.getDeclaredField(name + "_MASK").getInt(null);
                int2button.put(key, button);
                button2int.put(button, key);
            } catch (NoSuchFieldException ex) {
                throw new JemmyException("Unable to recognize button", ex, button);
            } catch (SecurityException ex) {
                throw new JemmyException("Unable to recognize button", ex, button);
            } catch (IllegalArgumentException ex) {
                throw new JemmyException("Unable to recognize button", ex, button);
            } catch (IllegalAccessException ex) {
                throw new JemmyException("Unable to recognize button", ex, button);
            }
        }
    }

    /**
     * TODO Provide javadoc
     * @param button
     * @return One of InputEvent.VK_* constants
     * @see InputEvent
     */
    public int convert(KeyboardButton button) {
        try {
            return key2int.get(button);
        } catch(Exception e) {
            throw new JemmyException("Unable to recognize key", e, button);
        }
    }

    /**
     * TODO Provide javadoc
     * @param modifiers
     * @return
     */
    public int convert(Modifier... modifiers) {
        int result = 0;
        for (Modifier modifier : modifiers) {
            try {
                result |= modifier2int.get(modifier);
            } catch (Exception e) {
                throw new JemmyException("Unable to recognize modifier", e, modifier);
            }
        }
        return result;
    }

    /**
     * TODO Provide javadoc
     * @param button
     * @return
     */
    public int convert(MouseButton button) {
        try {
            return button2int.get(button);
        } catch (Exception e) {
            throw new JemmyException("Unable to recognize mouse button", e, button);
        }
    }

    /**
     * TODO Provide javadoc
     * @param key
     * @return
     */
    public KeyboardButton convertKeyboardButton(int key) {
        KeyboardButton res = int2key.get(key);
        if (res == null) {
            throw new JemmyException("Unable to recognize key", key);
        }
        return res;
    }

    /**
     * TODO Provide javadoc
     * @param modifiers
     * @return
     */
    public Modifier[] convertModifiers(int modifiers) {
        List<Modifier> result = new ArrayList<Modifier>();
        for (int key : int2modifier.keySet()) {
            if ((key & modifiers) != 0) {
                Modifier res = int2modifier.get(key);
                if (res == null) {
                    throw new JemmyException("Unable to recognize modifiers", modifiers);
                }
                result.add(res);
            }
        }
        return result.toArray(new Modifier[result.size()]);
    }

    /**
     * TODO Provide javadoc
     * @param button
     * @return
     */
    public MouseButton convertMouseButton(int button) {
        MouseButton res = int2button.get(button);
        if (res == null) {
            throw new JemmyException("Unable to recognize mouse button", button);
        }
        return res;
    }
}
