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

/**
 *
 * @author shura
 */
public class MaxDistanceComparator extends ThresholdComparator{

    /**
     *
     * @param threshold 
     */
    public MaxDistanceComparator(double threshold) {
        super(0, Math.sqrt(3));
        setThreshold(threshold);
    }

    /**
     *
     * @param image1
     * @param image2
     * @return
     */
    public boolean compare(Raster image1, Raster image2) {
        Dimension size = PixelImageComparator.computeDiffSize(image1, image2);
        if (size == null) {
            return false;
        }
        double[] colors1 = new double[image1.getSupported().length];
        double[] colors2 = new double[image2.getSupported().length];
        double distance = 0;
        for (int x = 0; x < size.width; x++) {
            for (int y = 0; y < size.height; y++) {
                image1.getColors(x, y, colors1);
                image2.getColors(x, y, colors2);
                distance = Math.max(distance, AverageDistanceComparator.distance(image1.getSupported(), colors1, image2.getSupported(), colors2));
            }
        }
        return distance <= getThreshold();
    }

    /**
     *
     * @return
     */
    public String getID() {
        return MaxDistanceComparator.class.getName() + ":" + getThreshold();
    }
}
