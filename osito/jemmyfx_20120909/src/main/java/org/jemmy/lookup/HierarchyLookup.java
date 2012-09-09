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
import java.util.List;
import org.jemmy.control.Wrap;
import org.jemmy.control.Wrapper;
import org.jemmy.env.Environment;

/**
 *
 * @param <CONTROL>
 * @author shura
 */
public class HierarchyLookup<CONTROL> extends AbstractLookup<CONTROL> {

    ControlHierarchy hierarchy;

    /**
     *
     * @param env
     * @param hierarchy
     * @param wrapper
     * @param controlClass
     * @param criteria
     */
    public HierarchyLookup(Environment env, ControlHierarchy hierarchy, Wrapper wrapper, Class<CONTROL> controlClass, LookupCriteria<CONTROL> criteria) {
        super(env, controlClass, criteria, wrapper);
        this.hierarchy = hierarchy;
    }

    /**
     *
     * @param env
     * @param hierarchy
     * @param controlClass
     * @param criteria
     */
    public HierarchyLookup(Environment env, ControlHierarchy hierarchy, Class<CONTROL> controlClass, LookupCriteria<CONTROL> criteria) {
        this(env, hierarchy, Wrap.getWrapper(), controlClass, criteria);
    }

    @Override
    List getChildren(Object subParent) {
        if(subParent != null) {
            return hierarchy.getChildren(subParent);
        } else {
            return hierarchy.getControls();
        }
    }

    /**
     * 
     * @param out
     * @param lookup
     */
    @Override
    protected void dump(PrintStream out, Lookup<? extends CONTROL> lookup) {
        int size = lookup.size();
        for (int i = 0; i < size; i++) {
            CONTROL object = lookup.get(i);
            dumpOne(out, object, calcPrefix(object));
        }
    }

    private String calcPrefix(CONTROL child) {
        String res = "";
        Object sp = child;
        while((sp = hierarchy.getParent(sp)) != null) {
            res += PREFIX_DELTA;
        }
        return res;
    }
}
