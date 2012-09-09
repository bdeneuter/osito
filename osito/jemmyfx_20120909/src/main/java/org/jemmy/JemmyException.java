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
 * Contributor(s): Alexandre (Shura) Iline (shurymury@gmail.com),
 * Alexander Kouznetsov (mrkam@mail.ru).
 *
 * The Original Software is the Jemmy library.
 * The Initial Developer of the Original Software is Alexandre Iline.
 * All Rights Reserved.
 *
 */
package org.jemmy;


/**
 *
 * Parent of all Jemmy exceptions.
 * Exception can be thrown from inside jemmy methods,
 * if some exception occurs from code invoked from jemmy.
 *	
 * @author Alexandre Iline (alexandre.iline@oracle.com), Alexander Kouznetsov (Alexander.Kouznetsov@oracle.com)
 */

public class JemmyException extends RuntimeException {

    private Object object = null;

    /**
     * Constructor.
     * @param description An exception description.
     */
    public JemmyException(String description) {
        super(description);
    }

    /**
     * Constructor.
     * @param description An exception description.
     * @param innerException Exception from code invoked from jemmy.
     */
    public JemmyException(String description, Throwable innerException) {
        super(description, innerException);
    }

    /**
     * Constructor.
     * @param description An exception description.
     * @param object Object regarding which exception is thrown.
     */
    public JemmyException(String description, Object object) {
        this(description, null, object);
    }

    /**
     * Constructor.
     * @param description An exception description.
     * @param innerException Exception from code invoked from jemmy.
     * @param object Object regarding which exception is thrown.
     */
    public JemmyException(String description, Throwable innerException, Object object) {
        this(description + " (Object: " + object + ")", innerException);
        this.object = object;
    }

    /**
     * Returns "object" constructor parameter.
     * @return the Object value associated with the exception.
     */
    public Object getObject() {
        return(object);
    }

    /**
     * Returns inner exception.
     * @return An inner exception.
     * @deprecated Use getCause()
     */
    public Exception getInnerException() {
        if(getCause() instanceof Exception) {
            return (Exception)getCause();
        } else {
            return null;
        }
    }

    /**
     * Returns inner throwable.
     * @return An inner throwable.
     * @deprecated Use getCause();
     */
    public Throwable getInnerThrowable() {
        return getCause();
    }
}
