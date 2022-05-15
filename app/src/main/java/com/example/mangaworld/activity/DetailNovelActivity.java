package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.ChapterRecyclerViewAdapter;
import com.example.mangaworld.adapter.ItemClickInterface;
import com.example.mangaworld.controller.AuthorController;
import com.example.mangaworld.controller.ChapterController;
import com.example.mangaworld.controller.GenreController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.ChapterModel;
import com.example.mangaworld.model.GenreModel;
import com.example.mangaworld.model.NovelModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailNovelActivity extends AppCompatActivity {
    private RecyclerView recycleViewChapter;
    private TextView tvNovelName, tvNgayTao, tvTacGia, tvLuotXem, tvMoTa, tvTheLoai;
    private ImageView imgDetailNovel;
    private ArrayList<ChapterModel> chapterArrayList;
    private ArrayList<GenreModel> genreArrayList;
    private ChapterRecyclerViewAdapter chapterRecyclerViewAdapter;
    private int idNovel;
    private NovelModel novel;
    String imageUrl = LoadActivity.url + "/api/truyenchu/images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_chapter);

        //        Chuyển màn hình qua nhận cái id manga để get 1 cái manga có chứa list chapter từ cái id này
        Intent intent = getIntent();
        idNovel = intent.getIntExtra("idNovel", 0);

        LoadMangaAndChapter(idNovel);
    }

    @SuppressLint("SetTextI18n")
    private void LoadMangaAndChapter(int idManga) {
        //ánh xạ
        recycleViewChapter = findViewById(R.id.recycleViewChapter);
        imgDetailNovel = findViewById(R.id.imgDetailNovel);
        tvNovelName = findViewById(R.id.tvNovelName);
        tvNgayTao = findViewById(R.id.tvNgayTao);
        tvTacGia = findViewById(R.id.tvTacGia);
        tvLuotXem = findViewById(R.id.tvLuotXem);
        tvMoTa = findViewById(R.id.tvMoTa);
        tvTheLoai = findViewById(R.id.tvTheLoai);


        chapterArrayList = new ArrayList<>();
        genreArrayList = new ArrayList<>();

        ChapterController chapterController = new ChapterController(LoadActivity.url, this);
        chapterController.getChaptersByNovelId(idNovel,new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                chapterArrayList.clear();
                chapterArrayList.addAll(chapterController.convertJSONData(result));
                chapterRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        NovelController novelController = new NovelController(LoadActivity.url, this);
        novelController.getNovelByID(idManga, new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                novel = novelController.convertJSONNovel(result);
                //gắn data vào view
                Picasso.get()
                        .load(imageUrl + novel.getCover() + ".jpg")
                        .resize(1000, 1000)
                        .centerCrop()
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                        .into(imgDetailNovel);
                tvNovelName.setText(novel.getTitle());
                tvLuotXem.setText(novel.getViewCount()+"");
                tvMoTa.setText(novel.getDescription());

                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(novel.getDatePost());
                    tvNgayTao.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
                } catch (ParseException e) {
                    tvNgayTao.setText("");
                }


                AuthorController authorController = new AuthorController(LoadActivity.url, DetailNovelActivity.this);
                authorController.getAuthorByID(novel.getIdAuthor(), new IVolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        tvTacGia.setText(authorController.convertJSONAuthor(result).getName());
                    }
                });
            }
        });

        GenreController genreController = new GenreController(LoadActivity.url,this);
        genreController.getGenreByNovelID(idNovel, new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                ArrayList<GenreModel> genreList = genreController.convertJSONData(result);
                String genreStr = "";
                for (int i = 0; i < genreList.size(); i++) {
                    genreStr += genreList.get(i).getName();
                    if (i < genreList.size() - 1) {
                        genreStr += ", ";
                    }
                }
                tvTheLoai.setText(genreStr);
            }
        });



        chapterRecyclerViewAdapter = new ChapterRecyclerViewAdapter(chapterArrayList);
        //click vào từng nút +
        chapterRecyclerViewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(DetailNovelActivity.this, ChapterActivity.class);
                intent.putExtra("idNovel", novel.getId());
                intent.putExtra("idChapter", position);
                startActivity(intent);
            }
        });
        recycleViewChapter.setAdapter(chapterRecyclerViewAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycleViewChapter.setLayoutManager(layoutManager);
    }
}