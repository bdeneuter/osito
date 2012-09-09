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

package org.jemmy.image;


/**
 * This is an implementation of ImageFactory
 * which is able to load images through a given classloader and
 * captures using AWT Robot.
 * @author shura
 * @deprecated Use {@linkplain ClasspathImageLoader ClasspathImageLoader} instead.
 */
public class ClasspathImageFactory extends ImageFactoryImpl {

    public static final String OUTPUT = AWTImage.class.getName() + ".OUTPUT";

    public ClasspathImageFactory() {
        super(new AWTRobotCapturer(), new ClasspathImageLoader());
    }

    /**
     * Get the value of classLoader which is used to load images. By default
     * equal to class loader which loaded the ClasspathImageFactory class.
     *
     * @return the value of classLoader
     */
    public ClassLoader getClassLoader() {
        return ((ClasspathImageLoader) getImageLoader()).getClassLoader();
    }

    /**
     * Set the value of classLoader
     *
     * @param classLoader new value of classLoader
     */
    public void setClassLoader(ClassLoader classLoader) {
        ((ClasspathImageLoader) getImageLoader()).setClassLoader(classLoader);
    }

    public void setRootPackage(Package rootPackage) {
        ((ClasspathImageLoader) getImageLoader()).setRootPackage(rootPackage);
    }
}
