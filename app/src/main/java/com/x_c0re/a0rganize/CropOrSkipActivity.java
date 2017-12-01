package com.x_c0re.a0rganize;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class CropOrSkipActivity extends AppCompatActivity
{
    public static String login_registration;

    public static Bitmap cropped_image;
    public static Uri cropped_image_uri;
    public static int access_code;

    FloatingActionButton fab;

    ImageView avatar;

    CreateAccount ca;
    CreateAccountWithAvatar cawa;
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_or_skip);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Upload avatar");

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String[] choosePhotoUploadingWay = { "Choose from gallery", "Take a photo" };

                AlertDialog.Builder builder = new AlertDialog.Builder(CropOrSkipActivity.this);
                builder.setTitle("Avatar")
                        .setItems(choosePhotoUploadingWay, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which)
                            {
                                switch (which)
                                {
                                    case 0:
                                        UploadPhotoActivity.value = 228;
                                        Intent intent = new Intent(CropOrSkipActivity.this, UploadPhotoActivity.class);
                                        startActivity(intent);
                                        break;
                                    case 1:

                                        break;
                                }
                            }
                        });
                builder.show();
            }
        });

        avatar = findViewById(R.id.imageView);

        if (access_code == 123)
        {
            avatar.setImageBitmap(cropped_image);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.top_right_start_mission_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case (R.id.start_mission_button):
                cd = new ConnectionDetector(this);
                if (cd.isConnected())
                {

                    // если cropped_image_uri - null, execute() вызываем без него
                    if (cropped_image_uri != null)
                    {
                        cawa = new CreateAccountWithAvatar();
                        cawa.execute(VerificationCodeActivity.entered_name,
                                VerificationCodeActivity.entered_surname,
                                VerificationCodeActivity.entered_login,
                                VerificationCodeActivity.entered_password,
                                VerificationCodeActivity.entered_phone,
                                cropped_image_uri.toString());
                    }
                    else
                    {
                        ca = new CreateAccount();
                        ca.execute(VerificationCodeActivity.entered_name,
                                VerificationCodeActivity.entered_surname,
                                VerificationCodeActivity.entered_login,
                                VerificationCodeActivity.entered_password,
                                VerificationCodeActivity.entered_phone);
                    }

                    login_registration = VerificationCodeActivity.entered_login;

                    CheckActivity.activity = "fromAuthActivitytoMainActivity";
                    CheckActivity.loginS = login_registration;

                    MainActivity.check_for_login = "moved";
                    MainActivity.login_bridge = login_registration;

                    Intent intent = new Intent(CropOrSkipActivity.this, CheckActivity.class);
                    startActivity(intent);

                    Toast toast = Toast.makeText(CropOrSkipActivity.this,
                            "Welcome, " + login_registration + "!", Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Internet connection required", Toast.LENGTH_LONG);
                    toast.show();
                }

                return true;
            case (android.R.id.home):
                VerificationCodeActivity.codeVerifying = null;
                Intent intent = new Intent(this, CheckPhoneNumberActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static class CreateAccount extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... strings)
        {
            HttpRequest request = HttpRequest.post("http://95.85.19.194/contacts/");
            request.part("name", strings[0]);
            request.part("surname", strings[1]);
            request.part("login", strings[2]);
            request.part("password", strings[3]);
            request.part("phone", strings[4]);
            request.part("rating", 1);
            request.part("virginity", "true");

            int status = request.code();
            if (status == 200)
            {
                System.out.println(request.body());
            }

            return null;
        }
    }

    static class CreateAccountWithAvatar extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... strings)
        {
            HttpRequest request = HttpRequest.post("http://95.85.19.194/contacts/");
            request.part("name", strings[0]);
            request.part("surname", strings[1]);
            request.part("login", strings[2]);
            request.part("password", strings[3]);
            request.part("phone", strings[4]);
            request.part("avatar", strings[5]);
            request.part("rating", 1);
            request.part("virginity", "true");

            int status = request.code();
            if (status == 200)
            {
                System.out.println(request.body());
            }

            return null;
        }
    }

}
