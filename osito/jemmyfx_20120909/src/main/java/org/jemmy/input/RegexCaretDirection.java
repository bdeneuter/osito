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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jemmy.JemmyException;
import org.jemmy.interfaces.CaretOwner;
import org.jemmy.interfaces.Text;

/**
 *
 * @author shura
 */
public class RegexCaretDirection extends CaretImpl.DirectionToPosition {

    /**
     *
     */
    public static final String REGEX_FLAGS = "";

    String regex;
    boolean front;
    int index;
    Text text;
    int flags;

    /**
     *
     * @param text
     * @param caretOwner
     * @param regex
     * @param flags
     * @param front
     * @param index
     */
    public RegexCaretDirection(Text text, CaretOwner caretOwner, String regex, int flags, boolean front, int index) {
        super(caretOwner, 0);
        this.text = text;
        this.regex = regex;
        this.flags = flags;
        this.front = front;
        this.index = index;
        if (index < 0) {
            throw new JemmyException("Index must be >=0 but is " + index, regex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    protected double position() {
        return position(text, regex, flags, front, index);
    }

    /**
     *
     * @param text
     * @param regex
     * @param flags
     * @param front
     * @param index
     * @return
     */
    public static int position(Text text, String regex, int flags, boolean front, int index) {
        Matcher matcher = Pattern.compile(regex, flags).matcher(text.text());
        for (int i = 0; i <= index; i++) {
            if (!matcher.find()) {
                throw new JemmyException(index + "'s occurance of \"" + regex + "\" not found.", text);
            }
        }
        return front ? matcher.start() : matcher.end();
    }
}
