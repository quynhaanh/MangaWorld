package com.example.mangaworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.LoadActivity;
import com.example.mangaworld.model.NovelModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopNovelRecyclerViewAdapter extends RecyclerView.Adapter<TopNovelRecyclerViewAdapter.TopNovelViewHoder> {
    private ArrayList<NovelModel> novelArrayList = new ArrayList<>();
    String imageUrl = LoadActivity.url + "/api/truyenchu/images/";

    public TopNovelRecyclerViewAdapter(ArrayList<NovelModel> novelArrayList) {
        this.novelArrayList = novelArrayList;
    }

    private ItemClickInterface itemClickInterface;


    @NonNull
    @Override
    public TopNovelViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top, parent, false);
        return new TopNovelViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopNovelViewHoder holder, int position) {
        bind(holder, position);
    }

    @Override
    public int getItemCount() {
        if (novelArrayList.size() > 0) {
            return novelArrayList.size();
        } else {
            return 0;
        }
    }

    public class TopNovelViewHoder extends RecyclerView.ViewHolder {
        public TextView tvName, tvNumber;
        public ImageView imageView;

        public TopNovelViewHoder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imgTopTitle);
            this.tvName = (TextView) view.findViewById(R.id.tvTopTitle);
            this.tvNumber = (TextView) view.findViewById(R.id.tvTopNumber);
            this.setIsRecyclable(false);
        }
    }

    public void bind(@NonNull TopNovelViewHoder viewHolder, int i) {
        NovelModel novel = novelArrayList.get(i);
        Picasso.get()
                .load(imageUrl + novel.getCover() + ".jpg")
                .resize(1600, 900)
                .centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(viewHolder.imageView);
        viewHolder.tvName.setText(novel.getTitle());
        viewHolder.tvNumber.setText(String.valueOf(i + 1));
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickInterface.onClick(v, i);
            }
        });
    }

    public void setOnClickItemRecyclerView(ItemClickInterface itemRecyclerView) {
        itemClickInterface = itemRecyclerView;
    }
}
