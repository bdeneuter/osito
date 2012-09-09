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
package org.jemmy.lookup;

import org.jemmy.Rectangle;
import org.jemmy.control.Wrap;
import org.jemmy.control.Wrapper;
import org.jemmy.image.Image;

/**
 *
 * @author shura
 */
public class ImageLookup<T> implements LookupCriteria<T> {
    private Wrapper wrapper;
    private Image image;
    private Class<T> type;
    private Rectangle subArea;
    private Image lastDiff = null;
    private Image lastImage = null;

    public ImageLookup(Wrapper wrapper, Class<T> type, Image image, Rectangle subArea) {
        this.wrapper = wrapper;
        this.type = type;
        this.image = image;
        this.subArea = subArea;
    }
    
    public ImageLookup(Wrapper wrapper, Class<T> type, Image image) {
        this(wrapper, type, image, null);
    }

    public boolean check(T control) {
        Wrap<?> wrap = wrapper.wrap(type, control);
        if(subArea == null) {
            lastImage = wrap.getScreenImage();
        } else {
            lastImage = wrap.getScreenImage(subArea);
        }
        return (lastDiff = image.compareTo(lastImage)) == null;
    }

    public Image getLastDiff() {
        return lastDiff;
    }

    public Image getLastImage() {
        return lastImage;
    }
}
