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
import org.jemmy.image.pixel.Raster.Component;

/**
 *
 * @author shura
 */
public class PixelEqualityRasterComparator extends BaseCountingRasterComparator {

    /**
     *
     * @param treshold
     */
    public PixelEqualityRasterComparator(double treshold) {
        super(treshold);
    }

    /**
     *
     * @param comps1 
     * @param colors1 
     * @param comps2 
     * @param colors2 
     * @return
     */
    @Override
    protected boolean compare(Raster.Component[] comps1, double[] colors1,
            Raster.Component[] comps2, double[] colors2) {
        if (colors1.length == colors2.length) {
            for (int i = 0; i < colors1.length; i++) {
                if (colors1[i] != colors2[PixelImageComparator.arrayIndexOf(comps2, comps1[i])]) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return
     */
    public String getID() {
        return PixelEqualityRasterComparator.class.getName() + ":" + getThreshold();
    }
}
