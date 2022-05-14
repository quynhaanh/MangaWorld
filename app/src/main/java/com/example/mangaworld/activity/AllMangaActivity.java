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
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.AllMangaRecyclerViewAdapter;
import com.example.mangaworld.adapter.ItemClickInterface;
import com.example.mangaworld.adapter.MangaRecyclerviewAdapter;
import com.example.mangaworld.adapter.TopMangaRecyclerViewAdapter;
import com.example.mangaworld.model.Manga;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class AllMangaActivity extends AppCompatActivity {
    private RecyclerView recycleViewAllManga;
    private ShimmerFrameLayout shimmerAllManga;
    private TextView btnBack;
    private SearchView btnSearch;
    private ArrayList<Manga> mangaArrayList;
    private  ArrayList<Manga> tmpArray;
    private AllMangaRecyclerViewAdapter allMangaRecyclerViewAdapter;
    private String checkManga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_manga);

        Intent intent = getIntent();
        checkManga = intent.getStringExtra("checkManga");
        if(checkManga.equalsIgnoreCase("popular")){
            //call api get list manga popular
            LoadPopularManga();
        }
        else if(checkManga.equalsIgnoreCase("manhua")){
            //call api get list manga manhua
            LoadManhuaManga();
        }
        else if(checkManga.equalsIgnoreCase("manhwa")){
            //call api get list manga manhwa
            LoadManhwaManga();
        }
        else if(checkManga.equalsIgnoreCase("top")){
            //call api get list manga on top
            LoadTopManga();
        }
        else if(checkManga.equalsIgnoreCase("1")){
            //call api get list manga by genre
            LoadMangaByGenre();
        }
    }

    private void LoadMangaByGenre() {
        //lấy all manga theo thể loại nào đó
        recycleViewAllManga = findViewById(R.id.recycleViewAllManga);
        btnSearch = findViewById(R.id.btnSearch);
        shimmerAllManga = findViewById(R.id.shimmerAllManga);
        shimmerAllManga.startShimmer();
        shimmerAllManga.setVisibility(View.VISIBLE);
        mangaArrayList = new ArrayList<>();
        tmpArray = new ArrayList<>();

        Manga slide = new Manga(2, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507145/Mangaworld/Naruto_Volume_1_manga_cover_q2cere.jpg","Naruto", 50000, "Hành động");;
        mangaArrayList.add(slide);
        tmpArray.add(slide);

        allMangaRecyclerViewAdapter = new AllMangaRecyclerViewAdapter(mangaArrayList);
        //click vào từng nút +
        allMangaRecyclerViewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(AllMangaActivity.this, DetailMangaActivity.class);
                intent.putExtra("idManga", mangaArrayList.get(position).getIdManga());
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
        shimmerAllManga = findViewById(R.id.shimmerAllManga);
        shimmerAllManga.startShimmer();
        shimmerAllManga.setVisibility(View.VISIBLE);
        mangaArrayList = new ArrayList<>();
        tmpArray = new ArrayList<>();

        Manga slide = new Manga(1, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507319/Mangaworld/MV5BODcwNWE3OTMtMDc3MS00NDFjLWE1OTAtNDU3NjgxODMxY2UyXkEyXkFqcGdeQXVyNTAyODkwOQ_._V1_FMjpg_UX1000__ij1oft.jpg","Vua hải tặc", 30000, "Phiêu lưu");
        mangaArrayList.add(slide);
        tmpArray.add(slide);

        allMangaRecyclerViewAdapter = new AllMangaRecyclerViewAdapter(mangaArrayList);
        //click vào từng nút +
        allMangaRecyclerViewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Manga manga = allMangaRecyclerViewAdapter.getAtPosition(position);
                Intent intent = new Intent(AllMangaActivity.this, DetailMangaActivity.class);
                intent.putExtra("idManga", manga.getIdManga());
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
    private void LoadManhuaManga() {
        recycleViewAllManga = findViewById(R.id.recycleViewAllManga);
        btnSearch = findViewById(R.id.btnSearch);
        shimmerAllManga = findViewById(R.id.shimmerAllManga);
        shimmerAllManga.startShimmer();
        shimmerAllManga.setVisibility(View.VISIBLE);
        mangaArrayList = new ArrayList<>();
        tmpArray = new ArrayList<>();

        Manga slide1 = new Manga(2, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507145/Mangaworld/Naruto_Volume_1_manga_cover_q2cere.jpg","Naruto", 50000, "Hành động");
        Manga slide2 = new Manga(3, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507429/Mangaworld/conan_24_-_main_poster_1__vijsfb.jpg","Thám tử lừng danh conan", 80000, "Trinh thám");
        mangaArrayList.add(slide1);
        mangaArrayList.add(slide2);
        tmpArray.add(slide1);
        tmpArray.add(slide2);


        allMangaRecyclerViewAdapter = new AllMangaRecyclerViewAdapter(mangaArrayList);
        //click vào từng nút +
        allMangaRecyclerViewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(AllMangaActivity.this, DetailMangaActivity.class);
                intent.putExtra("idManga", mangaArrayList.get(position).getIdManga());
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
    private void LoadManhwaManga() {
        recycleViewAllManga = findViewById(R.id.recycleViewAllManga);
        btnSearch = findViewById(R.id.btnSearch);
        shimmerAllManga = findViewById(R.id.shimmerAllManga);
        shimmerAllManga.startShimmer();
        shimmerAllManga.setVisibility(View.VISIBLE);
        mangaArrayList = new ArrayList<>();
        tmpArray = new ArrayList<>();

        Manga slide3 = new Manga(4, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507420/Mangaworld/tranh-dan-tuong-3d-cartoon-songoku-scaled_cv67o3.jpg","Bảy viên ngọc rồng Z", 10000, "Hành động");
        mangaArrayList.add(slide3);
        tmpArray.add(slide3);

        allMangaRecyclerViewAdapter = new AllMangaRecyclerViewAdapter(mangaArrayList);
        //click vào từng nút +
        allMangaRecyclerViewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(AllMangaActivity.this, DetailMangaActivity.class);
                intent.putExtra("idManga", mangaArrayList.get(position).getIdManga());
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
    private void LoadTopManga() {
        recycleViewAllManga = findViewById(R.id.recycleViewAllManga);
        btnSearch = findViewById(R.id.btnSearch);
        shimmerAllManga = findViewById(R.id.shimmerAllManga);
        shimmerAllManga.startShimmer();
        shimmerAllManga.setVisibility(View.VISIBLE);
        mangaArrayList = new ArrayList<>();
        tmpArray = new ArrayList<>();

        Manga slide = new Manga(1, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507319/Mangaworld/MV5BODcwNWE3OTMtMDc3MS00NDFjLWE1OTAtNDU3NjgxODMxY2UyXkEyXkFqcGdeQXVyNTAyODkwOQ_._V1_FMjpg_UX1000__ij1oft.jpg","Vua hải tặc", 30000, "Phiêu lưu");
        Manga slide3 = new Manga(4, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507420/Mangaworld/tranh-dan-tuong-3d-cartoon-songoku-scaled_cv67o3.jpg","Bảy viên ngọc rồng Z", 10000, "Hành động");
        mangaArrayList.add(slide);
        mangaArrayList.add(slide3);
        tmpArray.add(slide);
        tmpArray.add(slide3);

        allMangaRecyclerViewAdapter = new AllMangaRecyclerViewAdapter(mangaArrayList);
        //click vào từng nút +
        allMangaRecyclerViewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(AllMangaActivity.this, DetailMangaActivity.class);
                intent.putExtra("idManga", mangaArrayList.get(position).getIdManga());
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
    public void queryData(String query) {
        ArrayList<Manga> tmp = new ArrayList<>();
        for(Manga manga:tmpArray){
            if(manga.getTitle().contains(query))
                tmp.add(manga);
        }
        allMangaRecyclerViewAdapter.updateChange(tmp);
    }
}