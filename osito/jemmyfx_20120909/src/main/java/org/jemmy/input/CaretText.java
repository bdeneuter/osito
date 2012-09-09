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

import org.jemmy.control.Wrap;
import org.jemmy.dock.Shortcut;
import org.jemmy.env.Environment;
import org.jemmy.interfaces.CaretOwner;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Keyboard.KeyboardModifier;
import org.jemmy.interfaces.Text;

/**
 *
 * @author shura
 */
public abstract class CaretText extends AbstractCaretOwner implements Text {

    static {
        Environment.getEnvironment().setPropertyIfNotSet(RegexCaretDirection.REGEX_FLAGS, 0);
    }

    TextCaret caret;
    TextImpl text;
    Wrap<?> wrap;
    /**
     *
     * @param wrap
     */
    public CaretText(Wrap<?> wrap) {
        this.wrap = wrap;
        caret = new TextCaret(wrap, this);
        text = new TextImpl(wrap) {

            public String text() {
                return CaretText.this.text();
            }
        };
    }

    public TextCaret caret() {
        return caret;
    }

    /**
     *
     * @return
     */
    protected int getFlags() {
        return (Integer)wrap.getEnvironment().
                getProperty(RegexCaretDirection.REGEX_FLAGS, 0);
    }

    public void type(String newText) {
        text.type(newText);
    }

    public void clear() {
        text.clear();
    }

    /**
     * Moves caret to a beginning/end of an <code>index</code>'th occurance of the regex.
     * @param regex
     * @param front
     * @param index
     */
    public void to(String regex, boolean front, int index) {
        caret().to(new RegexCaretDirection(this, this, regex, getFlags(), front, index));
    }

    /**
     * Moves caret to a beginning/end of the first occurance of the regex.
     * @param regex
     * @param front
     */
    public void to(String regex, boolean front) {
        to(regex, front, 0);
    }

    /**
     * Moves caret to a beginning the first occurance of the regex.
     * @param regex
     */
    public void to(String regex) {
        to(regex, true);
    }

    /**
     *
     * @param left
     * @param leftMods
     * @param right
     * @param rightMods
     */
    public void addNavKeys(KeyboardButton left, KeyboardModifier[] leftMods,
            KeyboardButton right, KeyboardModifier[] rightMods) {
        caret.addNavKeys(left, leftMods, right, rightMods);
    }

    /**
     *
     * @param left
     * @param right
     */
    public void addNavKeys(KeyboardButton left, KeyboardButton right) {
        addNavKeys(left, new KeyboardModifier[0], right, new KeyboardModifier[0]);
    }
}
