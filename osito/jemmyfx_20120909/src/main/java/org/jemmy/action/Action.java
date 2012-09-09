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

import org.jemmy.JemmyException;


/**
 *
 * @author shura, KAM
 */
public abstract class Action {

    private boolean interrupted = false;
    private long startTime = -1, endTime = 0, allowedTime = 0;
    private Throwable throwable = null;

    /**
     * Executes {@linkplain #run(java.lang.Object[]) run()} method of this
     * Action, saving the duration of its execution and storing any
     * RuntimeException and Error which may occur during its work.
     * @param parameters Parameters to pass to {@linkplain
     * #run(java.lang.Object[]) run()} method
     * @see #getThrowable()
     * @see #failed() 
     */
    public final void execute(Object... parameters) {
        startTime = System.currentTimeMillis();
        try {
            run(parameters);
        } catch (Error e) {
            throwable = e;
            throw e;
        } catch (RuntimeException e) {
            throwable = e;
            throw e;
        } catch (Exception e) {
            throwable = e;
            throw new JemmyException("Exception in action " + this.toString(), e);
        } finally {
            endTime = System.currentTimeMillis();
        }
    }

    /**
     *
     * @return
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     *
     * @return
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Should be used from {@linkplain #run(java.lang.Object[]) run()) method
     * to check whether execution time is withing allowed time
     * @return true if difference between current time and start time is less
     * then allowed time; false otherwice
     */
    protected boolean withinAllowedTime() {
        return System.currentTimeMillis() - startTime < allowedTime;
    }

    /**
     * 
     * @return
     */
    public long getAllowedTime() {
        return allowedTime;
    }

    /**
     *
     * @param allowedTime
     */
    public void setAllowedTime(long allowedTime) {
        this.allowedTime = allowedTime;
    }

    /**
     *
     * @param parameters
     */
    public abstract void run(Object... parameters) throws Exception;

    /**
     *
     * @return
     */
    public boolean isInterrupted() {
        return interrupted;
    }

    /**
     *
     */
    public void interrupt() {
        this.interrupted = true;
    }

    /**
     * Returns throwable that occurred during run() invocation.
     * @return Error or RuntimeException.
     */
    public Throwable getThrowable() {
        return throwable;
    }

    /**
     * Indicates whether action invocation failed.
     * @return true if some exception occurred during run() invocation.
     */
    public boolean failed() {
        return throwable != null;
    }

    /**
     * Override this method to provide action description which
     * will be printed into output. 
     * @return null If nothing should be printed into output.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
