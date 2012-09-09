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


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;


/**
 * Contains util methods to work with images.
 *
 * @author Alexandre Iline (alexandre.iline@sun.com)
 */
class ImageTool {

    /**
     * Increases image.
     * @param image an image to enlarge.
     * @param zoom A scale.
     * @return a result image.
     */
    public static BufferedImage enlargeImage(BufferedImage image, int zoom) {
        int wight = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(wight * zoom,
                height * zoom,
                image.getType());
        int rgb;
        for (int x = 0; x < wight; x++) {
            for (int y = 0; y < height; y++) {
                rgb = image.getRGB(x, y);
                for (int i = 0; i < zoom; i++) {
                    for (int j = 0; j < zoom; j++) {
                        result.setRGB(x * zoom + i,
                                y * zoom + j,
                                rgb);
                    }
                }
            }
        }
        return (result);
    }

    /**
     * Subtracts second image from first one.
     * Could be used to save file difference for future analysis.
     * @param minuend an image to subtract from.
     * @param deduction an image to subtract.
     * @return a result image.
     */
    public static BufferedImage subtractImage(BufferedImage minuend, BufferedImage deduction) {
        return (subtractImage(minuend, deduction, 0, 0, null));
    }

    /**
     * Subtracts second image from first one.
     * Could be used to save file difference for future analysis.
     * @param minuend an image to subtract from.
     * @param deduction an image to subtract.
     * @param highlight - a color to highlight the difference. If null,
     * color difference is shown.
     * @return a result image.
     */
    public static BufferedImage subtractImage(BufferedImage minuend, BufferedImage deduction, Color highlight) {
        return (subtractImage(minuend, deduction, 0, 0, highlight));
    }

    /**
     * Subtracts subimage from image.
     * Could be used to save file difference for future analysis.
     * @param minuend an image to subtract from.
     * @param deduction an image to subtract.
     * @param relativeX - deduction-in-minuend X coordinate
     * @param relativeY - deduction-in-minuend Y coordinate
     * @return a result image.
     */
    public static BufferedImage subtractImage(BufferedImage minuend, BufferedImage deduction, int relativeX, int relativeY) {
        return subtractImage(minuend, deduction, relativeX, relativeY, null);
    }

    /**
     * Subtracts subimage from image.
     * Could be used to save file difference for future analysis.
     * @param minuend an image to subtract from.
     * @param deduction an image to subtract.
     * @param relativeX - deduction-in-minuend X coordinate
     * @param relativeY - deduction-in-minuend Y coordinate
     * @param highlight - a color to highlight the difference. If null,
     * color difference is shown.
     * @return a result image.
     */
    public static BufferedImage subtractImage(BufferedImage minuend, BufferedImage deduction, int relativeX, int relativeY, Color highlight) {
        int mWidth = minuend.getWidth();
        int mHeight = minuend.getHeight();
        int dWidth = deduction.getWidth();
        int dHeight = deduction.getHeight();

        int maxWidth = (mWidth > relativeX + dWidth) ? mWidth : (relativeX + dWidth);
        int maxHeight = (mHeight > relativeY + dHeight) ? mHeight : (relativeY + dHeight);

        BufferedImage result = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_RGB);
        int mColor, dColor;
        for (int x = 0; x < maxWidth; x++) {
            for (int y = 0; y < maxHeight; y++) {
                if (x >= mWidth ||
                        y >= mHeight) {
                    mColor = 0;
                } else {
                    mColor = minuend.getRGB(x, y);
                }
                if (x >= dWidth + relativeX ||
                        y >= dHeight + relativeY ||
                        x < relativeX ||
                        y < relativeY) {
                    dColor = 0;
                } else {
                    dColor = deduction.getRGB(x - relativeX, y - relativeY);
                }
                result.setRGB(x, y, (mColor != dColor) ? subtractColor((highlight != null) ? highlight.getRGB() : 0, mColor, dColor) : 0);
            }
        }
        return (result);
    }

    public static double distance(int rgb1, int rgb2) {
        float [] buffer1 = new float[3];
        float [] buffer2 = new float[3];
        Color c1 = new Color(rgb1);
        Color c2 = new Color(rgb2);
        c1.getRGBColorComponents(buffer1);
        c2.getRGBColorComponents(buffer2);
        double distSquare = 0;
        for (int i = 0; i < 3; i++) {
            distSquare += (buffer1[i] - buffer2[i]) * (buffer1[i] - buffer2[i]);
        }
        return Math.sqrt(distSquare);
    }

    private static int subtractColor(int highlight, int m, int d) {
        if (highlight == 0) {
            float scale = (float) (distance(m, d) / Math.sqrt(3));
            return new Color(scale, scale, scale).getRGB();
        } else {
            return highlight;
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            usage();
            System.exit(1);
        }
        compare(new File(args[0]).getAbsoluteFile(), new File(args[1]).getAbsoluteFile());
    }

    private static void compare(File golden, File results) throws IOException {
        if (golden.isDirectory()) {
            File rfl;
            for (File fl : golden.listFiles()) {
                compare(fl, new File(results.getAbsolutePath() + File.separator + fl.getName()));
            }
            for (File fl : results.listFiles()) {
                rfl = new File(golden.getAbsolutePath() + File.separator + fl.getName());
                if (!rfl.exists()) {
                    compare(rfl, fl);
                }
            }
        } else {
            DiffDialog dialog = new DiffDialog();
            dialog.setImages(golden.exists() ? ImageIO.read(golden) : null, results.exists() ? ImageIO.read(results) : null);
            dialog.setVisible(true);
            switch (dialog.status) {
                case -2:
                    System.exit(0);
                case -1:
                    copy(results, golden);
                    break;
                case 1:
                    golden.delete();
                    break;
            }
        }
    }

    private static void usage() {
        System.out.println("java -jar JemmyAWTInput.jar <golden image set> <test result image set>");
    }

    private static void copy(File results, File golden) throws FileNotFoundException, IOException {
        if (golden.exists()) {
            golden.delete();
        }
        FileInputStream from = new FileInputStream(results);
        FileOutputStream to = new FileOutputStream(golden);
        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = from.read(buffer)) != -1) {
            to.write(buffer, 0, bytesRead); // write
        }
    }
}
