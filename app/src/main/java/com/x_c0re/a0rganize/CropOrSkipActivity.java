package com.x_c0re.a0rganize;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;

public class CropOrSkipActivity extends AppCompatActivity
{
    public static String login_registration;

    public static Bitmap cropped_image;
    public static int access_code;

    FloatingActionButton fab;

    ImageView avatar;
    ConnectionDetector cd;

    MultipartPostSender sender;

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
            // обрезанное фото сохранить в память и отослать на сервер multipart'ом
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                try
                {
                    File f = new File(getCacheDir(), "/overcome/temp/cropped_image_temp_file.jpg");
                    f.createNewFile();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    cropped_image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] bitmap_data = baos.toByteArray();

                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmap_data);
                    fos.flush();
                    fos.close();

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
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
                    if (cropped_image != null)
                    {
                        sender = new MultipartPostSender();
                        sender.execute(VerificationCodeActivity.entered_name,
                                VerificationCodeActivity.entered_surname,
                                VerificationCodeActivity.entered_login,
                                VerificationCodeActivity.entered_password,
                                VerificationCodeActivity.entered_phone);
                    }
                    else
                    {
                        RequestParams params = new RequestParams();
                        params.put("name", VerificationCodeActivity.entered_name);
                        params.put("surname", VerificationCodeActivity.entered_surname);
                        params.put("login", VerificationCodeActivity.entered_login);
                        params.put("password", VerificationCodeActivity.entered_password);
                        params.put("phone", VerificationCodeActivity.entered_phone);
                        params.put("rating", 1);
                        params.put("virginity", true);

                        AsyncHttpClient client = new AsyncHttpClient();
                        client.post("http://overcome-api.herokuapp.com/contacts/", params, new AsyncHttpResponseHandler()
                        {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
                            {
                                Log.w("async", "Success!");
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
                            {
                                Log.w("async", "Failure!");
                            }
                        });
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

    public class MultipartPostSender extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... strings)
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            cropped_image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bitmap_data = baos.toByteArray();

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("name", strings[0]);
            builder.addTextBody("surname", strings[1]);
            builder.addTextBody("login", strings[2]);
            builder.addTextBody("password", strings[3]);
            builder.addTextBody("phone", strings[4]);
            builder.addTextBody("rating", "1");
            builder.addTextBody("virginity", "true");

            ContentType contentType = ContentType.create("image/jpeg");
            String fileName = "file.jpeg";
            builder.addBinaryBody("avatar", bitmap_data, contentType, fileName);

            HttpEntity entity = builder.build();

            HttpPost post = new HttpPost("http://overcome-api.herokuapp.com/contacts/");
            post.setEntity(entity);

            HttpClient client = cz.msebera.android.httpclient.impl.client.HttpClients.createDefault();
            try
            {
                client.execute(post);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }
}
