package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.example.mangaworld.R;

import com.example.mangaworld.adapter.ContentChapRecyclerviewAdapter;

import com.example.mangaworld.controller.ChapterController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.ChapterModel;


import java.util.ArrayList;

public class ChapterActivity extends AppCompatActivity {
    private RecyclerView recycleViewContentChapter;
    private TextView tvNext, tvPositive;
    private ArrayList<ChapterModel> chapterArrayList;
    private ContentChapRecyclerviewAdapter contentChapRecyclerviewAdapter;
    private int idNovel;
    private int idChapter;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        Intent intent = getIntent();
        idNovel = intent.getIntExtra("idNovel", 0);
        idChapter = intent.getIntExtra("idChapter", 0);

        LoadContentChapter();
        getChapterList();
        increaseViewCount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        id_cur = idChapter;
        scrollToItem(idChapter);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_cur<chapterArrayList.size()){
                    scrollToItem(++id_cur);
                }

            }
        });

        tvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_cur>0){
                    scrollToItem(--id_cur);
                }
            }
        });
    }

    public int id_cur;
    private void LoadContentChapter() {
        id_cur = idChapter;
        recycleViewContentChapter = findViewById(R.id.recycleViewContentChap);
        tvNext = findViewById(R.id.txtGiam);
        tvPositive = findViewById(R.id.txtTang);
        scrollToItem(idChapter);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_cur<chapterArrayList.size()){
                    scrollToItem(++id_cur);
                }

            }
        });

        tvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_cur>0){
                    scrollToItem(--id_cur);
                }
            }
        });

        chapterArrayList = new ArrayList<>();

        contentChapRecyclerviewAdapter = new ContentChapRecyclerviewAdapter(chapterArrayList);
        recycleViewContentChapter.setAdapter(contentChapRecyclerviewAdapter);

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleViewContentChapter.setLayoutManager(layoutManager);
    }

    private void scrollToItem(int position) {
        if(layoutManager == null){
            return;
        }
        layoutManager.scrollToPosition(position);

    }

    private void getChapterList()
    {
        ChapterController chapterController = new ChapterController(LoadActivity.url,this);
        chapterController.getChaptersByNovelId(idNovel, new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                chapterArrayList.clear();
                chapterArrayList.addAll(chapterController.convertJSONData(result));
                contentChapRecyclerviewAdapter.notifyDataSetChanged();
                scrollToItem(idChapter);
            }
        });
    }

    private void increaseViewCount()
    {
        NovelController novelController = new NovelController(LoadActivity.url,this);
        novelController.increaseNovelViewCountByID(idNovel);
    }
}