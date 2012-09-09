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
package org.jemmy.action;


import org.jemmy.env.Environment;


/**
 * An action to get some value.
 * @param <T> 
 * @author shura
 */
public abstract class GetAction<T> extends Action {

    private boolean finished = false;
    private T result = null;

    /**
     *
     */
    public GetAction() {
    }

    /**
     *
     * @return
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     *
     * @return
     */
    public T getResult() {
        return result;
    }

    /**
     *
     * @param result
     */
    protected void setResult(T result) {
        this.result = result;
        finished = true;
    }

    /**
     * Dispatches action through the system UI queue to get the result.
     * @param env Environment to
     * {@linkplain Environment#getExecutor() get} executor and to pass to
     * {@linkplain ActionExecutor#execute(org.jemmy.env.Environment, boolean, 
     * org.jemmy.action.Action, java.lang.Object[]) execute()} method.
     * @param parameters Parameters to pass to {@linkplain
     * #run(java.lang.Object[]) run()} method.
     * @return value returned by {@linkplain #getResult() getResult()} method.
     */
    public T dispatch(Environment env, Object... parameters) {
        env.getExecutor().execute(env, true, this, parameters);
        return getResult();
    }

}
