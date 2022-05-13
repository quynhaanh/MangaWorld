package com.example.mangaworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.model.GenreModel;
import com.example.mangaworld.model.Manga;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllMangaRecyclerViewAdapter extends RecyclerView.Adapter <AllMangaRecyclerViewAdapter.AllMangaRecyclerViewHoder> {
    private ArrayList<Manga> mangaArrayList = new ArrayList<>();
    private ItemClickInterface itemClickInterface;

    public AllMangaRecyclerViewAdapter(ArrayList<Manga> mangaArrayList) {
        this.mangaArrayList = mangaArrayList;
    }

    public Manga getAtPosition(int position){
        return (Manga) mangaArrayList.get(position);
    }
    @NonNull
    @Override
    public AllMangaRecyclerViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_manga, parent, false);
        return new AllMangaRecyclerViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllMangaRecyclerViewHoder holder, int position) {
        bind(holder, position);
    }

    @Override
    public int getItemCount() {
        if(mangaArrayList.size()>0){
            return mangaArrayList.size();
        }
        else {
            return 0;
        }
    }

    public class AllMangaRecyclerViewHoder extends RecyclerView.ViewHolder{
        public TextView txtName, txt;
        public ImageView imgAllManga;
        public FrameLayout bgAllManga;
        public AllMangaRecyclerViewHoder(@NonNull View view) {
            super(view);
            this.imgAllManga = (ImageView) view.findViewById(R.id.imgAllManga);
            this.txtName = (TextView) view.findViewById(R.id.txtName);
            this.txt = (TextView) view.findViewById(R.id.txt);
            this.bgAllManga = (FrameLayout) view.findViewById(R.id.bgAllManga);
            this.setIsRecyclable(false);
        }
    }
    public void bind(@NonNull AllMangaRecyclerViewAdapter.AllMangaRecyclerViewHoder viewHolder, int i){
        Manga manga = mangaArrayList.get(i);
        Picasso.get()
                .load(manga.getLink())
                .resize(100, 100)
                .centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(viewHolder.imgAllManga);
        viewHolder.txtName.setText(manga.getTitle());
        viewHolder.txt.setText(String.valueOf(manga.getPrice())+" view");
        viewHolder.bgAllManga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickInterface.onClick(v, i);
            }
        });
    }
    public void setOnClickItemRecyclerView(ItemClickInterface itemRecyclerView){
        itemClickInterface = itemRecyclerView;
    }

    public void updateChange(ArrayList<Manga> data) {
        mangaArrayList = data;
        notifyDataSetChanged();
    }
}
