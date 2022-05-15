package com.example.mangaworld.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.ChapterItemAdapter;
import com.example.mangaworld.adapter.GenreItemAdapter;
import com.example.mangaworld.controller.AuthorController;
import com.example.mangaworld.controller.ChapterController;
import com.example.mangaworld.controller.GenreController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.controller.NovelGenresController;
import com.example.mangaworld.model.AuthorModel;
import com.example.mangaworld.model.ChapterModel;
import com.example.mangaworld.model.GenreModel;
import com.example.mangaworld.model.NovelGenresModel;
import com.example.mangaworld.model.NovelModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class YourNovelAddActivity extends AppCompatActivity {

    EditText txtYourNovelTitle, txtYourNovelDesc;
    TextView tvMultiSelectGenre;
    AutoCompleteTextView txtYourNovelAuthor;
    Button btnYourNovelConfirm;
    FloatingActionButton fbtnYourNovelToDetails;
    ImageButton ibtnYourNovelImgPicker;
    ListView lvYourNovelChapter;

    boolean[] genreSelected;

    ArrayList<Integer> genreList = new ArrayList<>();
    String[] array;
    ArrayList<String> genreArray = new ArrayList<>();

    ArrayList<NovelModel> novelData = new ArrayList<>();
    ArrayList<AuthorModel> authorData = new ArrayList<>();
    ArrayList<String> authorNameData = new ArrayList<>();
    String[] arrAuthName;
    ArrayList<ChapterModel> chapterData = new ArrayList<>();
    ArrayList<GenreModel> genreData = new ArrayList<>();
    ArrayList<NovelGenresModel> novelGenresData = new ArrayList<>();

    NovelController novelController;
    AuthorController authorController;
    ChapterController chapterController;
    GenreController genreController;
    NovelGenresController novelGenresController;

    //NovelItemAdapter novelItemAdapter;
    ChapterItemAdapter chapterItemAdapter;
    GenreItemAdapter genreItemAdapter;

    NovelModel loadNovel;
    AuthorModel author;

    Bundle bundle;
    int novelID;

    String url = LoadActivity.url;

    int authorID;
    int genreID;
    String authorName;
    boolean updateFlag = false;
    boolean updateFlagAuth = false;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_novel_add);
        setControl();
        setEvent();
    }

    private void setEvent() {

        lvYourNovelChapter.setAdapter(chapterItemAdapter);

        fbtnYourNovelToDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YourNovelAddActivity.this, YourNovelDetailsActivity.class));
            }
        });

        ibtnYourNovelImgPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        tvMultiSelectGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder( YourNovelAddActivity.this);
                //Set title
                builder.setTitle("Chọn thể loại");
                //Set dialog non cancelable
                builder.setCancelable(false);
                builder.setMultiChoiceItems(array, genreSelected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        //Check condition
                        if(b)
                        {
                            //when box checked, add to list and sort
                            genreList.add(i);
                            Collections.sort(genreList);
                        }
                        else
                        {
                            genreList.remove(i);
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for(int j =0;j< genreList.size();j++)
                        {
                            //combine array value
                            stringBuilder.append(array[genreList.get(j)]);
                            //Check condition
                            if(j!= genreList.size()-1)
                            {
                                stringBuilder.append(", ");

                            }
                            //Set text on Textview
                            tvMultiSelectGenre.setText(stringBuilder.toString());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //
                        for(int j =0;j<genreSelected.length;j++)
                        {
                            //Remove all selection
                            genreSelected[j] = false;
                            //clear list
                            genreList.clear();
                            //clear item value
                            tvMultiSelectGenre.setText("");
                        }
                    }
                });
                //show dialog
                builder.show();
            }
        });

        btnYourNovelConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeNovelData();
            }
        });

        lvYourNovelChapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(YourNovelAddActivity.this, YourNovelDetailsActivity.class);
                Bundle bundle = new Bundle();

                ChapterModel chapter = chapterData.get(position);

                bundle.putInt("idChapter", chapter.getId());
                bundle.putInt("idNovel", novelID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        if(bundle!=null)
        {
            novelID = bundle.getInt("idNovel");
            loadNovelData();
        }

        authorController = new AuthorController(url, this);
        getAuthorList();

        novelController = new NovelController(url, this);
        getNovelList();

        chapterController = new ChapterController(url, this);

        genreController = new GenreController(url, this);
        getGenreList();

        novelGenresController = new NovelGenresController(url, this);
        getNovelGenreList();

        txtYourNovelTitle = findViewById(R.id.txtYourNovelTitle);
        txtYourNovelDesc = findViewById(R.id.txtYourNovelDesc);
        txtYourNovelAuthor = findViewById(R.id.txtYourNovelAuthor);

        tvMultiSelectGenre = findViewById(R.id.tvMultiSelectGenre);

//        ArrayAdapter adapterNovelAuthor = new ArrayAdapter(this,
//                android.R.layout.simple_dropdown_item_1line,
//                arrAuthName);
//        txtYourNovelAuthor.setAdapter(adapterNovelAuthor);

        btnYourNovelConfirm = findViewById(R.id.btnYourNovelConfirm);

        fbtnYourNovelToDetails = findViewById(R.id.fbtnYourNovelToDetails);

        ibtnYourNovelImgPicker = findViewById(R.id.ibtnYourNovelImgPicker);

        lvYourNovelChapter = findViewById(R.id.lvYourNovelChapter);
        chapterController = new ChapterController(url, this);
        chapterItemAdapter = new ChapterItemAdapter(YourNovelAddActivity.this,
                R.layout.layout_item_chapter, chapterData, url);
    }


    private void writeNovelData()
    {
        authorName = txtYourNovelAuthor.getText().toString().trim();
        authorID = authorData.get(authorData.size()-1).getId()+1;

        author = new AuthorModel();
        author.setId(authorID);
        author.setName(authorName);

        for (AuthorModel authorModel : authorData) {
            if (authorModel.getName().equalsIgnoreCase(author.getName())) {
                authorID = authorModel.getId();
                updateFlagAuth = true;
                break;
            }
        }

        if(!updateFlagAuth)
        {
            authorController.insertAuthor(author, new IVolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d("insertAuthor", "1");
                    getAuthorList();
                }
            });
        }
        updateFlagAuth = false;

        NovelModel novel = new NovelModel();
        novel.setId(novelData.get(novelData.size()-1).getId() + 1);
        novel.setTitle(txtYourNovelTitle.getText().toString());
        novel.setIdAuthor(authorID);
        novel.setCover(novel.getId() + "-Cover");
        novel.setDescription(txtYourNovelDesc.getText().toString());
        //novel.setDatePost();
        novel.setIdUser(MainActivity.loggedUser.getId());
        novel.setCoverImageData(imageToString(bitmap));

        try {
            if (updateFlag == true) {
                novelController.updateNovel(novel, new IVolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(YourNovelAddActivity.this,
                                "Sửa thành công" + novel.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        getNovelList();
                    }
                });
            } else {
                novelController.insertNovel(novel, new IVolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(YourNovelAddActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        getNovelList();
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        updateFlag = false;

        String temp = tvMultiSelectGenre.getText().toString();
        String[] values = temp.split(",");
        temp = "";

        //NovelGenresModel novelGenresModel = new NovelGenresModel();
        //novelGenresModel.setNovelID(6);
        Log.d("Value size", values.length+"");
        for(String value : values)
        {

            //getGenreIdByName(value);

            for(GenreModel iGenre : genreData)
            {

                if(value.equalsIgnoreCase(iGenre.getName()))
                {
                    NovelGenresModel novelGenresModel = new NovelGenresModel();
                    novelGenresModel.setNovelID(novel.getId());
                    novelGenresModel.setGenreID(iGenre.getId());

                    novelGenresController.insertNovelGenre(novelGenresModel, new IVolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.d("insert Novel Genre", "1");
                        }
                    });
                    break;
                }
            }
        }
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteOut);
        byte[] imgBytes = byteOut.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
