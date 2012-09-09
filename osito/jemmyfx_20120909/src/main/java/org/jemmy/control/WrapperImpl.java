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


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import org.jemmy.env.Environment;


/**
 *
 * @author shura
 */
public class WrapperImpl implements Wrapper {

    private Hashtable<Class, Class<Wrap>> theWrappers;
    private Environment env;

    /**
     *
     * @param env
     */
    @SuppressWarnings("unchecked")
    public WrapperImpl(Environment env) {
        this.env = env;
        theWrappers = new Hashtable<Class, Class<Wrap>>();
    }

    /**
     *
     * @return
     */
    public Environment getEnvironment() {
        return env;
    }

    /**
     *
     * @param <P>
     * @param controlClass
     * @param wrapperClass
     */
    public <P> void add(Class controlClass, Class<Wrap> wrapperClass) {
        theWrappers.put(controlClass, wrapperClass);
        // TODO: Improve output
//        getEnvironment().getOutput().println("Added \"" + wrapperClass.getName() + "\"" +
//                " wrapper for \"" + controlClass.getName() + "\" control type");
    }

    /**
     *
     * @param <T>
     * @param controlClass
     * @param control
     * @return Wrap
     */
//    @SuppressWarnings("unchecked")
    public <T> Wrap<? extends T> wrap(Class<T> controlClass, T control) {
        Class cls = control.getClass();
        Class<Wrap> wrp;
        do {
            wrp = theWrappers.get(cls);
            if (wrp != null) {
                try {
                    return doWrap(control, cls, wrp);
                } catch (InstantiationException ex) {
                    throw new WrapperException(cls, wrp, ex);
                } catch (IllegalAccessException ex) {
                    throw new WrapperException(cls, wrp, ex);
                } catch (IllegalArgumentException ex) {
                    throw new WrapperException(cls, wrp, ex);
                } catch (InvocationTargetException ex) {
                    throw new WrapperException(cls, wrp, ex);
                } catch (NoSuchMethodException ex) {
                    throw new WrapperException(cls, wrp, ex);
                } catch (SecurityException ex) {
                    throw new WrapperException(cls, wrp, ex);
                }
            }
        } while ((cls = cls.getSuperclass()) != null);
        throw new WrapperException(control);
    }

    /**
     *
     * @param <T>
     * @param control
     * @param controlClass
     * @param wrapperClass
     * @return Wrap
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.IllegalArgumentException
     * @throws java.lang.reflect.InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    protected <T> Wrap<? extends T> doWrap(T control, Class controlClass, Class wrapperClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Constructor cns = null;
        Class cls = controlClass;
        do {
            try {
                cns = wrapperClass.getConstructor(Environment.class, cls);
            } catch (NoSuchMethodException e) {
            }
        } while ((cls = cls.getSuperclass()) != null);
        if (cns != null) {
            return (Wrap<T>) cns.newInstance(new Environment(env), control);
        } else {
            throw new WrapperException(controlClass, wrapperClass);
        }
    }
}
