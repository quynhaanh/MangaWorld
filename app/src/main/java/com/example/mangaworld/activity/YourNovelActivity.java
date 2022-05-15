package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.NovelItemAdapter;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.NovelModel;

import java.util.ArrayList;

public class YourNovelActivity extends AppCompatActivity {

    Button btnYourNovelToAdding;
    ListView lvYourNovel;

    String url = LoadActivity.url;

    NovelController novelController;
    ArrayList<NovelModel> novelData = new ArrayList<>();
    NovelItemAdapter novelItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_novel);
        setControl();
        setEvent();
        refreshListYourNovel();
    }

    private void setEvent() {
        lvYourNovel.setAdapter(novelItemAdapter);
        btnYourNovelToAdding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity, YourNovelAddActivity.class);
//                startActivity(intent);
                startActivity(new Intent(YourNovelActivity.this, YourNovelAddActivity.class));
            }
        });

        lvYourNovel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NovelModel novel = novelData.get(i);
                //putExtra...
                Intent intent = new Intent(YourNovelActivity.this, YourNovelAddActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("NovelID", novel.getId());

                if(bundle != null)
                {
                    intent.putExtras(bundle);
                }

                startActivity(intent);
            }
        });
    }

    private void setControl() {
        btnYourNovelToAdding = findViewById(R.id.btnYourNovelToAdding);
        lvYourNovel = findViewById(R.id.lvYourNovel);

        novelController = new NovelController(url, this);
        novelItemAdapter = new NovelItemAdapter(YourNovelActivity.this,
                R.layout.layout_item_novel, novelData, url);
    }

    public void refreshListYourNovel()
    {
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