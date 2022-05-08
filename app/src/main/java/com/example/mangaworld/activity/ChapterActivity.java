package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mangaworld.R;

import com.example.mangaworld.adapter.ChapterRecyclerViewAdapter;
import com.example.mangaworld.adapter.ContentChapRecyclerviewAdapter;

import com.example.mangaworld.adapter.ItemClickInterface;
import com.example.mangaworld.model.Chapter;


import java.util.ArrayList;

public class ChapterActivity extends AppCompatActivity {
    private RecyclerView recycleViewContentChapter;
    private TextView tvMangaName, tvContent, tvBack, tvNext, tvPositive;
    private ArrayList<Chapter> chapterArrayList;
    private ContentChapRecyclerviewAdapter contentChapRecyclerviewAdapter;
    private int idManga;
    private int idChapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        Intent intent = getIntent();
        idManga = intent.getIntExtra("idManga", 0);
        idChapter = intent.getIntExtra("idChapter", 0);
        Toast.makeText(ChapterActivity.this, String.valueOf(idChapter), Toast.LENGTH_SHORT).show();
        scrollToItem(idChapter-1);
        LoadContentChapter(idManga, idChapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scrollToItem(idChapter);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idChapter<chapterArrayList.size()){ ;
                    scrollToItem(idChapter+1);
                }
            }
        });

        tvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idChapter!=0){ ;
                    scrollToItem(idChapter-1);
                }
            }
        });
    }

    private void LoadContentChapter(int idManga, int idChapter) {
        recycleViewContentChapter = findViewById(R.id.recycleViewContentChap);
        tvBack = findViewById(R.id.textView9);
        tvNext = findViewById(R.id.txtGiam);
        tvPositive = findViewById(R.id.txtTang);
        scrollToItem(idChapter);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idChapter<chapterArrayList.size()){ ;
                    scrollToItem(idChapter+1);
                }
            }
        });

        tvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idChapter>0){ ;
                    scrollToItem(idChapter-1);
                }
            }
        });

        chapterArrayList = new ArrayList<>();
        Chapter chapter1 = new Chapter(0, "10", "Bắt đầu hành trình mới", "Tại Google, chúng tôi theo đuổi những ý tưởng và sản phẩm vượt khỏi các giới hạn công nghệ hiện có. Với tư cách là một công ty hành động có trách nhiệm, chúng tôi nỗ lực làm việc để đảm bảo rằng mọi sự đổi mới đều cân bằng với mức riêng tư và bảo mật thích hợp dành cho người dùng. Các Nguyên tắc về quyền riêng tư của chúng tôi giúp định hướng các quyết định mà chúng tôi thực hiện tại mọi cấp trong công ty, vì vậy, chúng tôi có thể giúp bảo vệ cũng như trao quyền cho người dùng trong khi vẫn hoàn thành sứ mệnh hiện nay của mình là sắp xếp thông tin của thế giới.\n" +
                "        \n\nKhi sử dụng dịch vụ của chúng tôi, bạn tin tưởng cung cấp thông tin của bạn cho chúng tôi. Chúng tôi hiểu rằng đây là một trách nhiệm lớn và chúng tôi nỗ lực bảo vệ thông tin của bạn cũng như để bạn nắm quyền kiểm soát.\n" +
                "        \n\nChính sách bảo mật này nhằm mục đích giúp bạn hiểu rõ những thông tin chúng tôi thu thập, lý do chúng tôi thu thập và cách bạn có thể cập nhật, quản lý, xuất và xóa thông tin của mình.", false);
        Chapter chapter2 = new Chapter(1, "10", "Đi đánh lộn", "Đánh lộn ầm ầm rồi hết chứ éo có mọe j", false);
        Chapter chapter3 = new Chapter(2, "10", "Đập banh nóc", "Coi coi cc", false);
        Chapter chapter4 = new Chapter(3, "10", "Hết truyện luôn", "Hết rồi ba", true);

        chapterArrayList.add(chapter1);
        chapterArrayList.add(chapter2);
        chapterArrayList.add(chapter3);
        chapterArrayList.add(chapter4);

        contentChapRecyclerviewAdapter = new ContentChapRecyclerviewAdapter(chapterArrayList);
        recycleViewContentChapter.setAdapter(contentChapRecyclerviewAdapter);

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleViewContentChapter.setLayoutManager(layoutManager);
    }

    private void scrollToItem(int position) {
        if(layoutManager == null){
            return;
        }
        layoutManager.scrollToPosition(position);

    }

}