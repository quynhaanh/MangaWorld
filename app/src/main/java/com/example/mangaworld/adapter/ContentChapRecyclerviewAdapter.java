package com.example.mangaworld.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.model.ChapterModel;

import java.util.ArrayList;

public class ContentChapRecyclerviewAdapter extends RecyclerView.Adapter<ContentChapRecyclerviewAdapter.ContentRecyclerViewHoder>{
    private ArrayList<ChapterModel> chapterArrayList;
    private ItemClickInterface itemClickInterface;

    public ContentChapRecyclerviewAdapter(ArrayList<ChapterModel> chapterArrayList) {
        this.chapterArrayList = chapterArrayList;
    }

    @NonNull
    @Override
    public ContentRecyclerViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content_chapter, parent, false);
        return new ContentRecyclerViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentRecyclerViewHoder holder, int position) {
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

    public class ContentRecyclerViewHoder extends RecyclerView.ViewHolder{
        public TextView txtName, tvContent;
        public ContentRecyclerViewHoder(@NonNull View view) {
            super(view);
            this.txtName = (TextView) view.findViewById(R.id.textView3);
            this.tvContent = (TextView) view.findViewById(R.id.tvContentChapter);
            this.setIsRecyclable(false);
        }
    }

    @SuppressLint("SetTextI18n")
    public void bind(@NonNull ContentChapRecyclerviewAdapter.ContentRecyclerViewHoder viewHolder, int i){
        ChapterModel chapter = chapterArrayList.get(i);
        viewHolder.txtName.setText("Chapter "+(i+1)+": "+ chapter.getTitle());
        viewHolder.tvContent.setText(chapter.getContent());
    }
    public void setOnClickItemRecyclerView(ItemClickInterface itemRecyclerView){
        itemClickInterface = itemRecyclerView;
    }

    public void updateChange(ArrayList<ChapterModel> data) {
        chapterArrayList = data;
        notifyDataSetChanged();
    }
}
