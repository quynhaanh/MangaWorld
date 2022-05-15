package com.example.mangaworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.model.GenreModel;

import java.util.ArrayList;

public class GenreRecylerviewAdapter extends RecyclerView.Adapter<GenreRecylerviewAdapter.GenreRecyclerViewHoder>{
    private ArrayList<GenreModel> genreModelArrayList;
    private ItemClickInterface itemClickInterface;

    public GenreRecylerviewAdapter(ArrayList<GenreModel> genreModelArrayList) {
        this.genreModelArrayList = genreModelArrayList;
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

        public GenreRecyclerViewHoder(@NonNull View view) {
            super(view);
            this.cardViewItemGenre = (CardView) view.findViewById(R.id.cardViewItemGenre);
            this.tvName = (TextView) view.findViewById(R.id.tvNameGenre);
            this.setIsRecyclable(false);
        }
    }
    public void bind(@NonNull GenreRecyclerViewHoder viewHolder, int i){
        viewHolder.tvName.setText(genreModelArrayList.get(i).getName());
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
    public void updateChange(ArrayList<GenreModel> data) {
        genreModelArrayList = data;
        notifyDataSetChanged();
    }
}
