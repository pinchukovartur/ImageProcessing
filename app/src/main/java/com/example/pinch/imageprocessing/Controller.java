package com.example.pinch.imageprocessing;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

public class Controller extends AppCompatActivity implements View.OnClickListener {

    private Bitmap image;
    private EditText editText;
    private Bitmap saveBitmap;
    private int scalingFlag;
    private ArrayList<Bitmap> bitmaps;
    private ImageView imageView;
    private Scaling scaling;
    private int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_window);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(this);

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

        Button plusButton = (Button) findViewById(R.id.plusButton);
        plusButton.setOnClickListener(this);

        Button minisButton = (Button) findViewById(R.id.minusButton);
        minisButton.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.editText);

        bitmaps = new ArrayList<>();

        scalingFlag = 1;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.newImageButton:
                Intent photoPicker = new Intent(Intent.ACTION_GET_CONTENT);
                photoPicker.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(photoPicker, 0);
                bitmaps.clear();
                break;
            case R.id.filtersButton:
                if (image != null) {
                    Filters filters = new Filters(genFilterMatrix(), 3, 1);
                    saveBitmap = filters.processingBitmap(image);
                    imageView.setImageBitmap(saveBitmap);
                }
                break;
            case R.id.anaglifButton:
                if (image != null) {
                    Anaglif anaglif = new Anaglif();
                    saveBitmap = anaglif.getAnaglif(image);
                    imageView.setImageBitmap(saveBitmap);
                }
                break;
            case R.id.resetbutton:
                imageView.setImageBitmap(image);
                break;
            case R.id.saveButton:
                if (saveBitmap != null) {
                    SaveImage saveImage = new SaveImage();
                    saveImage.writeFileSD(saveBitmap);
                }
                break;
            case R.id.segButton:
                if (image != null && editText.length() != 0) {
                    Segmentation segmentation = new Segmentation(image, Integer.valueOf(String.valueOf(editText.getText())));
                    imageView.setImageBitmap(segmentation.getSegmentation());
                }
                break;
            case R.id.plusButton:
                if (image != null && bitmaps.size() == 0 && editText.length() != 0) {
                    scaling = new Scaling(image, Integer.valueOf(String.valueOf(editText.getText())));
                    bitmaps = scaling.getScalingList();
                }
                if (image != null && scalingFlag + 1 < bitmaps.size()) {
                    scalingFlag++;
                    imageView.setImageBitmap(bitmaps.get(scalingFlag));
                }
                break;
            case R.id.minusButton:
                if (image != null && scalingFlag > 0 && bitmaps.size() != 0) {
                    scalingFlag--;
                    imageView.setImageBitmap(bitmaps.get(scalingFlag));
                }
                break;
            case R.id.imageView:
                if (image != null && editText.length() != 0) {
                    scaling = new Scaling(image, Integer.valueOf(String.valueOf(editText.getText())));
                    saveBitmap = scaling.rotateBitmap(image, Integer.valueOf(String.valueOf(editText.getText()))*i);
                    imageView.setImageBitmap(saveBitmap);
                    i++;
                }
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

    public double[][] genFilterMatrix() {
        double newFilter[][] = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newFilter[i][j] = Math.random() * 6 - 3;
            }
        }
        return newFilter;
    }
}