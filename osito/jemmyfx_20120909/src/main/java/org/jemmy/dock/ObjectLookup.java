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
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.LookupCriteria;

/**
 * This should be used to allow to build dock lookup constructors which take
 * some values (such as text, value, orientation, etc) instead of lookup criteria.
 * Methods annotated with this should take class of the looked up control as a 
 * first parameter and return parent around that type.
 * @see Parent
 * @see LookupCriteria
 * @author shura
 */
@Target(ElementType.METHOD)
@Documented
public @interface ObjectLookup {
    /**
     * Description of the method parameters. To be used in the javadoc later.
     */
    String value();
}
