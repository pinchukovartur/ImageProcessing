package com.example.pinch.imageprocessing;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Scaling {

    private Bitmap bitmap;
    private int size;

    public Scaling(Bitmap bitmap, int size) {
        this.bitmap = bitmap;
        this.size = size;
    }

    private Bitmap scalingPlus() {

        Bitmap newImage = Bitmap.createBitmap(bitmap.getWidth() - size * 2,
                bitmap.getHeight() - size * 2, bitmap.getConfig());

        for (int i = size; i < bitmap.getWidth() - size; i++) {
            for (int j = size; j < bitmap.getHeight() - size; j++) {
                newImage.setPixel(i - size, j - size, bitmap.getPixel(i, j));
            }
        }
        return newImage;
    }

    public ArrayList<Bitmap> getScalingList (){

        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        while (bitmap.getHeight() > size*2 && bitmap.getWidth() > size*2){
            bitmaps.add(bitmap);
            bitmap = scalingPlus();
        }

        return bitmaps;
    }
}
