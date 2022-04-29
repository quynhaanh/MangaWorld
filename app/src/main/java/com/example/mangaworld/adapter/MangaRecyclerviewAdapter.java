package com.example.mangaworld.adapter;

import android.content.Context;
import android.provider.ContactsContract;
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

public class MangaRecyclerviewAdapter extends RecyclerView.Adapter<MangaRecyclerviewAdapter.MangaRecyclerViewHoder>{
    private ArrayList<Manga> mangaArrayList = new ArrayList<>();

    public MangaRecyclerviewAdapter(ArrayList<Manga> mangaArrayList) {
        this.mangaArrayList = mangaArrayList;
    }

    private ItemClickInterface itemClickInterface;

    @NonNull
    @Override
    public MangaRecyclerViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manga, parent, false);
        return new MangaRecyclerViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaRecyclerViewHoder holder, int position) {
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

    public class MangaRecyclerViewHoder extends RecyclerView.ViewHolder{
        public TextView tvName, tvPrice, tvGenge, tvBuy;
        public ImageView imageView;
        public MangaRecyclerViewHoder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imgManga);
            this.tvName = (TextView) view.findViewById(R.id.tvName);
            this.tvGenge = (TextView) view.findViewById(R.id.tvGenre);
            this.tvPrice = (TextView) view.findViewById(R.id.txtPrice);
            this.tvBuy = (TextView) view.findViewById(R.id.tvBuy);
            this.setIsRecyclable(false);
        }
    }
    public void bind(@NonNull MangaRecyclerviewAdapter.MangaRecyclerViewHoder viewHolder, int i){
        Manga manga = mangaArrayList.get(i);
        Picasso.get()
                .load(manga.getLink())
                .resize(100, 100)
                .centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(viewHolder.imageView);
        viewHolder.tvName.setText(manga.getTitle());
        viewHolder.tvPrice.setText(String.valueOf(manga.getPrice())+" view");
        viewHolder.tvGenge.setText(manga.getGenre());
        viewHolder.tvBuy.setOnClickListener(new View.OnClickListener() {
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
