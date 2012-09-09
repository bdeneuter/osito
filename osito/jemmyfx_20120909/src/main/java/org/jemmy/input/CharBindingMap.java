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

import org.jemmy.interfaces.Button;
import org.jemmy.interfaces.Modifier;

/**
 *
 * Defines char-to-key binding.  The generation of a symbol will,
 * in general, require modifier keys to be pressed prior to pressing
 * a primary key.  Classes that implement <code>CharBindingMap</code>
 * communicate what modifiers and primary key are required to generate
 * a given symbol.
 *
 * @param <BUTTON>
 * @param <MODIFIER>
 * @author Alexandre Iline (alexandre.iline@sun.com)
 */

public interface CharBindingMap<BUTTON extends Button, MODIFIER extends Modifier> {

    /**
     * Returns the code of the primary key used to type a symbol.
     * @param c Character.
     * @return a Button constant.
     */
    public BUTTON getCharKey(char c);

    /**
     * Returns the modifiers that should be pressed to type a symbol.
     * @param c Character.
     * @return an array of Modifier constants.
     */
    public MODIFIER[] getCharModifiers(char c);
}
