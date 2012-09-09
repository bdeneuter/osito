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

package org.jemmy.lookup;

import org.jemmy.resources.StringComparePolicy;

/**
 * Default comparison policy is StringComparePolicy.SUBSTRING
 * @param <T>
 * @author shura
 */
public abstract  class ByStringLookup<T> implements LookupCriteria<T> {

    private StringComparePolicy policy;
    private String text;

    /**
     * Default comparison policy is StringComparePolicy.SUBSTRING
     * @param text
     */
    protected ByStringLookup(String text) {
        this(text, 0);
    }

    /**
     * Default comparison policy is StringComparePolicy.SUBSTRING
     * @param text
     * @param index
     * @deprecated index is ignored
     */
    protected ByStringLookup(String text, int index) {
        this(text, StringComparePolicy.SUBSTRING, index);
    }

    /**
     *
     * @param text
     * @param policy
     */
    protected ByStringLookup(String text, StringComparePolicy policy) {
        this(text, policy, 0);
    }

    /**
     *
     * @param text
     * @param policy
     * @param index
     * @deprecated index is ignored
     */
    protected ByStringLookup(String text, StringComparePolicy policy, int index) {
        this.policy = policy;
        this.text = text;
    }

    /**
     *
     * @param control
     * @return
     */
    public abstract String getText(T control);

    /**
     *
     * @param control
     * @return
     */
    public boolean check(T control) {
        return policy.compare(text, getText(control));
    }

    @Override
    public String toString() {
        return "Text = " + text;
    }

}
