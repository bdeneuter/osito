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
import org.jemmy.env.Timeout;


/**
 * Interface to execute user's action <b>at appropriate moment</b>.
 * @author shura
 */
public interface ActionExecutor {

    /**
     *
     */
    public static final String ACTION_EXECUTOR_PROPERTY = "action.executor";

    /**
     * Schedules to execute an action and waits for it to finish.
     * @param env Environment.
     * @param dispatch if true the action is executed on UI system dispatch 
     * thread. This is usually necessary to invoke methods of the UI to get
     * the correct state or to update it.
     * @param action Action to execute.
     * @param parameters Parameters to pass to 
     * {@linkplain Action#run(java.lang.Object[]) action.run()} method.
     */
    public void execute(Environment env, boolean dispatch, Action action, Object... parameters);

    /**
     * Schedules to execute an action and exits immediately. Used to be called
     * DoSomethingNoBlock operations in jemmy2.
     * @param env Environment.
     * @param dispatch if true the action is executed on UI system dispatch 
     * thread. This is usually necessary to invoke methods of the UI to get
     * the correct state or to update it.
     * @param action Action to execute.
     * @param parameters Parameters to pass to 
     * {@linkplain Action#run(java.lang.Object[]) action.run()} method.
     */
    public void executeDetached(Environment env, boolean dispatch, Action action, Object... parameters);

    /**
     * Checks whether the current thread is already performing an action.
     * @return true if the current thread is already performing an action.
     * @see AbstractExecutor#isDispatchThread() 
     */
    public boolean isInAction();

    /**
     * Waits for no activities to be going on. Implementation may be different
     * for different mechanisms.
     * @param waitTime maximum time for waiting.
     * @see AbstractExecutor#isQuiet() 
     */
    public void waitQuiet(Timeout waitTime);
}
