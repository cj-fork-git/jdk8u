/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/**
 * @bug 8130737
 * @test
 * @summary test no exception rasterop for child raster with non-zero offset
 */

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferUShort;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class TestChildRasterOp {

    private static AffineTransform at = new AffineTransform();
    private static final AffineTransformOp rop =
        new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    private static int[] offsets = {0};

    public static void main(String[] args) {
        testByteRaster();
        testShortRaster();
        testIntRaster();
    }

    private static void testByteRaster() {
        WritableRaster srcRaster, dstRaster;

        byte[] pixels =
            { 11, 12, 13, 14,
              21, 22, 23, 24,
              31, 32, 33, 34,
              41, 42, 43, 44 };

        DataBuffer db = new DataBufferByte(pixels, pixels.length);
        srcRaster =
            Raster.createInterleavedRaster(db, 4, 4, 4, 1, offsets, null);
        srcRaster = srcRaster.createWritableChild(1, 1, 3, 3, 0, 0, null);
        dstRaster = rop.filter(srcRaster, null);
    }

    private static void testShortRaster() {
        WritableRaster srcRaster, dstRaster;

        short[] pixels =
            { 11, 12, 13, 14,
              21, 22, 23, 24,
              31, 32, 33, 34,
              41, 42, 43, 44 };

        DataBuffer db = new DataBufferUShort(pixels, pixels.length);
        srcRaster =
            Raster.createInterleavedRaster(db, 4, 4, 4, 1, offsets, null);
        srcRaster = srcRaster.createWritableChild(1, 1, 3, 3, 0, 0, null);
        dstRaster = rop.filter(srcRaster, null);
    }

    private static void testIntRaster() {
        WritableRaster srcRaster, dstRaster;

        int[] pixels =
            { 11, 12, 13, 14,
              21, 22, 23, 24,
              31, 32, 33, 34,
              41, 42, 43, 44 };

        DataBuffer db = new DataBufferInt(pixels, pixels.length);
        srcRaster =
            Raster.createPackedRaster(db, 4, 4, 4,  offsets, null);
        srcRaster = srcRaster.createWritableChild(1, 1, 3, 3, 0, 0, null);
        dstRaster = rop.filter(srcRaster, null);
    }
}
