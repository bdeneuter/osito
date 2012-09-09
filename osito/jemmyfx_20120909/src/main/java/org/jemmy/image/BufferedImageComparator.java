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

import java.awt.image.BufferedImage;
import org.jemmy.env.Environment;
import org.jemmy.image.pixel.PixelImageComparator;
import org.jemmy.image.pixel.Raster;
import org.jemmy.image.pixel.RasterComparator;
import org.jemmy.image.pixel.WriteableRaster;

/**
 *
 * @author shura
 */
public class BufferedImageComparator extends PixelImageComparator {

    public BufferedImageComparator(RasterComparator comparator) {
        super(comparator);
    }

    public BufferedImageComparator(Environment env) {
        super(env);
    }

    @Override
    protected Image toImage(Raster image) {
        if(image instanceof AWTImage) {
            return (AWTImage)image;
        } else {
            throw new IllegalArgumentException("Unrecognized image type" + image.getClass().getName());
        }
    }

    @Override
    protected Raster toRaster(Image image) {
        if(image instanceof AWTImage) {
            return (AWTImage)image;
        } else {
            throw new IllegalArgumentException("Unrecognized image type" + image.getClass().getName());
        }
    }

    @Override
    protected WriteableRaster createDiffRaster(Raster r1, Raster r2) {
        AWTImage img2 = (AWTImage) r2;
        AWTImage img1 = (AWTImage) r1;
        return new AWTImage(new BufferedImage(
                Math.max(img1.getSize().width, img2.getSize().width), 
                Math.max(img1.getSize().height, img2.getSize().height), 
                img1.getTheImage().getType()));
    }
}
