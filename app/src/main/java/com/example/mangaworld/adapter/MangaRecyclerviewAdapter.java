package com.example.mangaworld.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.LoadActivity;
import com.example.mangaworld.model.Manga;
import com.example.mangaworld.model.NovelModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MangaRecyclerviewAdapter extends RecyclerView.Adapter<MangaRecyclerviewAdapter.MangaRecyclerViewHoder>{
    private ArrayList<NovelModel> mangaArrayList = new ArrayList<>();
    String imageUrl = LoadActivity.url + "/api/truyenchu/images/";

    public MangaRecyclerviewAdapter(ArrayList<NovelModel> mangaArrayList) {
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
        public TextView tvName, tvPrice, tvGenge;
        public FrameLayout bg_item_manga;
        public ImageView imageView;
        public MangaRecyclerViewHoder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imgManga);
            this.tvName = (TextView) view.findViewById(R.id.tvName);
            this.tvGenge = (TextView) view.findViewById(R.id.tvGenre);
            this.tvPrice = (TextView) view.findViewById(R.id.txtPrice);
            this.bg_item_manga = (FrameLayout) view.findViewById(R.id.bg_item_manga);
            this.setIsRecyclable(false);
        }
    }
    public void bind(@NonNull MangaRecyclerviewAdapter.MangaRecyclerViewHoder viewHolder, int i){
        NovelModel manga = mangaArrayList.get(i);
        Picasso.get()
                .load(imageUrl + manga.getCover() + ".jpg")
                .resize(1000, 1000)
                .centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(viewHolder.imageView);
        viewHolder.tvName.setText(manga.getTitle());
        viewHolder.tvPrice.setText(manga.getViewCount() + " lượt xem");
//        viewHolder.tvGenge.setText(manga.getGenre());
        viewHolder.bg_item_manga.setOnClickListener(new View.OnClickListener() {
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
