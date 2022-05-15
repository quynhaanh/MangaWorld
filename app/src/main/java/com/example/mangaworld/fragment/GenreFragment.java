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

import com.example.mangaworld.R;
import com.example.mangaworld.activity.AllNovelActivity;
import com.example.mangaworld.activity.LoadActivity;
import com.example.mangaworld.adapter.GenreRecylerviewAdapter;
import com.example.mangaworld.adapter.ItemClickInterface;
import com.example.mangaworld.controller.GenreController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.model.GenreModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;


public class GenreFragment extends Fragment {
    private RecyclerView recyclerViewGenre;
    private ShimmerFrameLayout shimmerGenre;
    private SearchView btnSearch;
    private ArrayList<GenreModel> genreModelArrayList;
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
        getGenreList();

        return view;
    }

    private void LoadGenre(View view) {
        recyclerViewGenre = view.findViewById(R.id.recycleViewGenreFragment);
        btnSearch = view.findViewById(R.id.btnSearchGenre);
        shimmerGenre = view.findViewById(R.id.contentShimmerGener);
        shimmerGenre.startShimmer();
        shimmerGenre.setVisibility(View.VISIBLE);
        genreModelArrayList = new ArrayList<>();

        genreRecylerviewAdapter = new GenreRecylerviewAdapter(genreModelArrayList);
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
                Intent intent = new Intent(getContext(), AllNovelActivity.class);
                intent.putExtra("checkNovel", "genre");
                intent.putExtra("genreID", genreModel.getId());
                intent.putExtra("genreName", genreModel.getName());
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

        if(query.isEmpty())
        {
            tmp = genreModelArrayList;
        }
        else {
            for(GenreModel genre: genreModelArrayList){
                if(genre.getName().toLowerCase().contains(query))
                    tmp.add(genre);
            }
        }

        genreRecylerviewAdapter.updateChange(tmp);
    }

    private void getGenreList()
    {
        GenreController controller = new GenreController(LoadActivity.url,getActivity());
        controller.getGenres(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                genreModelArrayList.clear();
                genreModelArrayList.addAll(controller.convertJSONData(result));
                genreRecylerviewAdapter.notifyDataSetChanged();
            }
        });
    }
}