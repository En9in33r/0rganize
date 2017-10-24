package com.x_c0re.a0rganize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.steelkiwi.cropiwa.CropIwaView;
import com.steelkiwi.cropiwa.config.CropIwaSaveConfig;

public class UploadPhotoActivity extends AppCompatActivity
{
    CropIwaView cropIwaView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        cropIwaView = (CropIwaView)findViewById(R.id.cropView);
        // cropIwaView.setImage();
    }


}
