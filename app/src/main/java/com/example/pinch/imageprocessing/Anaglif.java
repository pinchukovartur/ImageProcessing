package com.example.pinch.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Anaglif {

    public Bitmap getAnaglif (Bitmap bitmap){

        int pixelColorGreenBlue;
        int pixelColorRed;
        int newPixel;

        Bitmap newImage = Bitmap.createBitmap(bitmap.getWidth()/2,
                bitmap.getHeight(), bitmap.getConfig());

        int nRed ,nGreen ,nBlue ;

        for(int i = 0; i < bitmap.getWidth()/2; i++) {
                for (int j = 0; j < bitmap.getHeight(); j++) {
                    pixelColorGreenBlue = bitmap.getPixel(i+bitmap.getWidth()/2, j);
                    pixelColorRed = bitmap.getPixel(i, j);
                    nRed = Color.red(pixelColorRed);
                    nGreen = Color.green(pixelColorGreenBlue);
                    nBlue = Color.blue(pixelColorGreenBlue);
                    newPixel = Color.rgb(nRed, nGreen, nBlue);
                    newImage.setPixel(i, j, newPixel);
                }
        }
        return newImage;
    }
}
