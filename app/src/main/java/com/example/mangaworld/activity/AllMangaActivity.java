package com.example.mangaworld.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.AllMangaRecyclerViewAdapter;
import com.example.mangaworld.adapter.ItemClickInterface;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.NovelModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class AllMangaActivity extends AppCompatActivity {
    private RecyclerView recycleViewAllManga;
    private ShimmerFrameLayout shimmerAllManga;
    private SearchView btnSearch;
    private ArrayList<NovelModel> mangaArrayList;
    private  ArrayList<NovelModel> tmpArray;
    private AllMangaRecyclerViewAdapter allMangaRecyclerViewAdapter;
    private String checkManga;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_manga);

        Intent intent = getIntent();
        checkManga = intent.getStringExtra("checkNovel");
        if(checkManga.equalsIgnoreCase("popular")){
            //call api get list manga popular
            LoadPopularManga();
        }
        else if(checkManga.equalsIgnoreCase("genre")){
            //call api get list manga by genre
            LoadMangaByGenre();
        }
    }

    private void LoadMangaByGenre() {
        //lấy all manga theo thể loại nào đó

        int idGenre = getIntent().getIntExtra("genreID",0);
        String genreName= getIntent().getStringExtra("genreName");

        recycleViewAllManga = findViewById(R.id.recycleViewAllManga);
        btnSearch = findViewById(R.id.btnSearch);

        tvTitle = findViewById(R.id.tvAllNovelTitle);
        tvTitle.setText(genreName);

        shimmerAllManga = findViewById(R.id.shimmerAllManga);
        shimmerAllManga.startShimmer();
        shimmerAllManga.setVisibility(View.VISIBLE);
        mangaArrayList = new ArrayList<>();
        tmpArray = new ArrayList<>();

        NovelController novelController = new NovelController(LoadActivity.url,this);
        novelController.getNovelByIDGenre(idGenre, new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                mangaArrayList.clear();
                mangaArrayList.addAll(novelController.convertJSONData(result));
                allMangaRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        allMangaRecyclerViewAdapter = new AllMangaRecyclerViewAdapter(mangaArrayList,this);
        //click vào từng nút +
        allMangaRecyclerViewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(AllMangaActivity.this, DetailMangaActivity.class);
                intent.putExtra("idNovel", mangaArrayList.get(position).getId());
                startActivity(intent);            }
        });
        recycleViewAllManga.setAdapter(allMangaRecyclerViewAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycleViewAllManga.setLayoutManager(layoutManager);

        btnSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {
                queryData(newText);
                return true;
            }
        });

        //tắt shimmer
        shimmerAllManga.stopShimmer();
        shimmerAllManga.setVisibility(View.GONE);
    }

    private void LoadPopularManga() {
        recycleViewAllManga = findViewById(R.id.recycleViewAllManga);
        btnSearch = findViewById(R.id.btnSearch);

        tvTitle = findViewById(R.id.tvAllNovelTitle);
        tvTitle.setText("Thịnh hành");

        shimmerAllManga = findViewById(R.id.shimmerAllManga);
        shimmerAllManga.startShimmer();
        shimmerAllManga.setVisibility(View.VISIBLE);
        mangaArrayList = new ArrayList<>();
        tmpArray = new ArrayList<>();

        NovelController novelController = new NovelController(LoadActivity.url,this);
        novelController.getMostViewNovel(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                mangaArrayList.clear();
                mangaArrayList.addAll(novelController.convertJSONData(result));
                allMangaRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        allMangaRecyclerViewAdapter = new AllMangaRecyclerViewAdapter(mangaArrayList, this);
        //click vào từng nút +
        allMangaRecyclerViewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                NovelModel novel = allMangaRecyclerViewAdapter.getAtPosition(position);
                Intent intent = new Intent(AllMangaActivity.this, DetailMangaActivity.class);
                intent.putExtra("idManga", novel.getId());
                startActivity(intent);
            }
        });
        recycleViewAllManga.setAdapter(allMangaRecyclerViewAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycleViewAllManga.setLayoutManager(layoutManager);

        btnSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {
                queryData(newText);
                return true;
            }
        });

        //tắt shimmer
        shimmerAllManga.stopShimmer();
        shimmerAllManga.setVisibility(View.GONE);
    }

    public void queryData(String query) {
        ArrayList<NovelModel> tmp = new ArrayList<>();
        for(NovelModel manga:tmpArray){
            if(manga.getTitle().contains(query))
                tmp.add(manga);
        }
        allMangaRecyclerViewAdapter.updateChange(tmp);
    }
}