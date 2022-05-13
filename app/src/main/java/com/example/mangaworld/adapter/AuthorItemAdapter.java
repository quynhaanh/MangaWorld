package com.example.mangaworld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.CRUDAuthorActivity;
import com.example.mangaworld.activity.CRUDGenreActivity;
import com.example.mangaworld.controller.AuthorController;
import com.example.mangaworld.controller.GenreController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.model.AuthorModel;
import com.example.mangaworld.model.GenreModel;

import java.util.ArrayList;

public class AuthorItemAdapter extends ArrayAdapter {

    ArrayList<AuthorModel> data;
    ArrayList<AuthorModel> datatmp = new ArrayList<>();
    int resource;
    Context context;
    String url;
    public AuthorItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<AuthorModel> data, String url) {
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

        TextView tvID = convertView.findViewById(R.id.tvItemIDAuthor);
        TextView tvName = convertView.findViewById(R.id.tvItemNameAuthor);

        Button btnSua = convertView.findViewById(R.id.btnItemSuaAuthor);
        Button btnXoa = convertView.findViewById(R.id.btnItemXoaAuthor);

        AuthorModel author = data.get(position);
        tvID.setText(String.valueOf(author.getId()));
        tvName.setText(author.getName());

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CRUDAuthorActivity) context).loadData(author);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthorController authorDAO = new AuthorController(url, (CRUDAuthorActivity) context);
                authorDAO.deleteAuthor(author, new IVolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        ((CRUDAuthorActivity) context).refreshListView();
                    }
                });
            }
        });
        return convertView;
    }
}
