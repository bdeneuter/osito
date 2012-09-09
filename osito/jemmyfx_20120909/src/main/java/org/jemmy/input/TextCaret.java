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
import org.jemmy.interfaces.CaretOwner;
import org.jemmy.interfaces.IntervalSelector;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifier;

/**
 *
 * @author shura
 */
public class TextCaret extends CaretImpl implements IntervalSelector {

    /**
     *
     * @param wrap
     * @param scroll
     */
    public TextCaret(Wrap<?> wrap, CaretOwner scroll) {
        super(wrap, scroll);
        addScrollAction(new KeyboardScrollAction(KeyboardButtons.LEFT, KeyboardButtons.RIGHT));
    }

    /**
     *
     * @param down
     * @param downMods
     * @param up
     * @param upMods
     */
    public void addNavKeys(KeyboardButton down, KeyboardModifier[] downMods,
            KeyboardButton up, KeyboardModifier[] upMods) {
        addScrollAction(new CaretImpl.KeyboardScrollAction(down, downMods, up, upMods));
    }

    /**
     *
     * @param down
     * @param up
     */
    public void addNavKeys(KeyboardButton down, KeyboardButton up) {
        addNavKeys(down, new KeyboardModifier[0], up, new KeyboardModifier[0]);
    }

    public void selectTo(double value) {
        getWrap().keyboard().pressKey(KeyboardButtons.SHIFT);
        to(value);
        getWrap().keyboard().releaseKey(KeyboardButtons.SHIFT);
    }

    public void selectTo(Direction condition) {
        getWrap().keyboard().pressKey(KeyboardButtons.SHIFT);
        to(condition);
        getWrap().keyboard().releaseKey(KeyboardButtons.SHIFT);
    }
}
