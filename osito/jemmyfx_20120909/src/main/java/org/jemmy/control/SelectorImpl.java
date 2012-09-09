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

import org.jemmy.JemmyException;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Selector;
import org.jemmy.interfaces.Showable;
import org.jemmy.timing.State;

/**
 *
 * @param <CONTROL>
 * @param <STATE>
 * @author shura
 */
public class SelectorImpl<CONTROL, STATE> implements Selector<STATE> {

    Wrap<? extends CONTROL> target;
    Selectable<STATE> selectable;

    /**
     *
     * @param target
     * @param selectable
     */
    public SelectorImpl(Wrap<? extends CONTROL> target, Selectable<STATE> selectable) {
        this.target = target;
        this.selectable = selectable;
    }

    /**
     *
     * @param state
     */
    @SuppressWarnings("unchecked")
    public void select(final STATE state) {
        if (target.is(Showable.class)) {
            target.as(Showable.class).shower().show();
        }
        int attempts = 0;
        if (!selectable.getState().equals(state)) {
            do {
                final STATE currentState = selectable.getState();
                if (attempts >= selectable.getStates().size()) {
                    throw new JemmyException("State is not reached in " + attempts + " attempts", state);
                }
                target.mouse().click(clickCount(state));
                target.getEnvironment().getWaiter(Wrap.WAIT_STATE_TIMEOUT.getName()).ensureState(new State() {

                    public Object reached() {
                        return selectable.getState().equals(currentState) ? null : "";
                    }

                    @Override
                    public String toString() {
                        return "selectable state (" + selectable.getState() + ") equals '" + state + "'";
                    }

                });
                attempts++;
            } while (!selectable.getState().equals(state));
        }
    }

    private int clickCount(STATE state) {
        int current = selectable.getStates().indexOf(selectable.getState());
        int desired = selectable.getStates().indexOf(state);
        if (desired >= current) {
            return desired - current;
        } else {
            return selectable.getStates().size() - current + desired;
        }
    }

    private class StateChangeState implements State<STATE> {

        Selectable<STATE> source;
        STATE original;

        public StateChangeState(Selectable<STATE> source) {
            this.source = source;
            this.original = source.getState();
        }

        public STATE reached() {
            return (source.getState() != original) ? source.getState() : null;
        }
    }
}
