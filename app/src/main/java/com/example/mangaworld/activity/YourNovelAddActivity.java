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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.ChapterItemAdapter;
import com.example.mangaworld.controller.AuthorController;
import com.example.mangaworld.controller.ChapterController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.AuthorModel;
import com.example.mangaworld.model.ChapterModel;
import com.example.mangaworld.model.NovelModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class YourNovelAddActivity extends AppCompatActivity {

    EditText txtYourNovelTitle, txtYourNovelDesc, txtYourNovelCoverName;
    AutoCompleteTextView txtYourNovelAuthor; // Editable ComboBox(perhaps)
    Button btnYourNovelConfirm;
    FloatingActionButton fbtnYourNovelToDetails;
    ImageButton ibtnYourNovelImgPicker;
    ListView lvYourNovelChapter;

    ArrayList<NovelModel> novelData = new ArrayList<>();
    ArrayList<AuthorModel> listAuthor = new ArrayList<>();
    ArrayList<ChapterModel> chapterData = new ArrayList<>();
    NovelController novelController;
    AuthorController authorController;
    ChapterController chapterController;

    //NovelItemAdapter novelItemAdapter;
    ChapterItemAdapter chapterItemAdapter;

    NovelModel loadNovel;
    AuthorModel author;

    Bundle bundle;

    String url = LoadActivity.url;
    boolean updateFlag = false;
    boolean updateFlagAuth = false;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_novel_add);
        setControl();
        setEvent();
        refreshListNovel();
    }

    private void setEvent() {
        if(bundle!=null)
        {
            loadNovelData();
        }

        lvYourNovelChapter.setAdapter(chapterItemAdapter);

        fbtnYourNovelToDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YourNovelAddActivity.this, YourNovelDetailsActivity.class));
            }
        });

        ibtnYourNovelImgPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btnYourNovelConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeNovelData();
            }
        });
    }

    private void setControl() {
        txtYourNovelTitle = findViewById(R.id.txtYourNovelTitle);
        txtYourNovelDesc = findViewById(R.id.txtYourNovelDesc);
        txtYourNovelCoverName = findViewById(R.id.txtYourNovelCoverName);

        txtYourNovelAuthor = findViewById(R.id.txtYourNovelAuthor);
        authorController = new AuthorController(url, this);
        ArrayAdapter adapterNovelAuthor = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line,
                getAuthorNameList());
        txtYourNovelAuthor.setAdapter(adapterNovelAuthor);

        btnYourNovelConfirm = findViewById(R.id.btnYourNovelConfirm);

        fbtnYourNovelToDetails = findViewById(R.id.fbtnYourNovelToDetails);

        ibtnYourNovelImgPicker = findViewById(R.id.ibtnYourNovelImgPicker);

        lvYourNovelChapter = findViewById(R.id.lvYourNovelChapter);
        chapterController = new ChapterController(url, this);
        chapterItemAdapter = new ChapterItemAdapter(YourNovelAddActivity.this,
                R.layout.layout_item_chapter, chapterData, url);
    }

    private void writeNovelData()
    {
        listAuthor = getAuthorList();
        String authorName = txtYourNovelAuthor.toString().trim();
        int authorID = 0;

        authorID = listAuthor.get(listAuthor.size()-1).getId();
        updateFlagAuth = true;


        for (AuthorModel iAuthor : listAuthor) {
            if(iAuthor.getName().equals( authorName))
            {
                authorID = iAuthor.getId();
                updateFlagAuth = false;
                Log.d("for" , authorID+"");
                break;
            }

        }

        author = new AuthorModel();
        author.setId(authorID+1);
        author.setName(authorName);
        if(updateFlagAuth)
        {
            authorController.updateAuthor(author, new IVolleyCallback() {
                @Override
                public void onSuccess(String result) {

                }
            });
        }else{
            authorController.insertAuthor(author, new IVolleyCallback() {
                @Override
                public void onSuccess(String result) {

                }
            });
        }

        NovelModel novel = new NovelModel();
        novel.setId(3);
        novel.setTitle(txtYourNovelTitle.getText().toString());
        novel.setIdAuthor(authorID);
        novel.setCover(novel.getId() + "-Cover" +txtYourNovelCoverName.getText().toString());
        novel.setDescription(txtYourNovelDesc.getText().toString());
        //novel.setDatePost();
        novel.setIdUser(MainActivity.loggedUser.getId());
        novel.setCoverImageData(imageToString(bitmap));

//        for (NovelModel iNovel : novelData) {
//            if (iNovel.getId() == novel.getId()) {
//                updateFlag = true;
//                break;
//            }
//        }

        if (updateFlag == true) {
            novelController.updateNovel(novel, new IVolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(YourNovelAddActivity.this,
                            "Update success" + novel.getTitle(),
                            Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            novelController.insertNovel(novel, new IVolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(YourNovelAddActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
            });
        }

        updateFlag = false;
        refreshListNovel();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                File f = new File(path + "");
                String imageName = f.getName();
                txtYourNovelCoverName.setText(imageName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadNovelData()
    {
        bundle = getIntent().getExtras();
        updateFlag = true;
        int novelID = bundle.getInt("NovelID");
        novelController.getNovelByID(novelID, new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                //novelData.add(novelController.convertJSONNovel(result));
                loadNovel = novelController.convertJSONNovel(result);
            }
        });
        String authorName = getAuthorNameById(loadNovel.getIdAuthor());
        txtYourNovelTitle.setText(loadNovel.getTitle());
        txtYourNovelAuthor.setText(authorName);
        txtYourNovelDesc.setText(loadNovel.getDescription());
        txtYourNovelCoverName.setText(loadNovel.getCover());
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

    private String getAuthorNameById(int id)
    {
        String authorName = "";
        for (AuthorModel author : getAuthorList()
        ) {
            if(id == author.getId())
            {
                authorName = author.getName();
            }
        }
        return authorName;
    }

    private ArrayList<AuthorModel> getAuthorList()
    {
        authorController.getAuthors(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                listAuthor.clear();
                listAuthor.addAll(authorController.convertJSONData(result));
            }
        });
        return listAuthor;
    }

    public void refreshListNovel()
    {
        try {
            novelController.getNovelByIDUser(MainActivity.loggedUser.getId(), new IVolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    novelData.clear();
                    novelData.addAll(novelController.convertJSONData(result));
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(this, "Bạn chưa có truyện nào" +
                    "\nHãy thêm truyện", Toast.LENGTH_SHORT).show();
        }

    }
}