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


import java.util.Enumeration;
import java.util.Hashtable;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifier;
import static org.jemmy.interfaces.Keyboard.KeyboardButtons.*;
import static org.jemmy.interfaces.Keyboard.KeyboardModifiers.*;



/**
 *
 * Default implementation of CharBindingMap interface.
 * Provides a mapping for the following symbols:<BR>
 * @see org.jemmy.CharBindingMap
 *
 * @author Alexandre Iline (alexandre.iline@sun.com)
 */

public class DefaultCharBindingMap implements CharBindingMap<KeyboardButton, KeyboardModifier> {

    private Hashtable<Character, CharKey> chars;

    /**
     * Constructor.
     */
    public DefaultCharBindingMap() {
        initMap();
    }

    /**
     * Returns the code of the primary key used to type a symbol.
     * @param c Character.
     * @return a KeyboardButton.
     * @see CharBindingMap#getCharKey(char)
     */
    public KeyboardButton getCharKey(char c) {
        CharKey charKey = (CharKey)chars.get(new Character(c));
        if (charKey != null) {
            return charKey.key;
        } else {
            return null;
        }
    }

    /**
     * Returns the modifiers that should be pressed to type a symbol.
     * @param c Character.
     * @return an array of KeyboardModifier constants.
     * @see CharBindingMap#getCharModifiers(char)
     * @see KeyboardModifier
     */
    public KeyboardModifier[] getCharModifiers(char c) {
        CharKey charKey = (CharKey)chars.get(new Character(c));
        if(charKey != null) {
            return charKey.modifiers;
        } else {
            return null;
        }
    }

    /**
     * Returns an array of all supported chars.
     * @return an array of chars representing the supported chars values.
     */
    public char[] getSupportedChars() {
        char[] charArray = new char[chars.size()];
        Enumeration keys = chars.keys();
        int index = 0;
        while(keys.hasMoreElements()) {
            charArray[index] = ((Character)keys.nextElement()).charValue();
        }
        return(charArray);
    }

    /**
     * Removes a char from supported.
     * @param c Symbol code.
     */
    public void removeChar(char c) {
        chars.remove(new Character(c));
    }

    /**
     * Adds a char to supported.
     * @param c Symbol code.
     * @param key key code.
     * @param modifiers a combination of InputEvent MASK fields.
     */
    public void addChar(char c, KeyboardButton key, KeyboardModifier... modifiers) {
        chars.put(new Character(c), new CharKey(key, modifiers));
    }

    private void initMap() {
        chars = new Hashtable<Character, CharKey>();
        //first add letters and digits represented by . fields
        KeyboardButtons[] buttons = KeyboardButtons.values();
        for(int i = 0; i < buttons.length; i++) {
            String name = buttons[i].name();
            String letter;
            if (name.length() == 1 && Character.isLetter(name.charAt(0))) {
                letter = name;
            } else if (name.length() == 2 && name.startsWith("D") && Character.isDigit(name.charAt(1))) {
                letter = name.substring(1);
            } else {
                continue;
            }
            addChar(letter.toLowerCase().charAt(0), buttons[i]);
            if(!letter.toUpperCase().equals(letter.toLowerCase())) {
                addChar(letter.toUpperCase().charAt(0), buttons[i], SHIFT_DOWN_MASK);
            }
        }
        //add special simbols
        addChar('\t', TAB);
        addChar(' ', SPACE);
        addChar('!', D1 , SHIFT_DOWN_MASK);
        addChar('"', QUOTE , SHIFT_DOWN_MASK);
        addChar('#', D3 , SHIFT_DOWN_MASK);
        addChar('$', D4 , SHIFT_DOWN_MASK);
        addChar('%', D5 , SHIFT_DOWN_MASK);
        addChar('&', D7 , SHIFT_DOWN_MASK);
        addChar('\'', QUOTE);
        addChar('(', D9 , SHIFT_DOWN_MASK);
        addChar(')', D0 , SHIFT_DOWN_MASK);
        addChar('*', D8 , SHIFT_DOWN_MASK);
        addChar('+', EQUALS , SHIFT_DOWN_MASK);
        addChar(',', COMMA);
        addChar('-', MINUS);
        addChar('.', PERIOD);
        addChar('/', SLASH);
        addChar(':', SEMICOLON , SHIFT_DOWN_MASK);
        addChar(';', SEMICOLON);
        addChar('<', COMMA , SHIFT_DOWN_MASK);
        addChar('=', EQUALS);
        addChar('>', PERIOD , SHIFT_DOWN_MASK);
        addChar('?', SLASH , SHIFT_DOWN_MASK);
        addChar('@', D2 , SHIFT_DOWN_MASK);
        addChar('[', OPEN_BRACKET);
        addChar('\\', BACK_SLASH);
        addChar(']', CLOSE_BRACKET);
        addChar('^', D6 , SHIFT_DOWN_MASK);
        addChar('_', MINUS , SHIFT_DOWN_MASK);
        addChar('`', BACK_QUOTE);
        addChar('{', OPEN_BRACKET , SHIFT_DOWN_MASK);
        addChar('|', BACK_SLASH , SHIFT_DOWN_MASK);
        addChar('}', CLOSE_BRACKET, SHIFT_DOWN_MASK);
        addChar('~', BACK_QUOTE , SHIFT_DOWN_MASK);
        addChar('\n', ENTER);
    }

    private static class CharKey {
        public KeyboardButton key;
        public KeyboardModifier modifiers[];
        public CharKey(KeyboardButton key, KeyboardModifier... modifiers) {
            this.key = key;
            this.modifiers = modifiers;
        }
    }

}
