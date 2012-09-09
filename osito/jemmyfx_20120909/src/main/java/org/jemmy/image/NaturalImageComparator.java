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
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com),
 *                 Alexander (KAM) Kouznetsov <mrkam@mail.ru>.
 *
 * The Original Software is the Jemmy library.
 * The Initial Developer of the Original Software is Alexandre Iline.
 * All Rights Reserved.
 *
 */

package org.jemmy.image;


import java.awt.Color;
import java.awt.image.BufferedImage;
import org.jemmy.image.pixel.AverageDistanceComparator;
import org.jemmy.image.pixel.MaxDistanceComparator;


/**
 * Compares two images naturally
 * (i.e. ignoring slight difference between pixel colors).
 *
 * @author KAM
 */
public class NaturalImageComparator extends BufferedImageComparator {

    /**
     * Creates comparator with the default sensitivity value = 0.02 
     * (around 5 in 0-255 color component value).
     * @see #NaturalImageComparator(double)
     */
    public NaturalImageComparator() {
        this(0.02);
    }

    /**
     * Creates comparator with the specified sensitivity value
     * @param sensitivity Maximum threshold for 3-D distance between colors
     * in 3-D sRGB color space for pixels to be considered equal.
     * Meaningful values are between 0 and approx 1.733. 0 means colors should
     * be equal to pass the comparison, 1.733 (which is more than square root
     * of 3) means that comparison will be passed even if the colors are
     * completely different. You could also use {@linkplain
     * #findSensitivity(java.awt.image.BufferedImage, java.awt.image.BufferedImage)
     * findSensitivity()} method to obtain necessary sensitivity value.
     */
    public NaturalImageComparator(double sensitivity) {
        super(new MaxDistanceComparator(sensitivity));
    }
    public void setSensitivity(double sensitivity) {
        ((MaxDistanceComparator)getRasterComparator()).setThreshold(sensitivity);
    }
    public double getSensitivity() {
        return ((MaxDistanceComparator)getRasterComparator()).getThreshold();
    }
}
