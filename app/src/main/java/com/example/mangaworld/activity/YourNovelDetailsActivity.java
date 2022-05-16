package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mangaworld.R;
import com.example.mangaworld.controller.ChapterController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.ChapterModel;

import java.util.ArrayList;

public class YourNovelDetailsActivity extends AppCompatActivity {

    EditText txtYourNovelChapterName, txtYourNovelChapterContent;
    Button btnYourNovelAddChapter;

    NovelController novelController;
    ChapterController chapterController;

    ChapterModel chapter;
    ChapterModel loadChapter;

    ArrayList<ChapterModel> chapterData = new ArrayList<>();

    String url = LoadActivity.url;
    int novelID;
    int novelIDAdapter;
    int chapterID;
    boolean updateFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_novel_details);
        setControl();
        setEvent();
    }

    private void setControl() {
        txtYourNovelChapterName = findViewById(R.id.txtYourNovelChapterName);
        txtYourNovelChapterContent = findViewById(R.id.txtYourNovelChapterContent);

        btnYourNovelAddChapter = findViewById(R.id.btnYourNovelAddChapter);

        chapterController = new ChapterController(url, this);
        getChapters();
    }

    private void setEvent() {
        novelID = getIntent().getIntExtra("idNovel", -1);// Gửi từ float button thêm mới

        novelIDAdapter = getIntent().getIntExtra("idNovelAdapter",-1); // Gửi từ adapter
        chapterID = getIntent().getIntExtra("idChapter", -1); // Gửi từ adapter

        if(chapterID != -1)
        {
            loadChapterData();
        }

        btnYourNovelAddChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chapter = new ChapterModel();
                if(chapterID != -1)
                {
                    chapter.setId(chapterID);
                    chapter.setNovelID(novelIDAdapter);
                }else{
                    chapter.setId(chapterData.get(chapterData.size()-1).getId()+1);
                    chapter.setNovelID(novelID);
                }
                chapter.setTitle(txtYourNovelChapterName.getText().toString());
                chapter.setContent(txtYourNovelChapterContent.getText().toString());

                for(ChapterModel chapterModel : chapterData)
                {
                    if(chapter.getId() == chapterModel.getId())
                    {
                        updateFlag = true;
                        break;
                    }
                }

                if (updateFlag == true) {
                    chapterController.updateChapter(chapter, new IVolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            finish();
                        }
                    });
                } else {
                    chapterController.insertChapter(chapter, new IVolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            finish();
                        }
                    });
                }

                updateFlag = false;
            }
        });
    }

    private void getChapters()
    {
        chapterController.getChapters(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                chapterData.clear();
                chapterData.addAll(chapterController.convertJSONData(result));
            }
        });
    }

    private void loadChapterData()
    {
        updateFlag = true;
        chapterController.getChapterById(chapterID, new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                loadChapter = chapterController.convertJSONChapter(result);

                txtYourNovelChapterName.setText(loadChapter.getTitle());
                txtYourNovelChapterContent.setText(loadChapter.getContent());
            }
        });
    }

}