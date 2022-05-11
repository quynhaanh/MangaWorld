package com.example.mangaworld.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.CRUDGenreActivity;
import com.example.mangaworld.controller.GenreController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.model.GenreModel;

import java.util.ArrayList;

public class GenreItemAdapter extends ArrayAdapter {
    ArrayList<GenreModel> data;
    ArrayList<GenreModel> datatmp = new ArrayList<>();
    int resource;
    Context context;
    String url;

    public GenreItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<GenreModel> data, String url) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
        this.url = url;
        datatmp.addAll(data);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView tvID = convertView.findViewById(R.id.tvItemIDType);
        TextView tvName = convertView.findViewById(R.id.tvItemNameType);

        Button btnSua = convertView.findViewById(R.id.btnItemSuaType);
        Button btnXoa = convertView.findViewById(R.id.btnItemXoaType);

        GenreModel genre = data.get(position);
        tvID.setText(String.valueOf(genre.getId()));
        tvName.setText(genre.getName());

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CRUDGenreActivity) context).loadData(genre);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenreController typeDAO = new GenreController(url, (CRUDGenreActivity) context);
                typeDAO.deleteGenre(genre, new IVolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        ((CRUDGenreActivity) context).refreshListView();
                    }
                });
            }
        });
        return convertView;
    }
}
