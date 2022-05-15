package com.example.mangaworld.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.model.ChapterModel;

import java.util.ArrayList;

public class ChapterRecyclerViewAdapter extends RecyclerView.Adapter<ChapterRecyclerViewAdapter.ChapterRecyclerViewHoder> {
    private ArrayList<ChapterModel> chapterArrayList;
    private ItemClickInterface itemClickInterface;

    public ChapterRecyclerViewAdapter(ArrayList<ChapterModel> chapterArrayList) {
        this.chapterArrayList = chapterArrayList;
    }

    @NonNull
    @Override
    public ChapterRecyclerViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
        return new ChapterRecyclerViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterRecyclerViewHoder holder, int position) {
        bind(holder, position);
    }

    @Override
    public int getItemCount() {
        if(chapterArrayList.size()>0){
            return chapterArrayList.size();
        }
        else {
            return 0;
        }
    }

    public class ChapterRecyclerViewHoder extends RecyclerView.ViewHolder{
        public TextView txtName;
        public ConstraintLayout bgChapter;

        public ChapterRecyclerViewHoder(@NonNull View view) {
            super(view);
            this.txtName = (TextView) view.findViewById(R.id.textView11);
            this.bgChapter = (ConstraintLayout) view.findViewById(R.id.bgChapter);
            this.setIsRecyclable(false);
        }
    }

    @SuppressLint("SetTextI18n")
    public void bind(@NonNull ChapterRecyclerViewAdapter.ChapterRecyclerViewHoder viewHolder, int i){
        ChapterModel chapter = chapterArrayList.get(i);
        String chapterTitle = "Chapter "+ (i+1);
        if(chapter.getTitle()!=null)
        {
            chapterTitle += " - " + chapter.getTitle();
        }
        viewHolder.txtName.setText(chapterTitle);

        viewHolder.bgChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickInterface.onClick(v, i);
            }
        });
    }
    public void setOnClickItemRecyclerView(ItemClickInterface itemRecyclerView){
        itemClickInterface = itemRecyclerView;
    }

    public void updateChange(ArrayList<ChapterModel> data) {
        chapterArrayList = data;
        notifyDataSetChanged();
    }
}
