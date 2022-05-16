package com.example.mangaworld.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.CRUDAuthorActivity;
import com.example.mangaworld.activity.CRUDChapterActivity;
import com.example.mangaworld.activity.CRUDNovelActivity;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.activity.YourNovelAddActivity;
import com.example.mangaworld.activity.YourNovelDetailsActivity;
import com.example.mangaworld.controller.ChapterController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.ChapterModel;
import com.example.mangaworld.model.NovelModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        TextView tvName = convertView.findViewById(R.id.tvItemNameChapter);
        TextView tvDate = convertView.findViewById(R.id.tvItemDateChapter);

        ImageButton btnSua = convertView.findViewById(R.id.btnItemSuaChapter);
        ImageButton btnXoa = convertView.findViewById(R.id.btnItemXoaChapter);



        ChapterModel chapter = data.get(position);

        tvName.setText(chapter.getTitle());

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(chapter.getDatePost());
            tvDate.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date));
        } catch (ParseException e) {
            tvDate.setText(chapter.getDatePost());
        }

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(context, YourNovelDetailsActivity.class);
                intent.putExtra("idChapter", chapter.getId());
                intent.putExtra("idNovelAdapter", chapter.getNovelID());
                context.startActivity(intent);
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
