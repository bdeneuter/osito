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
package org.jemmy.control;

import org.jemmy.env.Environment;

/**
 *
 * @author shura
 */
public class DefaultWrapper extends WrapperImpl {

    /**
     *
     * @param env
     * @param wrapList
     */
    @SuppressWarnings("unchecked")
    public DefaultWrapper(Environment env, Class<? extends Wrap>... wrapList) {
        super(env);
        addAnnotated(wrapList);
    }

    /**
     *
     * @param env
     */
    @SuppressWarnings("unchecked")
    public DefaultWrapper(Environment env) {
        super(env);
    }

    /**
     *
     * @param list
     */
    @SuppressWarnings("unchecked")
    public final void addAnnotated(Class<? extends Wrap>... list) {
        for (Class cn : list) {
            if (!cn.isAnnotationPresent(ControlType.class)) {
                throw new IllegalStateException("\"" + cn.getName() + "\"" +
                        " must be annotated with @Control");
            }
            if (cn.isAnnotationPresent(ControlType.class)) {
                ControlType cc = (ControlType) cn.getAnnotation(ControlType.class);
                for(Class ccv : cc.value()) {
                    add(ccv, cn);
                }
            }
        }
    }


}
