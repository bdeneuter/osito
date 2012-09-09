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

import java.lang.annotation.*;
import org.jemmy.interfaces.TypeControlInterface;

/**
 * This annotation should be used to annotate methods which turn a wrap 
 * into a control interface. It is, then, used in <code>Wrap.as(*)</code> and 
 * <code>Wrap.is(*)</code> methods and in <code>*Dock.as*()</code> methods.
 * @see Wrap#as(java.lang.Class) 
 * @see Wrap#as(java.lang.Class, java.lang.Class) 
 * @see Wrap#is(java.lang.Class) 
 * @see Wrap#is(java.lang.Class, java.lang.Class) 
 * @author shura
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface As {
    /**
     * This should point out what is the encapsulated type for 
     * <code>TypeControlInterface</code>
     * @see TypeControlInterface
     * @return 
     */
    Class value() default Void.class;
}
