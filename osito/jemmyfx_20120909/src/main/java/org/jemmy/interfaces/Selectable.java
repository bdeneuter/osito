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

import java.util.List;
import org.jemmy.control.Property;
import org.jemmy.dock.Shortcut;

/**
 * Interface for a control with one selected state among the number of them.
 * @param <STATE>
 * @author shura, KAM
 */
public interface Selectable<STATE> extends TypeControlInterface<STATE> {

    /**
     *
     */
    public static final String STATES_PROP_NAME = "states";
    public static final String STATE_PROP_NAME = "state";
    
    /**
     * Returns all available states.
     * @return List of all states.
     */
    @Property(STATES_PROP_NAME)
    public List<STATE> getStates();

    /**
     * Returns currently selected state.
     * @return Selected state.
     */
    @Property(STATE_PROP_NAME)
    public STATE getState();

    /**
     * Returns Selector class instance that has methods to select state.
     * @return Selector class instance for the control.
     */
    @Shortcut
    public Selector<STATE> selector();

}
