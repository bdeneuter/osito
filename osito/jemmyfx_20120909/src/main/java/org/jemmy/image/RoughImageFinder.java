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

import org.jemmy.Point;
import java.awt.image.BufferedImage;

/**
 * Performs "rough" image search.
 *
 * @author Alexandre Iline (alexandre.iline@sun.com)
 */
public class RoughImageFinder implements ImageFinder {
    double roughness = .0;
    int bigWidth, bigHeight;
    int[][] bigPixels;

    /**
     * Creates an instance allowing to find an image inside the one
     * passed as parameter with some "roughness".
     * @param area - Image to search in.
     * @param roughness - Allowed 
     */
    public RoughImageFinder(BufferedImage area, double roughness) {
        this.roughness = roughness;
        bigWidth  = area.getWidth();
        bigHeight = area.getHeight();
        bigPixels = new int[bigWidth][bigHeight];
        for(int x = 0; x < bigWidth; x++) {
            for(int y = 0; y < bigHeight; y++) {
                bigPixels[x][y] = area.getRGB(x, y);
            }
        }
    }

    /**
     * Performs "rough" search.
     * @param image an image to search.
     * @param index an ordinal image location index.
     * @return Point where number of unmatching pixels less or equal to
     * <code>image1.getWidth() * image1.getHeight() * roughness<code>
     */
    public Point findImage(BufferedImage image, int index) {
        int smallWidth  = image.getWidth();
        int smallHeight = image.getHeight();
        int[][] smallPixels = new int[smallWidth][smallHeight];
        for(int x = 0; x < smallWidth; x++) {
            for(int y = 0; y < smallHeight; y++) {
                smallPixels[x][y] = image.getRGB(x, y);
            }
        }
        double maxRoughPixels = (double)(smallWidth * smallHeight) * roughness;
        int count = 0;
        for(int X = 0; X <= bigWidth - smallWidth; X++) {
            for(int Y = 0; Y <= bigHeight - smallHeight; Y++) {
                int roughPixels = 0;
                for(int x = 0; x < smallWidth; x++) {
                    for(int y = 0; y < smallHeight; y++) {
                        if(smallPixels[x][y] != bigPixels[X + x][Y + y]) {
                            roughPixels++;
                            if(roughPixels > maxRoughPixels) {
                                break;
                            }
                        }
                    }
                    if(roughPixels > maxRoughPixels) {
                        break;
                    }
                }
                if(roughPixels <= maxRoughPixels) {
                    if(count == index) {
                        return(new Point(X, Y));
                    }
                    count++;
                }
            }
        }
        return(null);
    }
}
