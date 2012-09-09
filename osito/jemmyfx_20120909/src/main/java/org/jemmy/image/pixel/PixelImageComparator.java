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

import java.util.Arrays;
import org.jemmy.Dimension;
import org.jemmy.env.Environment;
import org.jemmy.image.Image;
import org.jemmy.image.ImageComparator;
import org.jemmy.image.pixel.Raster.Component;

/**
 *
 * @author shura
 */
public abstract class PixelImageComparator implements ImageComparator {

    static {
        Environment.getEnvironment().setPropertyIfNotSet(RasterComparator.class, 
                new PixelEqualityRasterComparator(0));
//                new MaxDistanceComparator((double)1/0x8f));
    }

    private RasterComparator comparator = null;
    private Environment env = null;
    
    /**
     *
     * @param comparator
     */
    public PixelImageComparator(RasterComparator comparator) {
        this.comparator = comparator;
    }

    public PixelImageComparator(Environment env) {
        this.env = env;
    }

    public synchronized RasterComparator getRasterComparator() {
        if(comparator == null) {
            return env.getProperty(RasterComparator.class);
        } else {
            return comparator;
        }
    }

    /**
     *
     * @param one
     * @param two
     * @return
     */
    public static Dimension computeDiffSize(Raster one, Raster two) {
        if (one.getSize().equals(two.getSize())) {
            return one.getSize();
        } else {
            return null;
        }
    }

    public Image compare(Image image1, Image image2) {
        Raster pi1 = toRaster(image1);
        Raster pi2 = toRaster(image2);
        if (!getRasterComparator().compare(pi1, pi2)) {
            return toImage(computeDifference(pi1, pi2));
        } else {
            return null;
        }
    }

    /**
     *
     * @param image1
     * @param image2
     * @return
     */
    public WriteableRaster computeDifference(Raster image1, Raster image2) {
        Dimension size = computeDiffSize(image1, image2);
        if (size == null) {
            size = new Dimension(Math.max(image1.getSize().width, image2.getSize().width),
                    Math.max(image1.getSize().height, image2.getSize().height));
        }
        WriteableRaster res = createDiffRaster(image1, image2);
        double[] colors1 = new double[image1.getSupported().length];
        double[] colors2 = new double[image2.getSupported().length];
        double[] colorsRes = new double[res.getSupported().length];
        for (int x = 0; x < size.width; x++) {
            for (int y = 0; y < size.height; y++) {
                if (x < image1.getSize().width && y < image1.getSize().height) {
                    image1.getColors(x, y, colors1);
                } else {
                    Arrays.fill(colors1, 0);
                }
                if (x < image2.getSize().width && y < image2.getSize().height) {
                    image2.getColors(x, y, colors2);
                } else {
                    Arrays.fill(colors2, 1);
                }
                calcDiffColor(image1.getSupported(), colors1, image2.getSupported(), colors2, 
                        res.getSupported(), colorsRes);
                res.setColors(x, y, colorsRes);
            }
        }
        return res;
    }

    private static final Component[] diffComponents = {
        Component.RED, Component.BLUE, Component.GREEN
    };
    /**
     *
     * @param comps1 
     * @param colors1 
     * @param comps2 
     * @param colors2 
     * @param compsRes 
     * @param colorsRes  
     */
    protected void calcDiffColor(Raster.Component[] comps1, double[] colors1,
            Raster.Component[] comps2, double[] colors2, Raster.Component[] compsRes, double[] colorsRes) {
        double square1, square2;
        double dist = 0;
        for (Component c : diffComponents) {
            square1 = colors1[arrayIndexOf(comps1, c)];
            square2 = colors2[arrayIndexOf(comps2, c)];
            dist += (square2 - square1) * (square2 - square1);
        }
        for (Component c : diffComponents) {
            colorsRes[arrayIndexOf(compsRes, c)] = Math.sqrt(dist) / Math.sqrt(3);
        }
    }

    public String getID() {
        return getRasterComparator().getID();
    }

    /**
     *
     * @param image
     * @return
     */
    protected abstract Image toImage(Raster image);

    /**
     *
     * @param image
     * @return
     */
    protected abstract Raster toRaster(Image image);

    /**
     *
     * @param r1 
     * @param r2 
     * @return
     */
    protected abstract WriteableRaster createDiffRaster(Raster r1, Raster r2);

    /**
     * 
     * @param comps
     * @param comp
     * @return
     */
    public static int arrayIndexOf(Raster.Component[] comps, Raster.Component comp) {
        for (int i = 0; i < comps.length; i++) {
            if (comp == comps[i]) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unknown component " + comp);
    }
}
