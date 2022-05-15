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

    ArrayList<ChapterModel> chapterData = new ArrayList<>();

    String url = LoadActivity.url;
    int novelID;
    boolean updateFlag = false;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_novel_details);
        setControl();
        setEvent();
    }

    private void setControl() {
        bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            novelID = bundle.getInt("idNovel");
        }

        txtYourNovelChapterName = findViewById(R.id.txtYourNovelChapterName);
        txtYourNovelChapterContent = findViewById(R.id.txtYourNovelChapterContent);

        btnYourNovelAddChapter = findViewById(R.id.btnYourNovelAddChapter);

        chapterController = new ChapterController(url, this);
        getChapters();
    }

    private void setEvent() {
        btnYourNovelAddChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chapter = new ChapterModel();
                chapter.setId(chapterData.get(chapterData.size()-1).getId()+1);
                chapter.setTitle(txtYourNovelChapterName.getText().toString());
                chapter.setContent(txtYourNovelChapterContent.getText().toString());
                chapter.setNovelID(novelID);

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
                            getChapters();
                        }
                    });
                } else {
                    chapterController.insertChapter(chapter, new IVolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            getChapters();
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

}