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

package org.jemmy.timing;

import org.jemmy.JemmyException;
import org.jemmy.TimeoutExpiredException;
import org.jemmy.env.Timeout;

/**
 *
 * @author shura
 */
public class Waiter {
    /**
     *
     */
    public static final Timeout DEFAULT_DELTA = new Timeout("Default wait delta", 100);
    private long waitTime;
    private long delta;

    /**
     *
     * @param waitTime
     * @param delta
     */
    public Waiter(Timeout waitTime, Timeout delta) {
        this.waitTime = waitTime.getValue();
        this.delta = delta.getValue();
    }

    /**
     *
     * @param waitTime
     */
    public Waiter(Timeout waitTime) {
        this.waitTime = waitTime.getValue();
        this.delta = DEFAULT_DELTA.getValue();
    }

    /**
     *
     * @param <T>
     * @param state
     * @return
     */
    public <T> T waitState(State<T> state) {
        long start = System.currentTimeMillis();
        T res;
        while( System.currentTimeMillis() < start + waitTime) {
            res = state.reached();
            if(res != null) {
                return res;
            }
            try {
                Thread.sleep(delta);
            } catch (InterruptedException ex) {
                throw new JemmyException("Wait interrupted for: ", state);
            }
        }
        return null;
    }

    /**
     *
     * @param <T>
     * @param value
     * @param state
     * @return
     */
    public <T> T waitValue(final T value, final State<T> state) {
        State<T> st = new State<T>() {
            public T reached() {
                T res = state.reached();
                if(res != null && res.equals(value)) {
                    return res;
                } else {
                    return null;
                }
            }
        };
        return waitState(st);
    }

    /**
     *
     * @param <T>
     * @param state
     * @return
     */
    public <T> T ensureState(State<T> state) {
        T res = waitState(state);
        if(res == null) {
            throw new TimeoutExpiredException("State '" + state + "' has not been reached in " + waitTime + " milliseconds");
        }
        return res;
    }
    /**
     *
     * @param <T>
     * @param value
     * @param state
     * @return
     */
    public <T> T ensureValue(T value, State<T> state) {
        T res = waitValue(value, state);
        if (res == null) {
            throw new TimeoutExpiredException("State '" + state + "' has not been reached in " + waitTime + " milliseconds");
        }
        return res;
    }
}
