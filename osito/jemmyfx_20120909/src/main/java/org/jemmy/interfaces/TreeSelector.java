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
import org.jemmy.lookup.LookupCriteria;

/**
 * Represents a way to select an item within a hierarchy. Could be applied to
 * anything from tree to menu.
 * @author shura
 */
public interface TreeSelector<T> {
    /**
     * Selects an item. All implementations are assumed to perform all necessary
     * waiting for nodes to be expanded and the sub nodes shown, etc.
     * @param criteria used one per level.
     * @return a wrap for the last item, whatever it is
     */
    public abstract Wrap<? extends T> select(LookupCriteria<T>... criteria);
}
