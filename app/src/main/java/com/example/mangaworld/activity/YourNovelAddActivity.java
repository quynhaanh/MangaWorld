package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.ChapterItemAdapter;
import com.example.mangaworld.adapter.NovelItemAdapter;
import com.example.mangaworld.controller.AuthorController;
import com.example.mangaworld.controller.ChapterController;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.NovelModel;

import java.util.ArrayList;

public class YourNovelAddActivity extends AppCompatActivity {

    EditText txtYourNovelTitle, txtYourNovelDesc;
    AutoCompleteTextView txtYourNovelAuthor; // Editable ComboBox(perhaps)
    Button btnYourNovelConfirm;
    ImageButton ibtnYourNovelImgPicker;
    ListView lvYourNovelChapter;

    ArrayList<NovelModel> novelData = new ArrayList<>();
    NovelController novelController;
    AuthorController authorController;
    ChapterController chapterController;

    //NovelItemAdapter novelItemAdapter;
    ChapterItemAdapter chapterItemAdapter;

    Bundle bundle = getIntent().getExtras();

    String url = LoadActivity.url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_novel_add);
        setControl();
        setEvent();
    }

    private void setEvent() {
    }

    private void setControl() {
        txtYourNovelTitle = findViewById(R.id.txtYourNovelTitle);
        txtYourNovelAuthor = findViewById(R.id.txtYourNovelAuthor);
        txtYourNovelDesc = findViewById(R.id.txtYourNovelDesc);

        btnYourNovelConfirm = findViewById(R.id.btnYourNovelConfirm);

        ibtnYourNovelImgPicker = findViewById(R.id.ibtnYourNovelImgPicker);

        lvYourNovelChapter = findViewById(R.id.lvYourNovel);
    }

    public void loadNovelData(NovelModel novel)
    {
        txtYourNovelTitle.setText(novel.getTitle());
        txtYourNovelAuthor.setText(novel.getIdAuthor());
        txtYourNovelDesc.setText(novel.getDescription());
        //
    }
}