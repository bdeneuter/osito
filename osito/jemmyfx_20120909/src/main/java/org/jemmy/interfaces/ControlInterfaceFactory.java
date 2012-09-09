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


import org.jemmy.control.Wrap;

/**
 * This defines an interface to provide flexible way to control
 * test execution from outside. Check Wrap sources code for use case.
 * The interface is not intended to be used directly from test.
 * @see org.jemmy.control.Wrap
 * @author shura
 */
public interface ControlInterfaceFactory {
    /**
     * Instantiates interface.
     * @param <INTERFACE> ControlInterface type
     * @param control control to provide the interface for
     * @param interfaceClass ControlInterface type
     * @return ControlInterface instance or null for an unknown type
     */
    public <INTERFACE extends ControlInterface> INTERFACE create(Wrap<?> control, Class<INTERFACE> interfaceClass);
    /**
     *
     * Instantiates interface.
     * @param <TYPE>
     * @param <INTERFACE> ControlInterface type
     * @param control control to provide the interface for
     * @param interfaceClass ControlInterface type
     * @param type Incapsulated type
     * @return ControlInterface instance or null for an unknown type
     */
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE create(Wrap<?> control, Class<INTERFACE> interfaceClass, Class<TYPE> type);
}
