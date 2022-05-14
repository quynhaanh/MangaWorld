package com.example.mangaworld.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.AllMangaActivity;
import com.example.mangaworld.activity.DetailMangaActivity;
import com.example.mangaworld.adapter.AllMangaRecyclerViewAdapter;
import com.example.mangaworld.adapter.GenreRecylerviewAdapter;
import com.example.mangaworld.adapter.ItemClickInterface;
import com.example.mangaworld.model.GenreModel;
import com.example.mangaworld.model.Manga;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;


public class GenreFragment extends Fragment {
    private RecyclerView recyclerViewGenre;
    private ShimmerFrameLayout shimmerGenre;
    private TextView btnBack;
    private SearchView btnSearch;
    private ArrayList<GenreModel> genreModelArrayList;
    private  ArrayList<GenreModel> tmpArray;
    private  ArrayList<String> urlBGArraylist;
    private  ArrayList<String> urlTmpArraylist;
    private GenreRecylerviewAdapter genreRecylerviewAdapter;

    public static GenreFragment newInstance(String param1, String param2) {
        GenreFragment fragment = new GenreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_genre, container, false);

        LoadGenre(view);

        return view;
    }

    private void LoadGenre(View view) {
        recyclerViewGenre = view.findViewById(R.id.recycleViewGenreFragment);
        btnSearch = view.findViewById(R.id.btnSearchGenre);
        shimmerGenre = view.findViewById(R.id.contentShimmerGener);
        shimmerGenre.startShimmer();
        shimmerGenre.setVisibility(View.VISIBLE);
        genreModelArrayList = new ArrayList<>();
        tmpArray = new ArrayList<>();
        urlBGArraylist = new ArrayList<>();
        urlTmpArraylist = new ArrayList<>();

        //lấy dữ liệu tất cả truyện
        GenreModel genreModel1 = new GenreModel(1,"Hành động");
        GenreModel genreModel2 = new GenreModel(2,"Phép thuật");
        GenreModel genreModel3 = new GenreModel(3,"Siêu nhiên");
        GenreModel genreModel4 = new GenreModel(4,"Học đường");
        genreModelArrayList.add(genreModel1);
        genreModelArrayList.add(genreModel2);
        genreModelArrayList.add(genreModel3);
        genreModelArrayList.add(genreModel4);
        tmpArray.add(genreModel1);
        tmpArray.add(genreModel2);
        tmpArray.add(genreModel3);
        tmpArray.add(genreModel4);
        String url1 = "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652498644/Mangaworld/anime-hanh-dong-hay-nhat-ban-nen-xem-thu-54107_iki7yg.jpg";
        String url2 = "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652498638/Mangaworld/anime-phep-thuat_midjwk.jpg";
        String url3 = "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652498643/Mangaworld/20170409-112646-1_600x375_avjjip.jpg";
        String url4 = "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652498645/Mangaworld/053e87e47e5137e0_dcc7bc8a05258629_13907015298358613185710_pdjimj.jpg";
        urlBGArraylist.add(url1);
        urlBGArraylist.add(url2);
        urlBGArraylist.add(url3);
        urlBGArraylist.add(url4);
        urlTmpArraylist.add(url1);
        urlTmpArraylist.add(url2);
        urlTmpArraylist.add(url3);
        urlTmpArraylist.add(url4);

        genreRecylerviewAdapter = new GenreRecylerviewAdapter(genreModelArrayList, urlBGArraylist);
        //click vào từng nút +
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
        genreRecylerviewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                GenreModel genreModel = genreRecylerviewAdapter.getAtPosition(position);
                Toast.makeText(getContext(), genreModel.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), AllMangaActivity.class);
                intent.putExtra("checkManga", String.valueOf(genreModel.getId()));
                startActivity(intent);
            }
        });
        recyclerViewGenre.setAdapter(genreRecylerviewAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewGenre.setLayoutManager(layoutManager);



        //tắt shimmer
        shimmerGenre.stopShimmer();
        shimmerGenre.setVisibility(View.GONE);
    }
    public void queryData(String query) {
        ArrayList<GenreModel> tmp = new ArrayList<>();
        for(GenreModel manga:tmpArray){
            if(manga.getName().contains(query))
                tmp.add(manga);
        }

        ArrayList<String> tmpUrl = new ArrayList<>();
        for(String url:urlTmpArraylist){
                tmpUrl.add(url);
        }
        genreRecylerviewAdapter.updateChange(tmp, tmpUrl);
    }
}