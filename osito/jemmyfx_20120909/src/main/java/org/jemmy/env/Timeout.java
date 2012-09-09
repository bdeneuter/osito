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
package org.jemmy.env;

import org.jemmy.TimeoutExpiredException;
import org.jemmy.JemmyException;

/**
 * Represents one timeout.
 * @author Alexandre Iline (alexandre.iline@sun.com)
 */
public class Timeout extends Object implements Cloneable {

    private String name;
    private long value;
    private long startTime;

    /**
     * Constructor.
     * @param name Timeout name.
     * @param value Timeout value in milliseconds.
     */
    public Timeout(String name, long value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns timeout name.
     * @return timeout name.
     */
    public String getName() {
        return (name);
    }

    /**
     * Returns timeout value.
     * @return timeout value.
     */
    public long getValue() {
        return (value);
    }

    public void setValue(long value) {
        this.value = value;
    }

    /**
     * Sleeps for timeout value.
     */
    public void sleep() {
        if (getValue() > 0) {
            try {
                Thread.sleep(getValue());
            } catch (InterruptedException e) {
                throw (new JemmyException("Sleep " +
                        getName() +
                        " was interrupted!",
                        e));
            }
        }
    }

    /**
     * Starts timeout measuring.
     */
    public void start() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Checks if timeout has been expired after start() invocation.
     * @return true if timeout has been expired.
     */
    public boolean expired() {
        return (System.currentTimeMillis() - startTime > getValue());
    }

    /**
     * Throws a TimeoutExpiredException exception if timeout has been expired.
     * @throws TimeoutExpiredException if timeout has been expired after start() invocation.
     */
    public void check() {
        if (expired()) {
            throw (new TimeoutExpiredException(getName() +
                    " timeout expired!"));
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Timeout [" + name + ", " + value + "]";
    }

    @Override
    public Timeout clone() {
        return new Timeout(name, value);
    }
}
