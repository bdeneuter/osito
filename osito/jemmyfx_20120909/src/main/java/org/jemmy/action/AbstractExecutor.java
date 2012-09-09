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
import org.jemmy.env.TestOut;
import org.jemmy.env.Timeout;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;

/**
 *
 * @author shura
 */
public abstract class AbstractExecutor implements ActionExecutor {

    /**
     * Default timeout for action {@linkplain Action#run(java.lang.Object[])
     * run()} method to be completed.
     */
    public static final Timeout MAX_ACTION_TIME = new Timeout("max.action.time", 60000);
    /**
     * Indentifies output which would be used to print information for all actions
     * executed not on event queue.
     * @see AbstractExecutor#execute(org.jemmy.env.Environment, boolean, org.jemmy.action.Action, java.lang.Object[])
     * @see AbstractExecutor#executeDetached(org.jemmy.env.Environment, boolean, org.jemmy.action.Action, java.lang.Object[])
     * @see Environment#getOutput(java.lang.String)
     */
    public static final String NON_QUEUE_ACTION_OUTPUT = "org.jemmy.action.AbstractExecutor.NON_QUEUE_ACTION_OUTPUT";
    /**
     * Indentifies output which would be used to print information for all actions
     * executed on event queue.
     * @see AbstractExecutor#execute(org.jemmy.env.Environment, boolean, org.jemmy.action.Action, java.lang.Object[])
     * @see AbstractExecutor#executeDetached(org.jemmy.env.Environment, boolean, org.jemmy.action.Action, java.lang.Object[])
     * @see Environment#getOutput(java.lang.String)
     */
    public static final String QUEUE_ACTION_OUTPUT = "org.jemmy.action.AbstractExecutor.QUEUE_ACTION_OUTPUT";
    private ActionQueue queue;

    /**
     *
     */
    public AbstractExecutor() {
        queue = new ActionQueue();
    }

    static {
        Environment.getEnvironment().initTimeout(MAX_ACTION_TIME);
        Environment.getEnvironment().initOutput(QUEUE_ACTION_OUTPUT, TestOut.getNullOutput());
        Environment.getEnvironment().initOutput(NON_QUEUE_ACTION_OUTPUT, TestOut.getNullOutput());
    }

    /**
     * 
     * @return
     */
    protected int actionsInQueue() {
        return queue.actionsInQueue();
    }

    /**
     * {@inheritDoc }
     * Prints out what action is executed into output
     * specified by either NON_QUEUE_ACTION_OUTPUT or QUEUE_ACTION_OUTPUT
     * depending whether the action is called on queue or not. No output provided for
     * nested actions - only the top level ones are printed.
     * @see TestOut#getOutput(java.lang.String)
     */
    public final void execute(Environment env, boolean dispatch, final Action action, Object... parameters) {
        printStrace(env, "Action: ", action);
        action.setAllowedTime(env.getTimeout(MAX_ACTION_TIME.getName()).getValue());
        if (dispatch) {
            executeQueue(env, action, parameters);
        } else {
            if (isInAction()) {
                action.execute(parameters);
            } else {
                queue.invokeAndWait(action, parameters);
            }
        }
    }

    /**
     * {@inheritDoc }
     * Prints out what action is executed into output
     * specified by either NON_QUEUE_ACTION_OUTPUT or QUEUE_ACTION_OUTPUT
     * depending whether the action is called on queue or not. No output provided for
     * nested actions - only the top level ones are printed.
     * @see TestOut#getOutput(java.lang.String)
     */
    public final void executeDetached(Environment env, boolean dispatch, final Action action, final Object... parameters) {
        printStrace(env, "Action detached: ", action);
        if (dispatch) {
            executeQueueDetached(env, action, parameters);
        } else {
            if (isInAction()) {
                new Thread(new Runnable() {

                    public void run() {
                        action.execute(parameters);
                    }
                }).start();
            } else {
                queue.invoke(action, parameters);
            }
        }
    }

    private void printStrace(Environment env, String text, Action action) {
        String toString = action.toString();
        if (toString != null && toString.length() > 0) {
            if (!isInAction()) {
                if (isOnQueue()) {
                    env.getOutput(QUEUE_ACTION_OUTPUT).println(text + action.toString());
                } else {
                    env.getOutput(NON_QUEUE_ACTION_OUTPUT).println(text + action.toString());
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public final boolean isInAction() {
        return queue.getQueueThread() == Thread.currentThread() || isOnQueue();
    }

    /**
     * Schedules to execute an action through the UI system's dispatch thread and
     * waits for the action to be completed. This method is called from 
     * {@linkplain #execute(org.jemmy.env.Environment, boolean, org.jemmy.action.Action, java.lang.Object[]) execute()}
     * method when it is invoked with dispatch argument set to true.
     * @param env Environment.
     * @param action action to execute.
     * @param parameters parameters to pass to {@linkplain Action#run(java.lang.Object[]) action.run()} method.
     */
    public abstract void executeQueue(Environment env, Action action, Object... parameters);

    /**
     * Schedules to execute an action through the UI system's dispatch thread and
     * exits immediately. This method is called from 
     * {@linkplain #executeDetached(org.jemmy.env.Environment, boolean, org.jemmy.action.Action, java.lang.Object[]) executeDetached()}
     * method when it is invoked with dispatch argument set to true.
     * @param env Environment.
     * @param action action to execute.
     * @param parameters parameters to pass to {@linkplain Action#run(java.lang.Object[]) action.run()} method.
     */
    public abstract void executeQueueDetached(Environment env, Action action, Object... parameters);

    /**
     * Checks whether this is invoked from the UI system event queue.
     * @return true if invoked from the UI system event queue.
     */
    public abstract boolean isOnQueue();

    /**
     * Waits for UI to became quiet which is determined using
     * {@linkplain #isQuiet() isQuiet()} method.
     * @param waitTime maximum time for waiting.
     */
    public void waitQuiet(Timeout waitTime) {
        new Waiter(waitTime).ensureState(new State<Object>() {

            public Object reached() {
                return isQuiet() ? true : null;
            }
        });
    }

    /**
     * Tells whether the UI is quiet which usually means that all scheduled
     * actions are dispatched and the queue is empty.
     * @see #waitQuiet(org.jemmy.env.Timeout) 
     * @return true if the UI is quiet.
     */
    protected abstract boolean isQuiet();
}
