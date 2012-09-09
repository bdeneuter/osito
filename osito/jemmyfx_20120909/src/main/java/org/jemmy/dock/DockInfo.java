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

package org.jemmy.dock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import org.jemmy.control.Wrap;

/**
 * This should be used on classes - inheritors of <code>Wrap</code> class to give
 * annotation processor some information.
 * @see Wrap
 * @see DockGenerator
 * @author shura
 */
@Target(ElementType.TYPE)
@Documented
public @interface DockInfo {
    /**
     * Desired name of the dock class, should one be generated.
     * Usually empty ("", as nulls are not allowed) in which case the calculated value
     * is taken whatever logic annotation processor decides to use.
     */
    String name() default "";
    
    /**
     * Should there be extra constructors which take another lookup criteria - a class
     * of a desired control? That class must be a subtype of the one wrapped by the wrap
     * class annotated with this annotation.
     */
    boolean generateSubtypeLookups() default false;
    
    /**
     * Should generated <code>wrap()</code> method return this class or 
     * <code>Wrap<? extends ...></code> and also should there be a constructor with
     * one parameter - the wrap.
     */
    boolean anonymous() default false;
    
    /**
     * Should the lookup constructors have <code>LookupCriteria&lt;Type&gt;...</code>
     * parameter or the <code>LookupCriteria&lt;Type&gt;</code> parameter.
     */
    boolean multipleCriteria() default true;
}
