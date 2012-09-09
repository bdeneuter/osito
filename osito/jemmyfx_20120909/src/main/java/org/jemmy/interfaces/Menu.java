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

import org.jemmy.lookup.LookupCriteria;

/**
 * Represents a hierarchical structure (hence extending TreeSelector) in which
 * elements not only could be selected but also "pushes", which is an action
 * typically performed with menu.<br/>
 * @author shura
 * @see MenuOwner
 */
public interface Menu<T> extends TreeSelector<T>{

    /**
     * Pushes a menu item conforming to the criteria. That would mean that all
     * intermediate items get expanded and the menus are shown, etc., etc. It is
     * up to implementation whether to call select first or to do it somehow 
     * differently.
     * @param criteria used one for one level. In case of a menu bar, 
     * for example, first criteria is to be used to find a top level menu, second - 
     * to find a menu underneath, etc
     */
    public abstract void push(LookupCriteria<T>... criteria);

}
