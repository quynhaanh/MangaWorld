package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mangaworld.R;
import com.example.mangaworld.model.NovelModel;

public class YourNovelAddActivity extends AppCompatActivity {

    EditText txtYourNovelTitle, txtYourNovelAuthor, txtYourNovelDesc;
    Button btnYourNovelConfirm;
    ImageButton ibtnYourNovelImgPicker;
    ListView lvYourNovelChapter;

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
    }

    public void loadNovelData(NovelModel novel)
    {
        txtYourNovelTitle.setText(novel.getTitle());
        txtYourNovelAuthor.setText(novel.getIDAuthor());
//        txtYourNovelDesc.setText();
    }
}