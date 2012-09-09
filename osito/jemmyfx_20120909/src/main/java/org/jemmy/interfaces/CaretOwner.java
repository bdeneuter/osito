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
import org.jemmy.dock.Shortcut;


/**
 * Interface representing an object which represents an integer value which
 * could be increased or decreased, such as scroll bar, slider, etc.
 * @author shura
 */
public interface CaretOwner extends ControlInterface {
    /**
     *
     * @return
     */
    @Property(Wrap.VALUE_PROP_NAME)
    public double position();
    /**
     *
     * @return
     */
    @Shortcut
    public Caret caret();

    /**
     * Utility method that invokes caret().to(Direction) with correct 
     * direction.
     * TODO: Remove this method.
     * @param position 
     */
    @Shortcut
    public void to(double position);

}
