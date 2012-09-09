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

import org.jemmy.control.Wrap;
import org.jemmy.control.Wrapper;
import org.jemmy.env.Environment;
import org.jemmy.interfaces.Parent;

/**
 *
 * @param <T>
 * @author shura
 */
public class ParentImpl<T> extends AbstractParent<T> {

    private Class<T> type;
    private ControlHierarchy ch;
    private ControlList cl;
    private Environment env;
    private Wrapper wrapper;

    /**
     *
     * @param env
     * @param type
     * @param ch
     */
    public ParentImpl(Environment env, Class<T> type, ControlHierarchy ch) {
        this(env, type, ch, Wrap.getWrapper());
    }
    /**
     *
     * @param env
     * @param type
     * @param ch
     * @param wrapper
     */
    public ParentImpl(Environment env, Class<T> type, ControlHierarchy ch, Wrapper wrapper) {
        this.type = type;
        this.ch = ch;
        this.env = env;
        this.wrapper = wrapper;
    }

    /**
     *
     * @param env
     * @param type
     * @param cl
     */
    public ParentImpl(Environment env, Class<T> type, ControlList cl) {
        this(env, type, cl, Wrap.getWrapper());
    }
    /**
     *
     * @param env
     * @param type
     * @param cl
     * @param wrapper
     */
    public ParentImpl(Environment env, Class<T> type, ControlList cl, Wrapper wrapper) {
        this.type = type;
        this.cl = cl;
        this.env = env;
        this.wrapper = wrapper;
    }

    public <ST extends T> Lookup<ST> lookup(Class<ST> controlClass, LookupCriteria<ST> criteria) {
        if(ch != null) {
            return new HierarchyLookup<ST>(env, ch, wrapper, controlClass, criteria);
        } else if(cl != null) {
            return new PlainLookup<ST>(env, cl, wrapper, controlClass, criteria);
        }
        throw new IllegalStateException();
    }

    public Lookup<T> lookup(LookupCriteria<T> criteria) {
        return lookup(type, criteria);
    }

    public Class<T> getType() {
        return type;
    }

}
