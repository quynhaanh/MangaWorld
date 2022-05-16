package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.NovelItemAdapter;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.NovelModel;

import java.util.ArrayList;

public class YourNovelActivity extends AppCompatActivity {
    TextView tvTitle;
    Button btnYourNovelToAdding;
    ListView lvYourNovel;

    String url = LoadActivity.url;

    NovelController novelController;
    ArrayList<NovelModel> novelData = new ArrayList<>();
    NovelItemAdapter novelItemAdapter;

    boolean adminMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_novel);
        setControl();
        setEvent();
        refreshListYourNovel();
    }

    private void setEvent() {
        tvTitle.setText(adminMode ? "Quản lý truyện" : "Truyện đã đăng");

        lvYourNovel.setAdapter(novelItemAdapter);
        btnYourNovelToAdding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YourNovelActivity.this, YourNovelAddActivity.class));
            }
        });

        lvYourNovel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NovelModel novel = novelData.get(i);
                //putExtra...
                Intent intent = new Intent(YourNovelActivity.this, YourNovelAddActivity.class);
                intent.putExtra("idNovel", novel.getId());

                startActivity(intent);
            }
        });
    }

    private void setControl() {
        adminMode = getIntent().getBooleanExtra("adminMode", false);

        btnYourNovelToAdding = findViewById(R.id.btnYourNovelToAdding);
        lvYourNovel = findViewById(R.id.lvYourNovel);
        tvTitle = findViewById(R.id.tvYourNovelTitle);

        novelController = new NovelController(url, this);
        novelItemAdapter = new NovelItemAdapter(YourNovelActivity.this,
                R.layout.layout_item_novel, novelData, url);
    }

    public void refreshListYourNovel() {
        if (adminMode) {
            novelController.getNovel(new IVolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    novelData.clear();
                    novelData.addAll(novelController.convertJSONData(result));
                    novelItemAdapter.notifyDataSetChanged();
                }
            });
        } else {
            novelController.getNovelByIDUser(MainActivity.loggedUser.getId(), new IVolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    novelData.clear();
                    novelData.addAll(novelController.convertJSONData(result));
                    novelItemAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListYourNovel();
    }
}