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


package org.jemmy.interfaces;

import org.jemmy.control.Property;
import org.jemmy.control.Wrap;

/**
 * Interface representing an object which represents an integer value which
 * could be increased or decreased, such as scroll bar, slider, etc.
 * @author shura
 */
public interface Scroll extends CaretOwner {
    /**
     *
     */
    public static final String VERTICAL_PROP_NAME = "vertical";
    /**
     *
     */
    public static final String MAXIMUM_PROP_NAME = "maximum";
    /**
     *
     */
    public static final String MINIMUM_PROP_NAME = "minimum";
    /**
     * TODO javadoc
     * @return
     */
    @Property(MAXIMUM_PROP_NAME)
    public double maximum();
    /**
     *
     * @return
     */
    @Property(MINIMUM_PROP_NAME)
    public double minimum();
    /**
     *
     * @deprecated Use CaretOwner.position()
     * @return
     */
    @Property(Wrap.VALUE_PROP_NAME)
    public double value();
    /**
     *
     * @deprecated Use CaretOwner.caret()
     * @return
     */
    public Scroller scroller();
}
