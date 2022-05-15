package com.example.mangaworld.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.AllMangaActivity;
import com.example.mangaworld.activity.LoadActivity;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.controller.GenreController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.model.GenreModel;
import com.example.mangaworld.model.NovelModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AllMangaRecyclerViewAdapter extends RecyclerView.Adapter <AllMangaRecyclerViewAdapter.AllMangaRecyclerViewHoder> {
    private ArrayList<NovelModel> mangaArrayList = new ArrayList<>();
    private ItemClickInterface itemClickInterface;
    String imageUrl = LoadActivity.url + "/api/truyenchu/images/";
    Activity activity;

    public AllMangaRecyclerViewAdapter(ArrayList<NovelModel> mangaArrayList, Activity activity) {
        this.mangaArrayList = mangaArrayList;
        this.activity = activity;
    }

    public NovelModel getAtPosition(int position){
        return mangaArrayList.get(position);
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
        public TextView txtName, txtView, tvGenreAllManga, tvDate;
        public ImageView imgAllManga;
        public ConstraintLayout bgAllManga;

        public AllMangaRecyclerViewHoder(@NonNull View view) {
            super(view);
            this.imgAllManga = (ImageView) view.findViewById(R.id.imgAllManga);
            this.txtName = (TextView) view.findViewById(R.id.txtNameAll);
            this.txtView = (TextView) view.findViewById(R.id.txtViewAll);
            this.bgAllManga = (ConstraintLayout) view.findViewById(R.id.bgAllManga);
            this.tvGenreAllManga = (TextView) view.findViewById(R.id.tvGenreAllManga);
            this.tvDate = view.findViewById(R.id.tvDatePostAllManga);
            this.setIsRecyclable(false);
        }
    }
    public void bind(@NonNull AllMangaRecyclerViewAdapter.AllMangaRecyclerViewHoder viewHolder, int i){
        NovelModel novel = mangaArrayList.get(i);
        Picasso.get()
                .load(imageUrl + novel.getCover() + ".jpg")
                .resize(500, 800)
                .centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(viewHolder.imgAllManga);
        viewHolder.txtName.setText(novel.getTitle());
        viewHolder.txtView.setText(novel.getViewCount() + " lượt xem");

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(novel.getDatePost());
            viewHolder.tvDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        } catch (ParseException e) {
            viewHolder.tvDate.setText("");
        }

        GenreController controller = new GenreController(LoadActivity.url, activity);
        controller.getGenreByNovelID(novel.getId(), new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                ArrayList<GenreModel> genreList = controller.convertJSONData(result);
                String genreStr = "";
                for (int i = 0; i < genreList.size(); i++) {
                    genreStr += genreList.get(i).getName();
                    if (i < genreList.size() - 1) {
                        genreStr += ", ";
                    }
                }
                viewHolder.tvGenreAllManga.setText(genreStr);
            }
        });

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

    public void updateChange(ArrayList<NovelModel> data) {
        mangaArrayList = data;
        notifyDataSetChanged();
    }
}
