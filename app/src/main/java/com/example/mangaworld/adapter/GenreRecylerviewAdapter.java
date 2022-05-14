package com.example.mangaworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.model.GenreModel;
import com.example.mangaworld.model.Manga;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GenreRecylerviewAdapter extends RecyclerView.Adapter<GenreRecylerviewAdapter.GenreRecyclerViewHoder>{
    private ArrayList<GenreModel> genreModelArrayList = new ArrayList<>();
    private ArrayList<String> urlImgBG = new ArrayList<>();
    private ItemClickInterface itemClickInterface;

    public GenreRecylerviewAdapter(ArrayList<GenreModel> genreModelArrayList, ArrayList<String> urlImgBG) {
        this.genreModelArrayList = genreModelArrayList;
        this.urlImgBG = urlImgBG;
    }

    public GenreModel getAtPosition(int position){
        return (GenreModel) genreModelArrayList.get(position);
    }

    @NonNull
    @Override
    public GenreRecyclerViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre, parent, false);
        return new GenreRecyclerViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreRecyclerViewHoder holder, int position) {
        bind(holder, position);
    }

    @Override
    public int getItemCount() {
        if(genreModelArrayList.size()>0){
            return genreModelArrayList.size();
        }
        else {
            return 0;
        }
    }

    public class GenreRecyclerViewHoder extends RecyclerView.ViewHolder{
        public TextView tvName;
        public CardView cardViewItemGenre;
        public ImageView imgBGGenre;
        public GenreRecyclerViewHoder(@NonNull View view) {
            super(view);
            this.cardViewItemGenre = (CardView) view.findViewById(R.id.cardViewItemGenre);
            this.tvName = (TextView) view.findViewById(R.id.tvNameGenre);
            this.imgBGGenre = (ImageView) view.findViewById(R.id.imgBGGenre);
            this.setIsRecyclable(false);
        }
    }
    public void bind(@NonNull GenreRecyclerViewHoder viewHolder, int i){
        viewHolder.tvName.setText(genreModelArrayList.get(i).getName());
        Picasso.get()
                .load(urlImgBG.get(i))
                .resize(4000, 3000)
                .centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(viewHolder.imgBGGenre);
        viewHolder.cardViewItemGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickInterface.onClick(v, i);
            }
        });
    }
    public void setOnClickItemRecyclerView(ItemClickInterface itemRecyclerView){
        itemClickInterface = itemRecyclerView;
    }
    public void updateChange(ArrayList<GenreModel> data, ArrayList<String> dataUrl) {
        genreModelArrayList = data;
        urlImgBG = dataUrl;
        notifyDataSetChanged();
    }
}
