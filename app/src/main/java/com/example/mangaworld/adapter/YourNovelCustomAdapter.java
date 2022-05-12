//package com.example.mangaworld.adapter;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.example.mangaworld.R;
//import com.example.mangaworld.activity.YourNovelActivity;
//import com.example.mangaworld.activity.YourNovelAddActivity;
//import com.example.mangaworld.controller.IVolleyCallback;
//import com.example.mangaworld.controller.NovelController;
//import com.example.mangaworld.model.NovelModel;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//public class YourNovelCustomAdapter extends ArrayAdapter<NovelModel> {
//    Context context;
//    int resource;
//    ArrayList<NovelModel> objects;
//    ArrayList<NovelModel> objectsTemp = new ArrayList<>();
//
//    String url;
//
//    public YourNovelCustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<NovelModel> objects) {
//        super(context, resource, objects);
//        this.context = context;
//        this.resource = resource;
//        this.objects = objects;
//        objectsTemp.addAll(objects);
//    }
//
//    @Override
//    public int getCount() {
//        return objects.size();
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        convertView = LayoutInflater.from(context).inflate(resource,null);
//
//        ImageView ivYourNovelCover = convertView.findViewById(R.id.ivYourNovelCover);
//
//        TextView tvYourNovelID = convertView.findViewById(R.id.tvYourNovelID);
//        TextView tvYourNovelTitle = convertView.findViewById(R.id.tvYourNovelTitle);
//
//        Button btnYourNovelAdd = convertView.findViewById(R.id.btnYourNovelAdd);
//        Button btnYourNovelDel = convertView.findViewById(R.id.btnYourNovelDel);
//        Button btnYourNovelDetail = convertView.findViewById(R.id.btnYourNovelDetail);
//
//        NovelModel novel =objects.get(position);
//
//        //Picasso.get().load(novel.getCover()).resize(100,100).into();
//
//        tvYourNovelID.setText(novel.getID());
//        tvYourNovelTitle.setText(novel.getTitle());
//
//
//        btnYourNovelAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //((YourNovelAddActivity)context).loadBookData(novel);
//            }
//        });
//
//        btnYourNovelDel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NovelController novelController = new NovelController(url, (YourNovelActivity) context);
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                builder.setTitle("Xóa truyện");
//                builder.setMessage("Bạn có chắc muốn xóa " + novel.getTitle() + "?");
//
//                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Del Novel on confirmation
//                        novelController.deleteNovel(novel, new IVolleyCallback() {
//                            @Override
//                            public void onSuccess(String result) {
//                                ((YourNovelActivity)context).refreshListView();
//                                Toast.makeText(context, "Xóa thành công " + novel.getTitle(),
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                        dialog.dismiss();
//                    }
//                });
//
//                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Do nothing
//                        dialog.dismiss();
//                    }
//                });
//
//                AlertDialog alert = builder.create();
//                alert.show();
//            }
//        });
//
//        btnYourNovelDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder popUp = new AlertDialog.Builder(context);
//                final View DetailPopUp = LayoutInflater.from(context).inflate(R.layout.activity_lv_detail_popup,null);
//                ImageView imageBook1 = DetailPopUp.findViewById(R.id.imageBook1);
//                TextView txtID = DetailPopUp.findViewById(R.id.txtID);
//                TextView txtTitle = DetailPopUp.findViewById(R.id.txtBookTitle);
//                TextView txtAuthor = DetailPopUp.findViewById(R.id.txtBookAuthor);
//                TextView txtBookCover = DetailPopUp.findViewById(R.id.txtBookCover);
//                TextView txtDesc = DetailPopUp.findViewById(R.id.txtDesc);
//
//                Book book =objects.get(position);
//                AuthorDAO authorDAO = new AuthorDAO();
//
//                Picasso.get().load(book.getBookCover()).resize(500,800).into(imageBook1);
//                //Toast.makeText(context, imageBook1.getWidth()+" "+imageBook1.getHeight(), Toast.LENGTH_SHORT).show();
//                txtID.setText(book.getID());
//                txtTitle.setText(book.getTitle());
//                txtAuthor.setText(authorDAO.getAuthorByID(book.getAuthorID()).getAuthorName());
//                txtBookCover.setText(book.getBookCover());
//                txtDesc.setText(book.getShortDesc());
//
//                popUp.setView(DetailPopUp);
//                Dialog dialog = popUp.create();
//                dialog.show();
//            }
//        });
//
//
//        return convertView;
//    }
//
//    public void search(String searchString) {
//        if(objectsTemp.size() < objects.size())
//        {
//            objectsTemp.clear();
//            objectsTemp.addAll(objects);
//        }
//        objects.clear();
//        searchString = searchString.toLowerCase();
//        if (searchString.length()==0) {
//            objects.addAll(objectsTemp);
//        } else {
//            for (Book bk : objectsTemp) {
//                if (bk.getID().toLowerCase().contains(searchString) || bk.getTitle().toLowerCase().contains(searchString)) {
//                    objects.add(bk);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
//}
