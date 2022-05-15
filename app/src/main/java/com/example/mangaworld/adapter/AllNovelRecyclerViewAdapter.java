package com.example.mangaworld.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.LoadActivity;
import com.example.mangaworld.controller.GenreController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.model.GenreModel;
import com.example.mangaworld.model.NovelModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AllNovelRecyclerViewAdapter extends RecyclerView.Adapter <AllNovelRecyclerViewAdapter.AllMangaRecyclerViewHoder> {
    private ArrayList<NovelModel> novelArrayList = new ArrayList<>();
    private ItemClickInterface itemClickInterface;
    String imageUrl = LoadActivity.url + "/api/truyenchu/images/";
    Activity activity;

    public AllNovelRecyclerViewAdapter(ArrayList<NovelModel> mangaArrayList, Activity activity) {
        this.novelArrayList = mangaArrayList;
        this.activity = activity;
    }

    public NovelModel getAtPosition(int position){
        return novelArrayList.get(position);
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
        if(novelArrayList.size()>0){
            return novelArrayList.size();
        }
        else {
            return 0;
        }
    }

    public class AllMangaRecyclerViewHoder extends RecyclerView.ViewHolder{
        public TextView txtName, txtView, tvGenreAllNovel, tvDate;
        public ImageView imgAllNovel;
        public ConstraintLayout bgAllNovel;

        public AllMangaRecyclerViewHoder(@NonNull View view) {
            super(view);
            this.imgAllNovel = (ImageView) view.findViewById(R.id.imgAllNovel);
            this.txtName = (TextView) view.findViewById(R.id.txtNameAll);
            this.txtView = (TextView) view.findViewById(R.id.txtViewAll);
            this.bgAllNovel = (ConstraintLayout) view.findViewById(R.id.bgAllNovel);
            this.tvGenreAllNovel = (TextView) view.findViewById(R.id.tvGenreAllNovel);
            this.tvDate = view.findViewById(R.id.tvDatePostAllNovel);
            this.setIsRecyclable(false);
        }
    }
    public void bind(@NonNull AllNovelRecyclerViewAdapter.AllMangaRecyclerViewHoder viewHolder, int i){
        NovelModel novel = novelArrayList.get(i);
        Picasso.get()
                .load(imageUrl + novel.getCover() + ".jpg")
                .resize(500, 800)
                .centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(viewHolder.imgAllNovel);
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
                viewHolder.tvGenreAllNovel.setText(genreStr);
            }
        });

        viewHolder.bgAllNovel.setOnClickListener(new View.OnClickListener() {
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
        novelArrayList = data;
        notifyDataSetChanged();
    }
}
