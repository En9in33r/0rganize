package com.x_c0re.a0rganize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.steelkiwi.cropiwa.CropIwaView;
import com.steelkiwi.cropiwa.config.CropIwaSaveConfig;

public class UploadPhotoActivity extends AppCompatActivity
{
    CropIwaView cropIwaView;

    public static String login_registration;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        cropIwaView = (CropIwaView)findViewById(R.id.cropView);
        // cropIwaView.setImage();
    }

    public void onClick(View view)
    {
        CheckActivity.activity = "fromAuthActivitytoMainActivity";
        CheckActivity.loginS = login_registration;

        MainActivity.check_for_login = "moved";
        MainActivity.login_bridge = login_registration;

        Intent intent = new Intent(this, CheckActivity.class);
        startActivity(intent);

        Toast toast = Toast.makeText(this, "Welcome, " + login_registration + "!", Toast.LENGTH_LONG);
        toast.show();
    }
}
