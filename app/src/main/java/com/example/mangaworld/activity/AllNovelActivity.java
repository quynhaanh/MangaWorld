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
import com.example.mangaworld.adapter.AllNovelRecyclerViewAdapter;
import com.example.mangaworld.adapter.ItemClickInterface;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.NovelModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class AllNovelActivity extends AppCompatActivity {
    private RecyclerView recycleViewAllNovel;
    private ShimmerFrameLayout shimmerAllNovel;
    private SearchView btnSearch;
    private ArrayList<NovelModel> novelArrayList;
    private  ArrayList<NovelModel> tmpArray;
    private AllNovelRecyclerViewAdapter allNovelRecyclerViewAdapter;
    private String checkNovel;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_manga);

        Intent intent = getIntent();
        checkNovel = intent.getStringExtra("checkNovel");
        if(checkNovel.equalsIgnoreCase("popular")){
            //call api get list manga popular
            LoadPopularManga();
        }
        else if(checkNovel.equalsIgnoreCase("genre")){
            //call api get list manga by genre
            LoadMangaByGenre();
        }
    }

    private void LoadMangaByGenre() {
        //lấy all manga theo thể loại nào đó

        int idGenre = getIntent().getIntExtra("genreID",0);
        String genreName= getIntent().getStringExtra("genreName");

        recycleViewAllNovel = findViewById(R.id.recycleViewAllNovel);
        btnSearch = findViewById(R.id.btnSearch);

        tvTitle = findViewById(R.id.tvAllNovelTitle);
        tvTitle.setText(genreName);

        shimmerAllNovel = findViewById(R.id.shimmerAllNovel);
        shimmerAllNovel.startShimmer();
        shimmerAllNovel.setVisibility(View.VISIBLE);
        novelArrayList = new ArrayList<>();
        tmpArray = new ArrayList<>();

        NovelController novelController = new NovelController(LoadActivity.url,this);
        novelController.getNovelByIDGenre(idGenre, new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                novelArrayList.clear();
                novelArrayList.addAll(novelController.convertJSONData(result));
                allNovelRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        allNovelRecyclerViewAdapter = new AllNovelRecyclerViewAdapter(novelArrayList,this);
        //click vào từng nút +
        allNovelRecyclerViewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(AllNovelActivity.this, DetailNovelActivity.class);
                intent.putExtra("idNovel", novelArrayList.get(position).getId());
                startActivity(intent);            }
        });
        recycleViewAllNovel.setAdapter(allNovelRecyclerViewAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycleViewAllNovel.setLayoutManager(layoutManager);

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
        shimmerAllNovel.stopShimmer();
        shimmerAllNovel.setVisibility(View.GONE);
    }

    private void LoadPopularManga() {
        recycleViewAllNovel = findViewById(R.id.recycleViewAllNovel);
        btnSearch = findViewById(R.id.btnSearch);

        tvTitle = findViewById(R.id.tvAllNovelTitle);
        tvTitle.setText("Thịnh hành");

        shimmerAllNovel = findViewById(R.id.shimmerAllNovel);
        shimmerAllNovel.startShimmer();
        shimmerAllNovel.setVisibility(View.VISIBLE);
        novelArrayList = new ArrayList<>();
        tmpArray = new ArrayList<>();

        NovelController novelController = new NovelController(LoadActivity.url,this);
        novelController.getMostViewNovel(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                novelArrayList.clear();
                novelArrayList.addAll(novelController.convertJSONData(result));
                allNovelRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        allNovelRecyclerViewAdapter = new AllNovelRecyclerViewAdapter(novelArrayList, this);
        //click vào từng nút +
        allNovelRecyclerViewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                NovelModel novel = allNovelRecyclerViewAdapter.getAtPosition(position);
                Intent intent = new Intent(AllNovelActivity.this, DetailNovelActivity.class);
                intent.putExtra("idManga", novel.getId());
                startActivity(intent);
            }
        });
        recycleViewAllNovel.setAdapter(allNovelRecyclerViewAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycleViewAllNovel.setLayoutManager(layoutManager);

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
        shimmerAllNovel.stopShimmer();
        shimmerAllNovel.setVisibility(View.GONE);
    }

    public void queryData(String query) {
        ArrayList<NovelModel> tmp = new ArrayList<>();
        for(NovelModel manga:tmpArray){
            if(manga.getTitle().contains(query))
                tmp.add(manga);
        }
        allNovelRecyclerViewAdapter.updateChange(tmp);
    }
}