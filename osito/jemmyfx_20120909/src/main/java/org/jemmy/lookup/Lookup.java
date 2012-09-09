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

import java.io.PrintStream;
import org.jemmy.interfaces.*;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;


/**
 * A searcheable container of a set on UI controls.
 * @param <CONTROL>
 * @author shura
 */
public interface Lookup<CONTROL> extends Parent<CONTROL> {
    /**
     * Default wait control timeout.
     * @see Timeout
     * @see Environment
     */
    public static final Timeout WAIT_CONTROL_TIMEOUT = new Timeout("wait.control", 10000);
    /**
     * Reruns the search until the number of found components is equal or greater
     * than required.
     * @param count
     * @return this or another Lookup instance.
     */
    public Lookup<? extends CONTROL> wait(int count);
    /**
     * Creates an instance of the Wrap class for one of the found UI controls.
     * @see Wrap
     * @param index
     * @return
     */
    public Wrap<? extends CONTROL> wrap(int index);
    /**
     * Same as <code>wrap(0)</code>
     * @see #wrap(int)
     * @return
     */
    public Wrap<? extends CONTROL> wrap();

    /**
     * Returns one of the found UI controls itself.
     * @param index
     * @return
     */
    public CONTROL get(int index);
    /**
     * Same as <code>get(0)</code>
     * @see #get(int)
     * @return
     */
    public CONTROL get();

    /**
     * Same as <code>wrap(index).as(interfaceClass)</code>
     * @param <INTERFACE>
     * @param index
     * @param interfaceClass
     * @return
     * @see #wrap(int)
     * @see Wrap#as(java.lang.Class)
     */
    public <INTERFACE extends ControlInterface> INTERFACE as(int index, Class<INTERFACE> interfaceClass);
    /**
     * Same as <code>wrap().as(interfaceClass)</code>
     * @param <INTERFACE>
     * @param interfaceClass
     * @return
     * @see #wrap()
     * @see Wrap#as(java.lang.Class)
     */
    public <INTERFACE extends ControlInterface> INTERFACE as(Class<INTERFACE> interfaceClass);
    /**
     * Same as <code>wrap(index).as(interfaceClass, type)</code>
     * @param <TYPE>
     * @param <INTERFACE>
     * @param index
     * @param interfaceClass
     * @param type
     * @return
     * @see #wrap(int)
     * @see Wrap#as(java.lang.Class, java.lang.Class)
     */
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(int index, Class<INTERFACE> interfaceClass, Class<TYPE> type);
    /**
     * Same as <code>wrap().as(interfaceClass, type)</code>
     * @param <TYPE>
     * @param <INTERFACE>
     * @param interfaceClass
     * @param type
     * @return
     * @see #wrap(int)
     * @see Wrap#as(java.lang.Class, java.lang.Class)
     */
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type);

    /**
     *
     * @return
     */
    public int size();

    /**
     *
     * @param out
     */
    public void dump(PrintStream out);
}
