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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class CropOrSkipActivity extends AppCompatActivity
{
    FloatingActionButton fab;

    private int GALLERY = 1;

    ImageView avatar;

    CreateAccount ca;
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
                                        Intent intent = new Intent(Intent.ACTION_PICK);
                                        intent.setType("image/*");
                                        startActivityForResult(intent, GALLERY);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 1:
                try
                {
                    final Uri imageURI = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageURI);

                    /* Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                    avatar.setImageBitmap(bitmap); */

                    UploadPhotoActivity.loaded_image = BitmapFactory.decodeStream(imageStream);
                    Intent intent = new Intent(this, UploadPhotoActivity.class);
                    startActivity(intent);

                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                break;
        }
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
                    cd = new ConnectionDetector(this);
                    ca = new CreateAccount();
                    ca.execute(VerificationCodeActivity.entered_name,
                            VerificationCodeActivity.entered_surname,
                            VerificationCodeActivity.entered_login,
                            VerificationCodeActivity.entered_password,
                            VerificationCodeActivity.entered_phone);

                    UploadPhotoActivity.login_registration = VerificationCodeActivity.entered_login;

                    CheckActivity.activity = "fromAuthActivitytoMainActivity";
                    CheckActivity.loginS = UploadPhotoActivity.login_registration;

                    MainActivity.check_for_login = "moved";
                    MainActivity.login_bridge = UploadPhotoActivity.login_registration;

                    Intent intent = new Intent(CropOrSkipActivity.this, CheckActivity.class);
                    startActivity(intent);

                    Toast toast = Toast.makeText(CropOrSkipActivity.this,
                            "Welcome, " + UploadPhotoActivity.login_registration + "!", Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Internet connection required", Toast.LENGTH_LONG);
                    toast.show();
                }

                return true;
            case (android.R.id.home):
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static class CreateAccount extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... strings)
        {
            HttpRequest request = HttpRequest.post("http://95.85.19.194/contacts");
            request.part("name", strings[0]);
            request.part("surname", strings[1]);
            request.part("login", strings[2]);
            request.part("password", strings[3]);
            request.part("phone", strings[4]);
            // тут должна быть строчка про аватар - если пользователь выбрал изображение, то оно будет загружено
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
