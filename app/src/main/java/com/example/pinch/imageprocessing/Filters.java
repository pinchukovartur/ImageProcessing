package com.example.pinch.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Filters {

    private int[][] matrixFilters;
    private int size;
    private int div;

    Filters (int[][] matrixFilters,int size,int div){
        this.matrixFilters = matrixFilters;
        this.size = size;
        this.div = div;
    }

    public Bitmap processingBitmap(Bitmap image){

        Bitmap newImage = Bitmap.createBitmap(image.getWidth(),
                image.getHeight(), image.getConfig());

        for(int x = 0; x < image.getWidth(); x++){
            for(int y = 0; y < image.getHeight(); y++){
                newImage.setPixel(x, y, getNewPixel(x, y, image));
            }
        }
        return newImage;
    }

    public int getNewPixel (int x, int y, Bitmap image){
        int newPixel = 0;
        int nBlue = 0, nGreen = 0, nRed = 0;
        int pixelColor;

        int row = 0;
        int i = x - size/2;
        while (i < x + size/2){

            int j = y - size/2;
            int column = 0;
            while (j<y + size/2) {
                if(checkBorder(i, j, image.getWidth(), image.getHeight())){
                    pixelColor = image.getPixel(i, j);
                    nRed += (Color.red(pixelColor)*matrixFilters[row][column]);
                    nGreen += (Color.green(pixelColor)*matrixFilters[row][column]);
                    nBlue += (Color.blue(pixelColor)*matrixFilters[row][column]);
                }
                j++;
                column++;
            }
            i++;
            row++;
        }
        nRed /= div;
        nGreen /= div;
        nBlue /= div;
        System.out.println(nRed + " " + nGreen + " " + nBlue);

        nRed = checkColor(nRed);
        nGreen = checkColor(nGreen);
        nBlue = checkColor(nBlue);

        newPixel=  Color.rgb(nRed, nGreen, nBlue);
        return newPixel;
    }

    private boolean checkBorder(int x, int y, int width, int height){
        return (x>=0 && x<width && y>=0 && y<height);
    }

    public int checkColor(int x) {

        if (x<0) {
            return 0;
        } else if (x > 255) {
            return 255;
        } else return x;
    }
}
