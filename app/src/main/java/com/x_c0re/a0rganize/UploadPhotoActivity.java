package com.x_c0re.a0rganize;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.theartofdev.edmodo.cropper.CropImageView;

public class UploadPhotoActivity extends AppCompatActivity
{
    public static int value;
    private CropImageView mCropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Crop avatar");

        if (value == 228) // после перехода от CropOrSkipActivity (вход в галерею)
        {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        }

        mCropImageView = findViewById(R.id.cropImageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)       // после возвращения от галереи
        {
            if (data.getData() != null)
            {
                final Uri imageURI = data.getData();
                mCropImageView.setImageUriAsync(imageURI);
            }
            else
            {
                this.finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.top_right_start_mission_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case (R.id.start_mission_button):
                // обрезка фото и его сохранение, переход в CropOrSkipActivity
                CropOrSkipActivity.cropped_image = mCropImageView.getCroppedImage();
                CropOrSkipActivity.access_code = 123;

                Intent intent = new Intent(this, CropOrSkipActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                return true;
            case (android.R.id.home):
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
