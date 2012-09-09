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

import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Menu;
import org.jemmy.interfaces.MenuOwner;
import org.jemmy.lookup.LookupCriteria;

/**
 * In most cases menu has a text associated with every menu item. This interface 
 * makes it easy to push the menu based on that text information.
 * @author shura
 */
public abstract class StringMenuOwner<T> extends StringCriteriaList<T>
        implements MenuOwner<T> {
    
    private static final String MENU_PATH_LENGTH_ERROR = "Menu path length should be greater than 0";

    public StringMenuOwner(Wrap<?> menuOwner) {
        super(menuOwner.getEnvironment());
    }

    /**
     * Pushes the menu using one string for one level of the menu. Comparison 
     * is done according to the policy.
     * @param texts 
     * @see #getPolicy() 
     */
    public void push(String... texts) {
        if(texts.length == 0) {
            throw new IllegalArgumentException(MENU_PATH_LENGTH_ERROR);
        }
        menu().push(createCriteriaList(texts));
    }

    /**
     * A shortcut to <code>menu().push(LookupCriteria<T> ...)</code>
     * @see #menu() 
     * @see Menu#push(org.jemmy.lookup.LookupCriteria<T>[]) 
     * @param criteria 
     */
    public void push(LookupCriteria<T>... criteria) {
        menu().push(criteria);
    }

    /**
     * Select a menu item using one string for one level of the menu. Comparison 
     * is done according to the policy.
     * @param texts 
     * @return wrap for the last selected item
     * @see #getPolicy() 
     */
    public Wrap<? extends T> select(String... texts) {
        if(texts.length == 0) {
            throw new IllegalArgumentException(MENU_PATH_LENGTH_ERROR);
        }
        return menu().select(createCriteriaList(texts));
    }

    /**
     * A shortcut to <code>menu().select(LookupCriteria<T> ...)</code>
     * @see #menu() 
     * @see Menu#select(org.jemmy.lookup.LookupCriteria<T>[]) 
     * @param criteria 
     */
    public Wrap<? extends T> select(LookupCriteria<T>... criteria) {
        return menu().select(criteria);
    }

}
