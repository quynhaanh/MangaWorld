package com.example.mangaworld.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.NovelModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class ChartActivity extends AppCompatActivity {
    Button btnChoose, btnUpload;
    ImageView imageView;
    EditText edName;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        setControl();
        setEvent();
    }

    private void setEvent() {
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();

                NovelController controller = new NovelController(LoadActivity.url,ChartActivity.this);
                controller.getNovelByIDUser(MainActivity.loggedUser.getId(), new IVolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d("test", controller.convertJSONData(result).get(0).getDescription());
                    }
                });
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }

    private void setControl() {
        btnChoose = findViewById(R.id.btnChooseImage);
        btnUpload = findViewById(R.id.btnUploadImage);
        imageView = findViewById(R.id.ivUploadImage);
        edName = findViewById(R.id.edImageName);
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    private void uploadImage() {
        NovelModel model = new NovelModel();
        model.setId(1);
        model.setTitle("Test");
        model.setIdAuthor(1);
        model.setCover(edName.getText().toString());
        model.setDescription("Test Upload Cover");
//        model.setDatePost(getDateString()); Không cần thiết
        model.setIdUser(MainActivity.loggedUser.getId());
        model.setCoverImageData(imageToString(bitmap));

        NovelController controller = new NovelController(LoadActivity.url, this);
        controller.insertNovel(model, new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
            }
        });
    }

    private String getDateString() {
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteOut);
        byte[] imgBytes = byteOut.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
                edName.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}