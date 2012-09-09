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

package org.jemmy.interfaces;

import org.jemmy.JemmyException;
import org.jemmy.control.Wrap;

/**
 *
 * @author shura
 */
public class InterfaceException extends JemmyException {

    /**
     *
     * @param <T>
     * @param object
     * @param interfaceClass
     */
    public <T extends ControlInterface> InterfaceException(Wrap object, Class<T> interfaceClass) {
        super(interfaceClass.getName() + " is not implemented for " + object.getControl().getClass().getName() +
                " by " + object.getClass().getName(), object.getControl());
    }

    /**
     *
     * @param <CT>
     * @param <T>
     * @param object
     * @param interfaceClass
     * @param type
     */
    public <CT, T extends TypeControlInterface<CT>> InterfaceException(Wrap object, Class<T> interfaceClass, Class<CT> type) {
        super(interfaceClass.getName() + " is not implemented for " + object.getControl().getClass().getName() +
                " by " + object.getClass().getName() +
                " or " + type.getClass().getName() + " is not enclosed", object.getControl());
    }

}
