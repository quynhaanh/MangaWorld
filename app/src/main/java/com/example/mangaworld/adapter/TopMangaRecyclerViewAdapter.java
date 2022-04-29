package com.example.mangaworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.model.Manga;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopMangaRecyclerViewAdapter extends RecyclerView.Adapter<TopMangaRecyclerViewAdapter.TopMangaViewHoder>{
    private ArrayList<Manga> mangaArrayList = new ArrayList<>();

    public TopMangaRecyclerViewAdapter(ArrayList<Manga> mangaArrayList) {
        this.mangaArrayList = mangaArrayList;
    }

    private ItemClickInterface itemClickInterface;


    @NonNull
    @Override
    public TopMangaViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top, parent, false);
        return new TopMangaViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopMangaViewHoder holder, int position) {
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

    public class TopMangaViewHoder extends RecyclerView.ViewHolder{
        public TextView tvName, tvNumber;
        public ImageView imageView;
        public TopMangaViewHoder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imgTopTitle);
            this.tvName = (TextView) view.findViewById(R.id.tvTopTitle);
            this.tvNumber = (TextView) view.findViewById(R.id.tvTopNumber);
            this.setIsRecyclable(false);
        }
    }
    public void bind(@NonNull TopMangaRecyclerViewAdapter.TopMangaViewHoder viewHolder, int i){
        Manga manga = mangaArrayList.get(i);
        Picasso.get()
                .load(manga.getLink())
                .resize(100, 100)
                .centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(viewHolder.imageView);
        viewHolder.tvName.setText(manga.getTitle());
        viewHolder.tvNumber.setText(String.valueOf(i+1)+" ");
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickInterface.onClick(v, i);
            }
        });
    }
    public void setOnClickItemRecyclerView(ItemClickInterface itemRecyclerView){
        itemClickInterface = itemRecyclerView;
    }
}
