package com.example.mangaworld.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.CRUDNovelActivity;
import com.example.mangaworld.activity.LoadActivity;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.activity.YourNovelActivity;
import com.example.mangaworld.activity.YourNovelAddActivity;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.NovelModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NovelItemAdapter extends ArrayAdapter<NovelModel> {
    Context context;
    int resource;
    ArrayList<NovelModel> objects;
    ArrayList<NovelModel> objectsTemp = new ArrayList<>();

    String url;

    public NovelItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<NovelModel> objects, String url) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.url = url;
        objectsTemp.addAll(objects);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);

        ImageView ivNovelCover = convertView.findViewById(R.id.ivNovelCover);

        TextView tvNovelID = convertView.findViewById(R.id.tvNovelID);
        TextView tvNovelTitle = convertView.findViewById(R.id.tvNovelTitle);

        Button btnNovelUpdate = convertView.findViewById(R.id.btnNovelUpdate);
        Button btnNovelDel = convertView.findViewById(R.id.btnNovelDel);
        //Button btnNovelDetail = convertView.findViewById(R.id.btnNovelDetail);

        NovelModel novel = objects.get(position);

        Picasso.get().load(LoadActivity.url + "/api/truyenchu/images/" +
                novel.getCover() + ".jpg").resize(100,100).into(ivNovelCover);

        tvNovelID.setText(String.valueOf(novel.getId()));
        tvNovelTitle.setText(novel.getTitle());

        btnNovelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((YourNovelAddActivity)context).loadBookData(novel);
            }
        });

        btnNovelDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NovelController novelController = new NovelController(url, (YourNovelActivity) context);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Xóa truyện");
                builder.setMessage("Bạn có chắc muốn xóa " + novel.getTitle() + "?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Del Novel on confirmation
                        novelController.deleteNovel(novel, new IVolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                if(MainActivity.loggedUser.getIdRole() == 1)
                                {
                                    // Open CRUD Novel
                                    ((CRUDNovelActivity)context).refreshListNovel();
                                    Toast.makeText(context, "Xóa thành công " + novel.getTitle(),
                                            Toast.LENGTH_SHORT).show();
                                }
                                else if(MainActivity.loggedUser.getIdRole() == 2)
                                {
                                    ((YourNovelActivity)context).refreshListYourNovel();
                                    Toast.makeText(context, "Xóa thành công " + novel.getTitle(),
                                            Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(context, "Bro, what are you?",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        /*btnNovelDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder popUp = new AlertDialog.Builder(context);
                final View DetailPopUp = LayoutInflater.from(context).inflate(R.layout.activity_lv_detail_popup,null);
                ImageView imageBook1 = DetailPopUp.findViewById(R.id.imageBook1);
                TextView txtID = DetailPopUp.findViewById(R.id.txtID);
                TextView txtTitle = DetailPopUp.findViewById(R.id.txtBookTitle);
                TextView txtAuthor = DetailPopUp.findViewById(R.id.txtBookAuthor);
                TextView txtBookCover = DetailPopUp.findViewById(R.id.txtBookCover);
                TextView txtDesc = DetailPopUp.findViewById(R.id.txtDesc);

                Book book =objects.get(position);
                AuthorDAO authorDAO = new AuthorDAO();

                Picasso.get().load(book.getBookCover()).resize(500,800).into(imageBook1);
                //Toast.makeText(context, imageBook1.getWidth()+" "+imageBook1.getHeight(), Toast.LENGTH_SHORT).show();
                txtID.setText(book.getID());
                txtTitle.setText(book.getTitle());
                txtAuthor.setText(authorDAO.getAuthorByID(book.getAuthorID()).getAuthorName());
                txtBookCover.setText(book.getBookCover());
                txtDesc.setText(book.getShortDesc());

                popUp.setView(DetailPopUp);
                Dialog dialog = popUp.create();
                dialog.show();
            }
        });*/


        return convertView;
    }

    public void search(String searchString) {
        if(objectsTemp.size() < objects.size())
        {
            objectsTemp.clear();
            objectsTemp.addAll(objects);
        }
        objects.clear();
        searchString = searchString.toLowerCase();
        if (searchString.length()==0) {
            objects.addAll(objectsTemp);
        } else {
            for (NovelModel novel : objectsTemp) {
                if (novel.getId() == Integer.parseInt(searchString)
                        || novel.getTitle().toLowerCase().contains(searchString)) {
                    objects.add(novel);
                }
            }
        }
        notifyDataSetChanged();
    }
}
