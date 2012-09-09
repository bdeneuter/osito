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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import org.jemmy.JemmyException;
import org.jemmy.action.Action;
import org.jemmy.action.GetAction;
import org.jemmy.timing.TimedCriteria;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;

/**
 *
 * @author shura
 */
public class Timeline<T> {

    private LinkedList<TimedAction> list = new LinkedList<TimedAction>();
    private Environment env;
    private T control;
    private long startTime = 0;
    private long tic = 10;

    public Timeline(long startTime, Environment env, T control) {
        this.startTime = startTime;
        this.env = env;
        this.control = control;
    }

    public Timeline(long startTime, Wrap<? extends T> wrap) {
        this(startTime, wrap.getEnvironment(), wrap.getControl());
    }

    public Timeline(Environment env, T control) {
        this(0, env, control);
    }

    public Timeline(Wrap<T> wrap) {
        this(wrap.getEnvironment(), wrap.getControl());
    }

    public synchronized final Timeline<T> schedule(long when, TimedCriteria<T> frame) {
        if (when >= 0) {
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).when >= when) {
                        list.add(i, new TimedAction(frame, when));
                        return this;
                    }
                }
                list.add(new TimedAction(frame, when));
            } else {
                list.add(new TimedAction(frame, when));
            }
        }
        return this;
    }

    public synchronized final Timeline<T> schedule(long start, long until, long delta, TimedCriteria<T> frame) {
        long when = start;
        do {
            schedule(when, frame);
        } while ((when += delta) <= until);
        return this;
    }

    synchronized TimedAction next(long until) {
        if (list.get(0).when <= until) {
            return remove();
        }
        return null;
    }

    synchronized TimedAction remove() {
        return list.removeFirst();
    }

    public void start() {
        if (startTime > 0) {
            long before = System.currentTimeMillis();
            while (System.currentTimeMillis() < startTime + before) {
                try {
                    Thread.sleep(tic);
                } catch (InterruptedException ex) {
                    throw new JemmyException("Sleep interrupted.", ex);
                }
            }
        }
        long start = System.currentTimeMillis();
        while (hasMore()) {
            long now = System.currentTimeMillis() - start;
            TimedAction next = next(now);
            if (next != null) {
                env.getExecutor().execute(env,
                        true, next, Long.valueOf(now));
                if (!next.getResult()) {
                    throw new JemmyException("Check failed on " + now + ":", next.criteria);
                }
            }
            try {
                Thread.sleep(tic);
            } catch (InterruptedException ex) {
                throw new JemmyException("Sleep interrupted.", ex);
            }
        }
    }

    public boolean hasMore() {
        return list.size() > 0;
    }

    private class TimedAction extends GetAction<Boolean> {

        long when;
        TimedCriteria<T> criteria;

        public TimedAction(TimedCriteria<T> criteria, long when) {
            this.criteria = criteria;
            this.when = when;
        }

        @Override
        public void run(Object... parameters) {
            setResult(criteria.check(control, (Long) parameters[0]));
        }
    }
}
