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


import java.util.LinkedList;
import org.jemmy.JemmyException;
import org.jemmy.TimeoutExpiredException;


/**
 *
 * @author shura, KAM
 */
class ActionQueue {

    private Thread queueThread;
    private final LinkedList<ActionRecord> queue;
    private boolean stop = false;

    public ActionQueue() {
        queue = new LinkedList<ActionRecord>();
        queueThread = new Thread(new Runnable() {

            public void run() {
                int size;
                while (!stop) {
                    synchronized (queue) {
                        size = queue.size();
                        if (size == 0) {
                            try {
                                queue.wait();
                            } catch (InterruptedException ex) {
                            }
                        }
                    }
                    if (size > 0) {
                        ActionRecord r;
                        synchronized (queue) {
                            r = queue.poll();
                        }
                        try {
                            r.execute();
                        } catch (Exception e) {
                            System.err.println("Action '" + r + "' failed with the following exception: ");
                            e.printStackTrace(System.err);
                            System.err.flush();
                        }
                        r.setCompleted(true);
                    }
                }
            }
        }, "ActionQueue.queueThread");
        queueThread.start();
    }

    public int actionsInQueue() {
        synchronized(queue) {
            return queue.size();
        }
    }

    public void stop() {
        stop = true;
    }

    /**
     * Returns internal ActionQueue event dispatching thread
     * @return queue dispatching thread of ActionQueue object
     */
    public Thread getQueueThread() {
        return queueThread;
    }

    /**
     * Schedules execution of an action throught the internal ActionQueue queue
     * and exits immediately
     * @param action action to execute
     * @param parameters parameters to pass to action.run() method
     */
    public void invoke(Action action, Object... parameters) {
        synchronized (queue) {
            queue.add(new ActionRecord(action, parameters));
            queue.notifyAll();
        }
    }

    /**
     * Schedules execution of an action through the internal ActionQueue queue
     * and waits until it is completed
     * @param action action to execute
     * @param parameters parameters to pass to action.run() method
     */
    public void invokeAndWait(Action action, Object... parameters) {
        ActionRecord r = new ActionRecord(action, parameters);
        synchronized (queue) {
            queue.add(r);
            queue.notifyAll();
        }
        r.waitCompleted();

        if (r.failed()) {
            throw new JemmyException("Action '" + r + "' invoked through ActionQueue failed", r.getThrowable());
        }
    }

    private class ActionRecord {

        Action action;
        Object[] parameters;
        boolean completed;
        boolean started;

        public ActionRecord(Action action, Object[] parameters) {
            this.action = action;
            this.parameters = parameters;
        }

        public boolean failed() {
            return action.failed();
        }

        public Throwable getThrowable() {
            return action.getThrowable();
        }

        public Object[] getParameters() {
            return parameters;
        }

        public boolean isCompleted() {
            return completed;
        }

        public synchronized void setCompleted(boolean completed) {
            this.completed = completed;
            notifyAll();
        }

        public void execute() {
            synchronized (this) {
                started = true;
                notifyAll();
            }
            action.execute(parameters);
        }

        public synchronized void waitCompleted() {
            try {
                while (!started) {
                    wait();
                }
                if (!completed) {
                    wait(action.getAllowedTime());
                    if (!completed) {
                        action.interrupt();
                        throw new TimeoutExpiredException("Action did not finish in " + action.getAllowedTime() + " ms: " + action);
                    }
                }
            } catch (InterruptedException ex) {
            }
        }

        @Override
        public String toString() {
            return action.toString();
        }
    }
}
