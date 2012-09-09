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

import java.util.List;
import org.jemmy.Dimension;
import org.jemmy.image.pixel.Raster.Component;

/**
 *
 * @author shura
 */
public abstract class ColorMappingComparator implements RasterComparator {

    final private ColorMap left;
    final private ColorMap right;
    private RasterComparator subComparator;

    /**
     * 
     * @param left
     * @param right
     * @param subComparator
     */
    public ColorMappingComparator(ColorMap left, ColorMap right,
            RasterComparator subComparator) {
        this.subComparator = subComparator;
        this.left = left;
        this.right = right;
    }

    /**
     * 
     * @return
     */
    public RasterComparator getSubComparator() {
        return subComparator;
    }

    /**
     * 
     * @param subComparator
     */
    public void setSubComparator(RasterComparator subComparator) {
        this.subComparator = subComparator;
    }

    /**
     * 
     * @param both
     * @param subComparator
     */
    public ColorMappingComparator(ColorMap both, RasterComparator subComparator) {
        this(both, both, subComparator);
    }

    /**
     * 
     * @param image1
     * @param image2
     * @return
     */
    public boolean compare(Raster image1, Raster image2) {
        return subComparator.compare(map(image1, left), map(image2, right));
    }
    
    /**
     * 
     * @param image
     * @param map
     * @return
     */
    public WriteableRaster map(Raster image, ColorMap map) {
        WriteableRaster res = createView(image.getSize());
        double[] colors = new double[image.getSupported().length];
        double[] newColors = new double[image.getSupported().length];
        for (int x = 0; x < image.getSize().width; x++) {
            for (int y = 0; y < image.getSize().height; y++) {
                image.getColors(x, y, colors);
                map.map(image.getSupported(), colors, newColors);
                res.setColors(x, y, newColors);
            }
        }
        return res;
    }

    /**
     * 
     * @param size
     * @return
     */
    protected abstract WriteableRaster createView(Dimension size);

    /**
     * 
     * @return
     */
    public String getID() {
        return ColorMappingComparator.class.getName() + ":" + 
                left.getID() + "," + right.getID() + "(" +
                subComparator.getID() + ")";
    }

    /**
     * 
     */
    public interface ColorMap {

        /**
         * 
         * @param components
         * @param values 
         * @param newValues  
         */
        public void map(Component[] components, double[] values, double[] newValues);
        /**
         * 
         * @return
         */
        public String getID();
    }
}
