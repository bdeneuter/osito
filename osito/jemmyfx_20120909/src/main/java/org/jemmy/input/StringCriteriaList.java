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
package org.jemmy.input;

import org.jemmy.env.Environment;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

/**
 * Defines logic of converting a list of strings (aka titles, texts) into a list of
 * LookupCriteria. It is to be extended for hierarchical data support
 * where string identification is applicable (such as trees, menus).
 * @author shura
 */
public abstract class StringCriteriaList<T> {

    private StringComparePolicy policy;

    public final static String STRING_COMPARE_POLICY_PROP_NAME =
            StringCriteriaList.class.getName() + ".string.compare.policy";

    protected StringCriteriaList(Environment env) {
        policy = (StringComparePolicy) env.getProperty(STRING_COMPARE_POLICY_PROP_NAME, StringComparePolicy.EXACT);
    }

    protected StringCriteriaList(StringComparePolicy policy) {
        this.policy = policy;
    }

    public StringComparePolicy getPolicy() {
        return policy;
    }

    public void setPolicy(StringComparePolicy policy) {
        this.policy = policy;
    }

    protected LookupCriteria<T>[] createCriteriaList(String[] texts) {
        LookupCriteria[] criteria = new LookupCriteria[texts.length];
        for(int i = 0; i < texts.length; i++) {
            criteria[i] = createCriteria(texts[i], policy);
        }
        return criteria;
    }

    protected abstract LookupCriteria<T> createCriteria(String text, StringComparePolicy policy);
}
