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
package org.jemmy.image.pixel;

import org.jemmy.Dimension;
import org.jemmy.image.Image;
import org.jemmy.image.ImageComparator;

/**
 *
 * @author shura
 */
public abstract class ResizeComparator implements ImageComparator {

    private ImageComparator subComparator;
    private Mode mode;

    /**
     * 
     */
    public enum Mode {

        /**
         * 
         */
        LEFT,
        /**
         *
         */
        RIGTH,
        /**
         *
         */
        MAX
    };

    /**
     *
     * @param subComparator
     * @param mode  
     */
    public ResizeComparator(ImageComparator subComparator, Mode mode) {
        this.subComparator = subComparator;
        this.mode = mode;
    }

    /**
     * 
     * @param subComparator
     */
    public ResizeComparator(ImageComparator subComparator) {
        this(subComparator, Mode.MAX);
    }

    /**
     *
     * @return
     */
    public ImageComparator getSubComparator() {
        return subComparator;
    }

    /**
     *
     * @param subComparator
     */
    public void setSubComparator(ImageComparator subComparator) {
        this.subComparator = subComparator;
    }

    /**
     *
     * @param image1
     * @param image2
     * @return
     */
    public Image compare(Image image1, Image image2) {
        if(image1 == null || image2 == null) {
            return (image1 == null) ? image2 : image1;
        }
        Dimension size;
        switch (mode) {
            case LEFT:
                size = getSize(image1);
                break;
            case RIGTH:
                size = getSize(image2);
                break;
            case MAX:
                Dimension size1 = getSize(image1);
                Dimension size2 = getSize(image2);
                size = new Dimension(Math.max(size1.width, size2.width),
                        Math.max(size1.height, size2.height));
                break;
            default:
                throw new IllegalStateException("mode is not recognized");
        }
        Image r1 = resize(image1, size);
        Image r2 = resize(image2, size);
        if(r1 == null) {
            return image1;
        }
        if(r2 == null) {
            return image2;
        }
        return subComparator.compare(r1, r2);
    }

    /**
     *
     * @param image
     * @param size
     * @return
     */
    abstract public Image resize(Image image, Dimension size);

    /**
     * 
     * @param image
     * @return
     */
    abstract public Dimension getSize(Image image);

    /**
     *
     * @return
     */
    public String getID() {
        return ResizeComparator.class.getName() + "(" + subComparator.getID() + ")";
    }
}
