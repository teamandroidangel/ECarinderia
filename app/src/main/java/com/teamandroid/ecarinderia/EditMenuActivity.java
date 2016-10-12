package com.teamandroid.ecarinderia;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.database.DatabaseUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditMenuActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private int PICK_IMAGE_REQUEST = 3;

    EditText nameText;
    EditText priceText;
    EditText quantityText;
    ImageView menuImage;
    long itemId;
    int itemIndex;
    MenuItem item;
    int code;

    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        nameText = (EditText) findViewById(R.id.nameEditText);
        priceText = (EditText) findViewById(R.id.priceEditText);
        quantityText = (EditText) findViewById(R.id.quantityEditText);
        menuImage = (ImageView) findViewById(R.id.picView);


        item = (MenuItem) getIntent().getSerializableExtra("item");
        itemId = getIntent().getLongExtra("itemId",0);
        itemIndex = getIntent().getIntExtra("itemIndex",0);
        code = getIntent().getIntExtra("code", 0);

        nameText.setText(item.getName());
        priceText.setText(Double.toString(item.getPrice()));
        quantityText.setText(Integer.toString(item.getQuantity()));

        Picasso.with(this).load(item.getImageUrl()).into(menuImage);


    }

    public void onGallery(View view) {

        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


    }

    public void onCamera(View view) {

        dispatchTakePictureIntent();

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.teamandroid.ecarinderia.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();

            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //menuImage.setImageBitmap(imageBitmap);

            galleryAddPic();

            Picasso.with(this).load(mCurrentPhotoPath).into(menuImage);


        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);


                ImageView imageView = (ImageView) findViewById(R.id.picView);
                imageView.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }



            //Picasso.with(this).load(mCurrentPhotoPath).into(menuImage);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public void onSaveEditItem(View view) {

        if (item == null){
            item = new MenuItem();
        }

        item.setName(nameText.getText().toString());
        item.setPrice(Double.parseDouble(priceText.getText().toString()));
        item.setQuantity(Integer.parseInt(quantityText.getText().toString()));

        Intent data = new Intent();

        data.putExtra("item", item);
        data.putExtra("itemIndex", itemIndex);
        data.putExtra("itemId", itemId);
        data.putExtra("code", 200);
        setResult(RESULT_OK, data);
        finish();
    }

    public void onCancelEditItem(View view) {
        finish();
    }
}
