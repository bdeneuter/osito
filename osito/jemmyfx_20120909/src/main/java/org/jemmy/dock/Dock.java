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
package org.jemmy.dock;

import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Drag;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Mouse;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;

/**
 * Superclass for all "docks" - classes which simple provide API for lookup, interfaces
 * and properties.
 * @author shura
 */
public class Dock {

    private Wrap<?> wrap;

    protected Dock(Wrap<?> wrap) {
        this.wrap = wrap;
    }

    /**
     * Method which at the end actually get called from all dock lookup
     * constructors.
     * @param <T>
     * @param parent
     * @param controlType
     * @param index
     * @param criteria
     * @return 
     */
    protected static <T> Wrap<? extends T> lookup(Parent<? super T> parent, Class<T> controlType, int index, LookupCriteria<T>... criteria) {
        Lookup<T> lookup;
        if (criteria.length > 0) {
            lookup = parent.lookup(controlType, criteria[0]);
            for (int i = 1; i < criteria.length; i++) {
                lookup = lookup.lookup(controlType, criteria[i]);
            }
        } else {
            lookup = parent.lookup(controlType);
        }
        return lookup.wrap(index);
    }

    /**
     * 
     * @return Wrap instance obtainer through lookup 
     */
    public Wrap<?> wrap() {
        return wrap;
    }
    
    /**
     * 
     * @return Wrap instance obtainer through lookup 
     */
    public Object control() {
        return wrap.getControl();
    }
    
    /**
     * Shortcut to <code>wrap().mouse()</code> 
     */
    public Mouse mouse() {
        return wrap.mouse();
    }

    /**
     * Shortcut to <code>wrap().keyboard()</code> 
     */
    public Keyboard keyboard() {
        return wrap.keyboard();
    }

    /**
     * Shortcut to <code>wrap().drag()</code> 
     */
    public Drag drag() {
        return wrap.drag();
    }

    /**
     * Shortcut to <code>wrap().getScreenBounds()</code> 
     */
    public Rectangle bounds() {
        return wrap.getScreenBounds();
    }
    
    protected <P> P getProperty(GetAction<P> action) {
        action.execute();
        return action.getResult();
    }
}
