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

/**
 * Interface for all classes performing image comparison. 
 *
 * @author Alexandre Iline (alexandre.iline@sun.com)
 */
public interface ImageComparator {

    /**
     * Checks if images are the same according to whatever comparison logic is used.
     * @param image1 an image to compare.
     * @param image2 an image to compare.
     * @return null if images match each other; difference otherwise
     */
    public Image compare(Image image1, Image image2);
    
    /**
     * A string qualifying an image comparison algorithm. To be used in logs and
     * tools.
     * @return 
     */
    public String getID();
}
