package com.example.mangaworld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.CRUDAuthorActivity;
import com.example.mangaworld.activity.CRUDChapterActivity;
import com.example.mangaworld.activity.YourNovelAddActivity;
import com.example.mangaworld.controller.ChapterController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.ChapterModel;
import com.example.mangaworld.model.NovelModel;

import java.util.ArrayList;

public class ChapterItemAdapter  extends ArrayAdapter {
    ArrayList<ChapterModel> data;
    ArrayList<ChapterModel> datatmp = new ArrayList<>();
    int resource;
    Context context;
    String url;


    public ChapterItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ChapterModel> data, String url) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
        this.url = url;
        datatmp.addAll(data);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView tvID = convertView.findViewById(R.id.tvItemIDChapter);
        TextView tvName = convertView.findViewById(R.id.tvItemNameChapter);
        TextView tvNovel = convertView.findViewById(R.id.tvItemChapterNovel);
        TextView tvDate = convertView.findViewById(R.id.tvItemDateChapter);

        Button btnSua = convertView.findViewById(R.id.btnItemSuaChapter);
        Button btnXoa = convertView.findViewById(R.id.btnItemXoaChapter);



        ChapterModel chapter = data.get(position);
        tvID.setText(String.valueOf(chapter.getId()));
        tvName.setText(chapter.getTitle());
        //tvNovel.setText(String.valueOf(chapter.getNovelID()));
        tvDate.setText(chapter.getDatePost());

        ArrayList<NovelModel> listBook = new ArrayList<>();
        NovelController novelController = new NovelController(url, (YourNovelAddActivity) context);
        novelController.getNovel(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                listBook.addAll(novelController.convertJSONData(result));
                for (NovelModel novel : listBook) {
                    if(novel.getId()==chapter.getNovelID())
                    {
                        tvNovel.setText(novel.getTitle());
                    }
                }
            }
        });



        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((CRUDChapterActivity) context).loadData(chapter);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChapterController chapterDAO = new ChapterController(url, (YourNovelAddActivity) context);
                chapterDAO.deleteChapter(chapter, new IVolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        ((YourNovelAddActivity) context).getChapterListByNovelId();
                    }
                });
            }
        });
        return convertView;
    }
}
