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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.jemmy.Dimension;
import org.jemmy.env.Environment;
import org.jemmy.env.TestOut;
import org.jemmy.image.pixel.Raster;
import org.jemmy.image.pixel.RasterComparator;
import org.jemmy.image.pixel.ResizeComparator;

/**
 * Comparator that makes image sizes equal to compare them with other comparator
 * most of them work only for the images with equal dimensions.
 *
 * It is controlled by three parameters: <li>Resize Mode - defines way of
 * resizing images that are being compared. One of the following constants: <ul> <li>{@linkplain ResizeMode#NO_RESIZE NO_RESIZE},</li> <li>{@linkplain ResizeMode#PROPORTIONAL_RESIZE PROPORTIONAL_RESIZE},</li> <li>{@linkplain ResizeMode#ARBITRARY_RESIZE ARBITRARY_RESIZE}.</li></ul>
 * Default value is {@linkplain ResizeMode#PROPORTIONAL_RESIZE}.</li> <li>Resize
 * Hint - defines the way of images scaling that is specified when {@linkplain java.awt.Image#getScaledInstance(int, int, int)
 * Image.getScaledInstance()} method is invoked. One of the Image.SCALE_XXX is
 * expected. Default value is
 * {@linkplain java.awt.Image#SCALE_DEFAULT SCALE_DEFAULT}.</li> <li>Proportion
 * Distortion Threshold - defines maximum proportion distortion that is used in {@linkplain ResizeMode#PROPORTIONAL_RESIZE
 * PROPORTIONAL_RESIZE} mode to deal with cases when rounding and other problems
 * could cause sizes to be not ideally proportional.<p/>
 * Threshold value is applied in the following way: {@code Math.abs(image.getSize() * scale
 * - size) / image.getSize() > PROPORTION_DISTORTION_THRESHOLD}, where {@code image.getSize()}
 * is both image dimensions and {@code size} is target width and height (which
 * is defined as mininum of sizes of two images) and scale is the scale factor
 * that scales the particular image to fit the width and height.
 * <p/>
 * Default value is 0.02 so as much as 2% of width/height could differ (around 5
 * in 0-255 color component values).</li>
 *
 * @author mrkam
 */
public class ResizeImageComparator extends ResizeComparator {

    /**
     * Indentifies output where resize details are printed. Output is disabled
     * by default.
     *
     * @see Environment#getOutput(java.lang.String)
     */
    public static final String OUTPUT = ResizeImageComparator.class.getName()
            + ".OUTPUT";

    static {
        Environment.getEnvironment().setOutput(OUTPUT, TestOut.getNullOutput());
    }
    ResizeMode resizeMode;
    int hint;
    double propDistThreshold;

    /**
     * Resize Modes
     *
     * @see
     * #ResizeImageComparator(org.jemmy.image.ResizeImageComparator.ResizeMode,
     * org.jemmy.image.ImageComparator)
     * @see
     * #ResizeImageComparator(org.jemmy.image.ResizeImageComparator.ResizeMode,
     * int, double, org.jemmy.image.ImageComparator)
     */
    public static enum ResizeMode {

        /**
         * Images are never resized. Original images are always compared.
         */
        NO_RESIZE,
        /**
         * Images are resized only if they have exactly or almost proportional
         * sizes which is controlled by {@code proportionDistortion} parameter.
         * If images have different proportions no resize is done and original
         * images are compared.
         *
         * @see
         * #ResizeImageComparator(org.jemmy.image.ResizeImageComparator.ResizeMode,
         * int, double, org.jemmy.image.ImageComparator)
         */
        PROPORTIONAL_RESIZE,
        /**
         * Images are always resized to match both width and height and then
         * compared.
         */
        ARBITRARY_RESIZE
    }

