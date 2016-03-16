package com.example.pinch.imageprocessing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Random;

public class Controller extends AppCompatActivity implements View.OnClickListener {

    Bitmap image;
    Bitmap saveBitmap;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_window);

        Button newImageButton = (Button) findViewById(R.id.newImageButton);
        newImageButton.setOnClickListener(this);

        Button filtersButton = (Button) findViewById(R.id.filtersButton);
        filtersButton.setOnClickListener(this);

        Button anaglifButton = (Button) findViewById(R.id.anaglifButton);
        anaglifButton.setOnClickListener(this);

        Button resetButton = (Button) findViewById(R.id.resetbutton);
        resetButton.setOnClickListener(this);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        Button segmentsButton = (Button) findViewById(R.id.segButton);
        segmentsButton.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.editText);
    }

    @Override
    public void onClick(View v) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        switch (v.getId()) {
            case R.id.newImageButton:
                Intent photoPicker = new Intent(Intent.ACTION_GET_CONTENT);
                photoPicker.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(photoPicker, 0);
                break;
            case R.id.filtersButton:
                if(image!=null) {
                    double[][] matrixFilters = new double[][]{{-0.99, 0, 0.99}, {0, 0, 0}, {0.99, 0, -0.99}};
                    Filters filters = new Filters(matrixFilters, 3, 1);
                    saveBitmap = filters.processingBitmap(image);
                    imageView.setImageBitmap(saveBitmap);
                }
                break;
            case R.id.anaglifButton:
                if(image!=null) {
                    Anaglif anaglif = new Anaglif();
                    saveBitmap = anaglif.getAnaglif(image);
                    imageView.setImageBitmap(saveBitmap);
                }
                break;
            case R.id.resetbutton:
                imageView.setImageBitmap(image);
                break;
            case R.id.saveButton:
                if (saveBitmap!=null) {
                    System.out.println("Сохраняем...");
                    SaveImage saveImage = new SaveImage();
                    saveImage.writeFileSD();
                    System.out.println("save");
                }
                break;
            case R.id.segButton:

                Segmentation segmentation = new Segmentation(image,Integer.valueOf(String.valueOf(editText.getText())));
                saveBitmap = segmentation.getSegmentation();
                imageView.setImageBitmap(saveBitmap);
                System.out.println("OK");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        image = null;
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        image = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(image);
                }
        }
    }

    public double [][] genFilterMatrix (){
        double newFilter [][] = new double[3][3];
        for (int i = 0; i <3; i++) {
            for (int j = 0; j <3; j++) {
                newFilter[i][j]  =  Math.random()*6-3;
            }
        }
        return newFilter;
    }
}