package com.example.pinch.imageprocessing;

import android.graphics.Bitmap;

public class Anaglif {

    Bitmap image;

    Anaglif(Bitmap bitmap){
        this.image = bitmap;
    }

    public Bitmap getAnaglif (){

        Bitmap newImage = Bitmap.createBitmap(image.getWidth(),
                image.getHeight(), image.getConfig());

        return image; // поменять потом на newImage
    }
}
