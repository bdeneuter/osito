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

package org.jemmy.lookup;

import org.jemmy.interfaces.Parent;

/**
 * Implements two shortcut methods.
 * @param <CONTROL>
 * @author shura
 */
public abstract class AbstractParent<CONTROL> implements Parent<CONTROL> {

    /**
     * Calls <code>lookup(controlClass, new Any<ST>())</code>
     * @see Any
     * @param <ST>
     * @param controlClass
     * @return
     */
    public <ST extends CONTROL> Lookup<ST> lookup(Class<ST> controlClass) {
        return lookup(controlClass, new Any<ST>());
    }

    /**
     * Calls <code>lookup(new Any<T>())</code>
     * @see Any
     * @return
     */
    public Lookup<CONTROL> lookup() {
        return lookup(new Any<CONTROL>());
    }

}
