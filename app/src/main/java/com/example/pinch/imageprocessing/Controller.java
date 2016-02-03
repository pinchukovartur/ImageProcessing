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
import android.widget.ImageView;

import java.io.IOException;

public class Controller extends AppCompatActivity implements View.OnClickListener {

    Bitmap image;

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


    }

    @Override
    public void onClick(View v) {
        int[][] matrixFilters = new int[][]{{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}};
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        switch (v.getId()) {
            case R.id.newImageButton:
                Intent photoPicker = new Intent(Intent.ACTION_GET_CONTENT);
                photoPicker.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(photoPicker, 0);
                break;
            case R.id.filtersButton:
                Filters filters = new Filters(matrixFilters, 7, 40);
                imageView.setImageBitmap(filters.processingBitmap(image));
                System.out.println("good work!");
                break;
            case R.id.anaglifButton:
                Anaglif anaglif = new Anaglif(image);
                imageView.setImageBitmap(anaglif.getAnaglif());
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
}