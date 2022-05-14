package com.example.mangaworld.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.NovelItemAdapter;
import com.example.mangaworld.controller.AuthorController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.AuthorModel;
import com.example.mangaworld.model.NovelModel;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CRUDNovelActivity extends AppCompatActivity {

    EditText txtNovelID, txtNovelTitle, txtNovelDesc, txtNovelCoverName;
    Spinner spNovelAuthor;
    Button btnNovelCommit;
    ImageButton ibtnNovelImgPicker;
    SearchView searchNovel;

    //View decorView;
    ListView lvNovel;

    ArrayList<NovelModel> novelData = new ArrayList<>();
    NovelController novelController;
    AuthorController authorController;

    NovelItemAdapter novelItemAdapter;

    String url = LoadActivity.url;
    boolean updateFlag = false;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudnovel);
        setControl();
        setEvent();
        refreshListNovel();
    }

    private void setEvent() {
        lvNovel.setAdapter(novelItemAdapter);

        btnNovelCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeNovelData();
            }
        });

        ibtnNovelImgPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    private void setControl() {
        txtNovelID = findViewById(R.id.txtNovelID);
        txtNovelTitle = findViewById(R.id.txtNovelTitle);
        txtNovelDesc = findViewById(R.id.txtNovelDesc);
        txtNovelCoverName = findViewById(R.id.txtNovelCoverName);

        spNovelAuthor = findViewById(R.id.spNovelAuthor);
        authorController = new AuthorController(url, this);
        ArrayAdapter adapterNovelAuthor = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                getAuthorNameList());
        spNovelAuthor.setAdapter(adapterNovelAuthor);

        ibtnNovelImgPicker = findViewById(R.id.ibtnNovelImgPicker);

        btnNovelCommit = findViewById(R.id.btnNovelCommit);

        searchNovel = findViewById(R.id.searchNovel);

        lvNovel = findViewById(R.id.lvNovel);

        novelController = new NovelController(url, this);
        novelItemAdapter = new NovelItemAdapter(CRUDNovelActivity.this,
                R.layout.layout_item_novel, novelData, url);
        Log.d("ListNovel", novelData.size() + "");
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

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
//            Uri path = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
//                imageView.setImageBitmap(bitmap);
//                imageView.setVisibility(View.VISIBLE);
//                edName.setVisibility(View.VISIBLE);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private void writeNovelData()
    {
        String authorName = spNovelAuthor.getSelectedItem().toString();
        int authorID = getAuthorIDbyName(authorName);

        NovelModel novel = new NovelModel();
        novel.setId(Integer.parseInt(txtNovelID.getText().toString()));
        novel.setTitle(txtNovelTitle.getText().toString());
        novel.setIdAuthor(authorID);
        novel.setCover(txtNovelCoverName.getText().toString());
        novel.setDescription(txtNovelDesc.getText().toString());
        //novel.setDatePost();
        novel.setIdUser(MainActivity.loggedUser.getId());
        novel.setCoverImageData(imageToString(bitmap));

        for (NovelModel iNovel : novelData) {
            if (iNovel.getId() == novel.getId()) {
                updateFlag = true;
                break;
            }
        }

        if (updateFlag == true) {
            novelController.updateNovel(novel, new IVolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(CRUDNovelActivity.this,
                            "Update success" + novel.getTitle(),
                            Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            novelController.insertNovel(novel, new IVolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(CRUDNovelActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            });
        }

        updateFlag = false;
        refreshListNovel();
    }

    private int getAuthorIDbyName(String name)
    {
        int authorID = 0;
        for (AuthorModel author : getAuthorList()
        ) {
            if(name.equals(author.getName()))
            {
                authorID = author.getId();
            }
        }
        return authorID;
    }

    private ArrayList<AuthorModel> getAuthorList()
    {
        ArrayList<AuthorModel> listAuthor = new ArrayList<>();
        authorController.getAuthors(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                listAuthor.clear();
                listAuthor.addAll(authorController.convertJSONData(result));
            }
        });
        return  listAuthor;
    }

    private ArrayList<String> getAuthorNameList() {
        ArrayList<AuthorModel> listAuthor = new ArrayList<>();
        ArrayList<String> listAuthorName = new ArrayList<>();
        authorController.getAuthors(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                listAuthor.clear();
                listAuthor.addAll(authorController.convertJSONData(result));
                for (AuthorModel author : listAuthor
                     ) {
                    listAuthorName.add(author.getName());
                }
                //authorItemAdapter.notifyDataSetChanged();
            }
        });
        return listAuthorName;
    }

    public void refreshListNovel()
    {
        novelController.getNovel(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                novelData.clear();
                novelData.addAll(novelController.convertJSONData(result));
                novelItemAdapter.notifyDataSetChanged();
            }
        });
    }
}