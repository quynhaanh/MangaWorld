package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.mangaworld.R;
import com.example.mangaworld.controller.ChapterController;
import com.example.mangaworld.controller.NovelController;

public class YourNovelDetailsActivity extends AppCompatActivity {

    EditText txtYourNovelChapterName, txtYourNovelChapterContent;

    NovelController novelController;
    ChapterController chapterController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_novel_details);
        setControl();
        setEvent();
    }

    private void setControl() {

    }

    private void setEvent() {

    }
}