package com.example.pinch.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.Color;


public class Filters {

    private double[][] matrixFilters;
    private int size;
    private int div;
    private int width;
    private int height;

    Filters(double[][] matrixFilters, int size, int div) {
        this.matrixFilters = matrixFilters;
        this.size = size;
        this.div = div;
    }

    public Bitmap processingBitmap(Bitmap image) {

        width = image.getWidth();
        height = image.getHeight();
        Bitmap newImage = Bitmap.createBitmap(width, height, image.getConfig());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                newImage.setPixel(x, y, getNewPixel(x, y, image));
            }
        }
        return newImage;
    }

    public int getNewPixel(int x, int y, Bitmap image) {
        int newPixel;
        int nBlue = 0, nGreen = 0, nRed = 0;
        int pixelColor;

        int interval = size / 2;
        int row = 0;
        for (int i = x - interval; i <= x + interval; i++) {
            int column = 0;
            for (int j = y - interval; j <= y + interval; j++) {
                if (i >= 0 && i < width && j >= 0 && j < height) {
                    pixelColor = image.getPixel(i, j);
                    nRed += (Color.red(pixelColor)) * matrixFilters[row][column];
                    nGreen += (Color.green(pixelColor)) * matrixFilters[row][column];
                    nBlue += (Color.blue(pixelColor)) * matrixFilters[row][column];
                }
                column++;
            }
            row++;
        }
        nRed /= div;
        nGreen /= div;
        nBlue /= div;
        nRed = checkColor(nRed);
        nGreen = checkColor(nGreen);
        nBlue = checkColor(nBlue);

        newPixel = Color.rgb(nRed, nGreen, nBlue);
        return newPixel;
    }

    public int checkColor(int x) {

        if (x < 0) {
            return 0;
        } else if (x > 255) {
            return 255;
        } else return x;
    }

}