    /**
     * Creates ResizeImageComparator with default resize settings: <li>resize
     * mode: {@linkplain ResizeMode#PROPORTIONAL_RESIZE ResizeMode.PROPORTIONAL_RESIZE}.</li>
     * <li>proportion distortion threshold: 0.02.</li> <li>resize hint: {@linkplain java.awt.Image#SCALE_DEFAULT Image.SCALE_DEFAULT}.</li>
     *
     * @param subComparator comparator to compare images after resize.
     * @see java.awt.Image#getScaledInstance(int, int, int)
     * @see ResizeMode
     * @see ResizeImageComparator
     */
    public ResizeImageComparator(ImageComparator subComparator) {
        super(subComparator, ResizeComparator.Mode.LEFT);
        this.resizeMode = ResizeMode.PROPORTIONAL_RESIZE;
        this.hint = java.awt.Image.SCALE_DEFAULT;
        this.propDistThreshold = 0.02;
    }

    /**
     * Creates ResizeImageComparator with the specified resize mode and default
     * settings for other parameters: <li>proportion distortion threshold:
     * 0.02.</li> <li>resize hint: {@linkplain java.awt.Image#SCALE_DEFAULT Image.SCALE_DEFAULT}.</li>
     *
     * @param resizeMode resize mode for this comparator.
     * @param subComparator comparator to compare images after resize.
     * @see ResizeMode
     * @see ResizeImageComparator
     */
    public ResizeImageComparator(ResizeMode resizeMode,
            ImageComparator subComparator) {
        this(subComparator);
        this.resizeMode = resizeMode;
    }

    /**
     * Creates ResizeImageComparator with the following settings: <li>resize
     * mode: {@linkplain ResizeMode#PROPORTIONAL_RESIZE ResizeMode.PROPORTIONAL_RESIZE}.</li>
     * <li>specified proportion distortion threshold.</li> <li>resize hint: {@linkplain java.awt.Image#SCALE_DEFAULT Image.SCALE_DEFAULT}.</li>
     *
     * @param propDistThreshold proportion distortion threshold.
     * @param subComparator comparator to compare images after resize.
     * @see ResizeImageComparator
     */
    public ResizeImageComparator(double propDistThreshold,
            ImageComparator subComparator) {
        this(subComparator);
        this.propDistThreshold = propDistThreshold;
    }

    /**
     * Creates ResizeImageComparator with specified settings.
     *
     * @param resizeMode Resize mode.
     * @param propDistThreshold Proportion distortion threshold.
     * @param hint Resize hint.
     * @param subComparator comparator to compare images after resize.
     * @see ResizeImageComparator
     * @see ResizeMode
     */
    public ResizeImageComparator(ResizeMode resizeMode, double propDistThreshold,
            int hint, ImageComparator subComparator) {
        this(subComparator);
        this.resizeMode = resizeMode;
        this.hint = hint;
        this.propDistThreshold = propDistThreshold;
    }

    @Override
    public Image resize(Image image, Dimension size) {
        Dimension isize = getSize(image);
        double scalex = (double) size.width / isize.width;
        double scaley = (double) size.height / isize.height;
        switch (resizeMode) {
            case NO_RESIZE:
                return image;
            case PROPORTIONAL_RESIZE:
                if (Math.abs(scalex - scaley) > propDistThreshold) {
                    return null;
                }
            case ARBITRARY_RESIZE:
                BufferedImage res = new BufferedImage(size.width, size.height, ((AWTImage) image).getTheImage().getType());
                java.awt.Image scaled = ((AWTImage) image).getTheImage().getScaledInstance(
                        size.width, size.height, hint);
                Graphics2D g = res.createGraphics();
                g.drawImage(scaled, 0, 0, null);
                g.dispose();
                return new AWTImage(res);
            default:
                return null;
        }
    }
    @Override
    public String getID() {
        return ResizeImageComparator.class.getName() + "("
                + getSubComparator().getID() + ")";
    }

    @Override
    public Dimension getSize(Image image) {
        BufferedImage bi = ((AWTImage)image).getTheImage();
        return new Dimension(bi.getWidth(), bi.getHeight());
    }

}
