package com.example.pinch.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;

public class Segmentation {

    ArrayList<Integer> segments;
    Bitmap bitmap;
    int threshold;

    Segmentation(Bitmap bitmap, int threshold) {
        this.bitmap = bitmap;
        this.threshold = threshold;
    }

    public Bitmap getSegmentation() {

        Bitmap newImage = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), bitmap.getConfig());
        segments = new ArrayList<>();
        segments.add(bitmap.getPixel(0, 0));

        for (int i = 1; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                newImage.setPixel(i, j, getPixel(i, j));
            }
        }
        return newImage;
    }

    public int getPixel(int x, int y) {

        int nRed, nGreen, nBlue;
        int pixelColor;
        pixelColor = bitmap.getPixel(x, y);

        for (Integer seg : segments) {
            nRed = Math.abs(Color.red(pixelColor) - Color.red(seg.intValue()));
            nGreen = Math.abs(Color.green(pixelColor) - Color.green(seg.intValue()));
            nBlue = Math.abs(Color.blue(pixelColor) - Color.blue(seg.intValue()));
            if (nRed < threshold && nGreen < threshold && nBlue < threshold) return seg;
        }
        segments.add(bitmap.getPixel(x, y));
        return bitmap.getPixel(x, y);
    }
}
