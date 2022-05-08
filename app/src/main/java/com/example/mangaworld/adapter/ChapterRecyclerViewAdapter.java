package com.example.mangaworld.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.model.Chapter;
import com.example.mangaworld.model.Manga;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChapterRecyclerViewAdapter extends RecyclerView.Adapter<ChapterRecyclerViewAdapter.ChapterRecyclerViewHoder> {
    private ArrayList<Chapter> chapterArrayList = new ArrayList<>();
    private ItemClickInterface itemClickInterface;

    public ChapterRecyclerViewAdapter(ArrayList<Chapter> chapterArrayList) {
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
        public Button txtLock;
        public ConstraintLayout bgChapter;
        public ChapterRecyclerViewHoder(@NonNull View view) {
            super(view);
            this.txtName = (TextView) view.findViewById(R.id.textView11);
            this.txtLock = (Button) view.findViewById(R.id.txtLock);
            this.bgChapter = (ConstraintLayout) view.findViewById(R.id.bgChapter);
            this.setIsRecyclable(false);
        }
    }

    @SuppressLint("SetTextI18n")
    public void bind(@NonNull ChapterRecyclerViewAdapter.ChapterRecyclerViewHoder viewHolder, int i){
        Chapter chapter = chapterArrayList.get(i);
        viewHolder.txtName.setText("Chapter "+String.valueOf(chapter.getIdChapter())+": "+ chapter.getChapter_title());
        if (chapter.getLock()){
            viewHolder.txtLock.setText("1");
        }
        else {
            viewHolder.txtLock.setText("0");
        }
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

    public void updateChange(ArrayList<Chapter> data) {
        chapterArrayList = data;
        notifyDataSetChanged();
    }
}
