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
 * Performs "strict" (i.e. based on all pixels matching) image search.
 *
 * @author Alexandre Iline (alexandre.iline@sun.com)
 */
public class StrictImageFinder implements ImageFinder {
    int bigWidth, bigHeight;
    int[][] bigPixels;

    /**
     * Creates an instance searching subimages insige a parameter image.
     * @param area - Image to search in.
     */
    public StrictImageFinder(BufferedImage area) {
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
     * Searchs for an image inside image passed into constructor.
     * @param image an image to search.
     * @param index an ordinal image location index. If equal to 1, for example,
     * second appropriate location will be found.
     * @return Left-up corner coordinates of image location.
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
        boolean good;
        int count = 0;
        for(int X = 0; X <= bigWidth - smallWidth; X++) {
            for(int Y = 0; Y <= bigHeight - smallHeight; Y++) {
                good = true;
                for(int x = 0; x < smallWidth; x++) {
                    for(int y = 0; y < smallHeight; y++) {
                        if(smallPixels[x][y] != bigPixels[X + x][Y + y]) {
                            good = false;
                            break;
                        }
                    }
                    if(!good) {
                        break;
                    }
                }
                if(good) {
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
