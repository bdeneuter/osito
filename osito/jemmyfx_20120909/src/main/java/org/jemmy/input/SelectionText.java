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
import org.jemmy.interfaces.IntervalSelectable;
import org.jemmy.interfaces.IntervalSelector;

/**
 *
 * @author shura
 */
public abstract class SelectionText extends CaretText implements IntervalSelectable {

    /**
     *
     * @param wrap
     */
    public SelectionText(Wrap<?> wrap) {
        super(wrap);
    }

    /**
     * Selects <code>index</code>'th occurance of the regex.
     * @param regex
     * @param index
     */
    public void select(String regex, int index) {
        to(regex, true, index);
        caret().selectTo(new RegexCaretDirection(this, this, regex, getFlags(), false, index));
    }

    /**
     * Selects first occurance of the regex.
     * @param regex
     */
    public void select(String regex) {
        select(regex, 0);
    }

    /**
     * Retuns the selection portion of the text.
     * @return
     */
    public String selection() {
        int a = (int) anchor(); int p = (int) position();
        int start = (a < p) ? a : p;
        int end = (a < p) ? p : a;
        return text().substring(start, end);
    }
}
