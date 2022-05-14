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
import com.example.mangaworld.adapter.ItemClickInterface;
import com.example.mangaworld.model.Manga;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;


public class LibraryFragment extends Fragment {
    private RecyclerView recycleViewAllManga;
    private ShimmerFrameLayout shimmerAllManga;
    private TextView btnBack;
    private SearchView btnSearch;
    private ArrayList<Manga> mangaArrayList;
    private  ArrayList<Manga> tmpArray;
    private AllMangaRecyclerViewAdapter allMangaRecyclerViewAdapter;
    private String checkManga;
    public LibraryFragment() {
        // Required empty public constructor
    }

    public static LibraryFragment newInstance(String param1, String param2) {
        LibraryFragment fragment = new LibraryFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        LoadSearchManga(view);
        return view;
    }
    private void LoadSearchManga(View view) {
        recycleViewAllManga = view.findViewById(R.id.recycleViewAllManga);
        btnSearch = view.findViewById(R.id.btnSearch);
        shimmerAllManga = view.findViewById(R.id.shimmerAllManga);
        shimmerAllManga.startShimmer();
        shimmerAllManga.setVisibility(View.VISIBLE);
        mangaArrayList = new ArrayList<>();
        tmpArray = new ArrayList<>();

        //lấy dữ liệu tất cả truyện
        Manga slide = new Manga(1, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507319/Mangaworld/MV5BODcwNWE3OTMtMDc3MS00NDFjLWE1OTAtNDU3NjgxODMxY2UyXkEyXkFqcGdeQXVyNTAyODkwOQ_._V1_FMjpg_UX1000__ij1oft.jpg","Vua hải tặc", 30000, "Phiêu lưu");
        Manga slide1 = new Manga(2, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507145/Mangaworld/Naruto_Volume_1_manga_cover_q2cere.jpg","Naruto", 50000, "Hành động");
        Manga slide2 = new Manga(3, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507429/Mangaworld/conan_24_-_main_poster_1__vijsfb.jpg","Thám tử lừng danh conan", 80000, "Trinh thám");
        Manga slide3 = new Manga(4, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507420/Mangaworld/tranh-dan-tuong-3d-cartoon-songoku-scaled_cv67o3.jpg","Bảy viên ngọc rồng Z", 10000, "Hành động");

        mangaArrayList.add(slide);
        tmpArray.add(slide);
        mangaArrayList.add(slide2);
        tmpArray.add(slide2);
        mangaArrayList.add(slide1);
        tmpArray.add(slide1);
        mangaArrayList.add(slide3);
        tmpArray.add(slide3);

        allMangaRecyclerViewAdapter = new AllMangaRecyclerViewAdapter(mangaArrayList);
        //click vào từng nút +
        allMangaRecyclerViewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Manga manga = allMangaRecyclerViewAdapter.getAtPosition(position);
                Toast.makeText(getContext(), manga.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DetailMangaActivity.class);
                intent.putExtra("idManga", manga.getIdManga());
                startActivity(intent);            }
        });
        recycleViewAllManga.setAdapter(allMangaRecyclerViewAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
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