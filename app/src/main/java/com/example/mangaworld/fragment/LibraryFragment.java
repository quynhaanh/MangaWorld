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
import com.example.mangaworld.activity.DetailNovelActivity;
import com.example.mangaworld.activity.LoadActivity;
import com.example.mangaworld.adapter.AllNovelRecyclerViewAdapter;
import com.example.mangaworld.adapter.ItemClickInterface;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.NovelModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;


public class LibraryFragment extends Fragment {
    private RecyclerView recycleViewAllManga;
    private ShimmerFrameLayout shimmerAllManga;
    private SearchView btnSearch;
    private ArrayList<NovelModel> mangaArrayList;
    private ArrayList<NovelModel> tmpArray;
    private AllNovelRecyclerViewAdapter allNovelRecyclerViewAdapter;

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
        getNovelList();
        return view;
    }

    private void LoadSearchManga(View view) {
        recycleViewAllManga = view.findViewById(R.id.recycleViewAllNovel);
        btnSearch = view.findViewById(R.id.btnSearch);
        shimmerAllManga = view.findViewById(R.id.shimmerAllNovel);
        shimmerAllManga.startShimmer();
        shimmerAllManga.setVisibility(View.VISIBLE);
        mangaArrayList = new ArrayList<>();
        tmpArray = new ArrayList<>();

        allNovelRecyclerViewAdapter = new AllNovelRecyclerViewAdapter(mangaArrayList, getActivity());
        //click vào từng nút +
        allNovelRecyclerViewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                NovelModel manga = allNovelRecyclerViewAdapter.getAtPosition(position);
                Intent intent = new Intent(getContext(), DetailNovelActivity.class);
                intent.putExtra("idNovel", manga.getId());
                startActivity(intent);
            }
        });
        recycleViewAllManga.setAdapter(allNovelRecyclerViewAdapter);
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
        ArrayList<NovelModel> tmp = new ArrayList<>();
        if (query.isEmpty()) {
            tmp = mangaArrayList;
        } else {
            for (NovelModel novel : mangaArrayList) {
                if (novel.getTitle().toLowerCase().contains(query))
                    tmp.add(novel);
            }
        }

        allNovelRecyclerViewAdapter.updateChange(tmp);
    }

    private void getNovelList() {
        NovelController controller = new NovelController(LoadActivity.url, getActivity());
        controller.getNovelOrderByDatePost(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                mangaArrayList.clear();
                mangaArrayList.addAll(controller.convertJSONData(result));
                allNovelRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }
}