//                File f = new File(path + "");
//                String imageName = f.getName();
//                txtYourNovelCoverName.setText(imageName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadNovelData()
    {
        updateFlag = true;
        novelController.getNovelByID(novelID, new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                //novelData.add(novelController.convertJSONNovel(result));
                loadNovel = novelController.convertJSONNovel(result);
            }
        });
        authorName = getAuthorNameById(loadNovel.getIdAuthor());
        txtYourNovelTitle.setText(loadNovel.getTitle());
        txtYourNovelAuthor.setText(authorName);
        txtYourNovelDesc.setText(loadNovel.getDescription());
        getChapterListByNovelId(novelID);
    }

    private String getAuthorNameById(int id)
    {
        String authorName = "";
        for (AuthorModel author : authorData
        ) {
            if(id == author.getId())
            {
                authorName = author.getName();
            }
        }
        return authorName;
    }

    private void getAuthorList()
    {
        authorController.getAuthors(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                authorData.clear();
                authorData.addAll(authorController.convertJSONData(result));
                for (AuthorModel iAuthor : authorData)
                {
                    authorNameData.add(iAuthor.getName());
                }

                arrAuthName = new String[authorNameData.size()];
                authorNameData.toArray(arrAuthName);
            }
        });
    }

    private void getAuthorIdByName(String name)
    {
        authorController.getAuthors(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                authorData.clear();
                authorData.addAll(authorController.convertJSONData(result));

                for (AuthorModel iAuthor : authorData) {
                    if(iAuthor.getName().equals(name))
                    {
                        authorID = iAuthor.getId();
                        updateFlagAuth = false;
                        Log.d("forAuthorID" , authorID+"");
                        break;
                    }
                }
            }
        });
    }

    private void getNovelGenreList()
    {
        novelGenresController.getNovelGenres(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                novelGenresData.clear();
                novelGenresData.addAll(novelGenresController.convertJSONData(result));
            }
        });
    }

    private void getNovelList()
    {
        novelController.getNovel(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                novelData.clear();
                novelData.addAll(novelController.convertJSONData(result));
            }
        });
    }

    private void getGenreList()
    {
        // Chạy bất đồng bộ
        // Không thể xử lý array ở ngoài onSuccess
        genreController.getGenres(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                genreData.clear();
                genreData.addAll(genreController.convertJSONData(result));
                //genreItemAdapter.notifyDataSetChanged();
                for(GenreModel iGenre : genreData)
                {
                    genreArray.add(iGenre.getName());
                    Log.d("GenreArray #0",genreArray.get(0));
                }

                array = new String[genreArray.size()];
                genreArray.toArray(array);
                genreSelected = new boolean[array.length];
            }
        });
    }

    private void getGenreIdByName(String name)
    {
        genreController.getGenres(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                genreData.clear();
                genreData.addAll(genreController.convertJSONData(result));

                for(GenreModel iGenre : genreData)
                {
                    if(iGenre.getName().equalsIgnoreCase(name))
                    {
                        genreID = iGenre.getId();
                    }
                }
            }
        });
    }

    private void getChapterListByNovelId(int novelId)
    {
        chapterController.getChaptersByNovelId(novelId, new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                chapterData.clear();
                chapterData.addAll(chapterController.convertJSONData(result));
            }
        });
    }
}