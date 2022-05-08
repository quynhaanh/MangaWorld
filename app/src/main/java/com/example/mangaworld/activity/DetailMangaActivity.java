package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.ChapterRecyclerViewAdapter;
import com.example.mangaworld.adapter.ItemClickInterface;
import com.example.mangaworld.model.Chapter;
import com.example.mangaworld.model.DetailManga;
import com.example.mangaworld.model.Genre;
import com.example.mangaworld.model.Manga;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class DetailMangaActivity extends AppCompatActivity {
    private RecyclerView recycleViewChapter;
    private TextView tvMangaName, tvBack, tvSoTap, tvNgayTao, tvTacGia, tvLuotXem, tvMoTa, tvTheLoai;
    private ImageView imgDetailManga;
    private Button btnFavorite;
    private ArrayList<Chapter> chapterArrayList;
    private ArrayList<Genre> genreArrayList;
    private ChapterRecyclerViewAdapter chapterRecyclerViewAdapter;
    private int idManga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_chapter);

        //        Chuyển màn hình qua nhận cái id manga để get 1 cái manga có chứa list chapter từ cái id này
        Intent intent = getIntent();
        idManga = intent.getIntExtra("idManga", 0);

        LoadMangaAndChapter(idManga);
    }
    @SuppressLint("SetTextI18n")
    private void LoadMangaAndChapter(int idManga) {
        //ánh xạ
        recycleViewChapter = findViewById(R.id.recycleViewChapter);
        imgDetailManga = findViewById(R.id.imgDetailManga);
        tvMangaName = findViewById(R.id.tvMangaName);
        tvSoTap = findViewById(R.id.tvSoTap);
        tvNgayTao = findViewById(R.id.tvNgayTao);
        tvTacGia = findViewById(R.id.tvTacGia);
        tvLuotXem = findViewById(R.id.tvLuotXem);
        tvMoTa = findViewById(R.id.tvMoTa);
        tvTheLoai = findViewById(R.id.tvTheLoai);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailMangaActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
            }
        });


        chapterArrayList = new ArrayList<>();
        genreArrayList = new ArrayList<>();
        Chapter chapter1 = new Chapter(0, "10", "Bắt đầu hành trình mới", "", false);
        Chapter chapter2 = new Chapter(1, "10", "Đi đánh lộn", "", false);
        Chapter chapter3 = new Chapter(2, "10", "Đập banh nóc", "", false);
        Chapter chapter4 = new Chapter(3, "10", "Hết truyện luôn", "", true);

        chapterArrayList.add(chapter1);
        chapterArrayList.add(chapter2);
        chapterArrayList.add(chapter3);
        chapterArrayList.add(chapter4);

        Genre genre = new Genre(1, "Hành động");
        Genre genre1 = new Genre(2, "Phép thuật");
        Genre genre2 = new Genre(1, "Siêu nhiên");
        genreArrayList.add(genre);
        genreArrayList.add(genre1);
        genreArrayList.add(genre2);

        Manga manga = new Manga(1, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1638689117/cqihlyiwovjgksu3jnmy.jpg","Tiệc tùng thôi nào", "abc", "390", "06/05/2021", "06/05/2022", true, "xyz", 1, 30000);
        DetailManga detailManga = new DetailManga(1, 1, "Nhật Minh", "",chapterArrayList, genreArrayList);

        //gắn data vào view
        Picasso.get()
                .load(manga.getLink())
                .resize(100, 100)
                .centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(imgDetailManga);
        tvMangaName.setText("Tên truyện: "+manga.getTitle());
        tvLuotXem.setText("Lượt xem: "+String.valueOf(manga.getView()));
        tvMoTa.setText("Mô tả: "+manga.getRecents());
        tvNgayTao.setText("Ngày xuất bản: "+manga.getUpload_on());
        tvSoTap.setText("Số tập: "+manga.getEndpoint());
        String tl="";
        for(int i=0; i<genreArrayList.size(); i++){
            if(i < genreArrayList.size()-1){
                tl += genreArrayList.get(i).getNameGenre()+", ";
            }
            else tl += genreArrayList.get(i).getNameGenre();
        }
        tvTheLoai.setText("Thể loại: "+tl);
        tvTacGia.setText("Tác giả: "+detailManga.getAuthor());

        chapterRecyclerViewAdapter = new ChapterRecyclerViewAdapter(chapterArrayList);
        //click vào từng nút +
        chapterRecyclerViewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(DetailMangaActivity.this, ChapterActivity.class);
                intent.putExtra("idManga", manga.getIdManga());
                intent.putExtra("idChapter", chapterArrayList.get(position).getIdChapter());
                startActivity(intent);
            }
        });
        recycleViewChapter.setAdapter(chapterRecyclerViewAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycleViewChapter.setLayoutManager(layoutManager);
    }
}