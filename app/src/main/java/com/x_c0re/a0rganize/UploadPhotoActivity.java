package com.x_c0re.a0rganize;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.steelkiwi.cropiwa.CropIwaView;
import com.steelkiwi.cropiwa.config.CropIwaSaveConfig;

public class UploadPhotoActivity extends AppCompatActivity
{
    CropIwaView cropIwaView;

    public static String login_registration;

    public static Bitmap loaded_image;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Crop avatar");

        cropIwaView = findViewById(R.id.cropView);
        cropIwaView.setImage(loaded_image);
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


                return true;
            case (android.R.id.home):
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view)
    {

    }
}
