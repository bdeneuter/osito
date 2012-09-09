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

import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;

/**
 * Represents a container for UI controls.
 * @param <T> type of the control.
 * @author shura
 */
public interface Parent<T> extends TypeControlInterface<T> {
    /**
     * Searcher the hierarchy for objects extending <code>ST</code> which fit
     * the criteria.
     * @see Lookup
     * @see LookupCriteria
     * @param <ST>
     * @param controlClass
     * @param criteria
     * @return an instance of Lookup, which holds found controls.
     */
    public <ST extends T> Lookup<ST> lookup(Class<ST> controlClass, LookupCriteria<ST> criteria);
    /**
     * Same as <code>lookup(controlClass, new Any<ST>())</code>
     * @see #lookup(java.lang.Class, org.jemmy.lookup.LookupCriteria)
     * @param <ST>
     * @param controlClass
     * @return an instance of Lookup, which holds found controls.
     */
    public <ST extends T> Lookup<ST> lookup(Class<ST> controlClass);
    /**
     * Searcher the hierarchy for objects extending <code>T</code> which fit
     * the criteria.
     * @see Lookup
     * @see LookupCriteria
     * @param criteria
     * @return an instance of Lookup, which holds found controls.
     */
    public Lookup<T> lookup(LookupCriteria<T> criteria);
    /**
     * Same as <code>lookup(new Any<T>())</code>
     * @see #lookup(org.jemmy.lookup.LookupCriteria) 
     * @return an instance of Lookup, which holds found controls.
     */
    public Lookup<T> lookup();
}
