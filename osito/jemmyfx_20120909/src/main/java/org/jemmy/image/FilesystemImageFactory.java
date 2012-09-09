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


import java.io.File;


/**
 * This is an implementation of ImageFactory which loads images from filesystem
 * and captures using AWT Robot.
 * @author shura, KAM
 * @deprecated Use {@linkplain FilesystemImageLoader FilesystemImageLoader} instead.
 */
public class FilesystemImageFactory extends ImageFactoryImpl {

    public FilesystemImageFactory() {
        super(new AWTRobotCapturer(), new FilesystemImageLoader());
    }

    public static final String OUTPUT = AWTImage.class.getName() + ".OUTPUT";

    public File getImageRoot() {
        return ((FilesystemImageLoader) getImageLoader()).getImageRoot();
    }

    public void setImageRoot(File imageRoot) {
        ((FilesystemImageLoader) getImageLoader()).setImageRoot(imageRoot);
    }

}
