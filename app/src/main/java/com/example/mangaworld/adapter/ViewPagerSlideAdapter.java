package com.example.mangaworld.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.model.Manga;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewPagerSlideAdapter extends RecyclerView.Adapter<ViewPagerSlideAdapter.PhotoViewHoder>{

    private ItemClickInterface itemClickInterface;
    private ArrayList<Manga> slideArrayList;

    public ViewPagerSlideAdapter(ArrayList<Manga> slideArrayList) {
        this.slideArrayList = slideArrayList;
    }

    @NonNull
    @Override
    public PhotoViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide, parent, false);
        return new PhotoViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHoder holder, @SuppressLint("RecyclerView") int position) {
        Manga slide = slideArrayList.get(position);
        Picasso.get()
                .load(slideArrayList.get(position).getLink())
                .resize(100, 100)
                .centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(holder.imageView);
        holder.textView.setText(slide.getTitle());
        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickInterface.onClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return slideArrayList.size();
    }

    public class PhotoViewHoder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public FrameLayout frameLayout;
        public PhotoViewHoder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgTitle);
            textView = itemView.findViewById(R.id.tvTitle);
            frameLayout = itemView.findViewById(R.id.frameSlide);
        }
    }

    public void setOnClickItemRecyclerView(ItemClickInterface itemRecyclerView){
        itemClickInterface = itemRecyclerView;
    }
}
