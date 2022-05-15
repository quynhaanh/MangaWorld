package com.example.mangaworld.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class YourNovelAddActivity extends AppCompatActivity {

    EditText txtYourNovelTitle, txtYourNovelDesc;
    TextView tvMultiSelectGenre;
    AutoCompleteTextView txtYourNovelAuthor;
    Button btnYourNovelConfirm;
    FloatingActionButton fbtnYourNovelToDetails;
    ImageButton ibtnYourNovelImgPicker;
    ListView lvYourNovelChapter;
    ImageView ivCover;

    boolean[] genreSelected;

    ArrayList<Integer> genreList = new ArrayList<>();
    String[] genreNameMultiBoxArray;
    Map<String, Integer> genreMap = new HashMap<>();

    ArrayList<NovelModel> novelData = new ArrayList<>();
    ArrayList<AuthorModel> authorData = new ArrayList<>();
    Map<String, Integer> authorMap = new HashMap<>();
    String[] arrAuthName;
    ArrayList<ChapterModel> chapterData = new ArrayList<>();
    ArrayList<GenreModel> genreData = new ArrayList<>();

    NovelController novelController;
    AuthorController authorController;
    ChapterController chapterController;
    GenreController genreController;
    NovelGenresController novelGenresController;

    ChapterItemAdapter chapterItemAdapter;

    NovelModel loadNovel;

    int novelID;

    String url = LoadActivity.url;

    int authorID;
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
        novelID = getIntent().getIntExtra("idNovel", -1);
        if (novelID != -1) {
            loadNovelData();
        } else {
            getGenreList();
        }

        getNovelList();

        fbtnYourNovelToDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YourNovelAddActivity.this, YourNovelDetailsActivity.class);
                intent.putExtra("idNovel", novelID);
                startActivity(intent);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(YourNovelAddActivity.this);
                //Set title
                builder.setTitle("Chọn thể loại");
                //Set dialog non cancelable
                builder.setCancelable(false);
                builder.setMultiChoiceItems(genreNameMultiBoxArray, genreSelected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        //Check condition
                        if (b) {
                            //when box checked, add to list and sort
                            genreList.add(i);
                            Collections.sort(genreList);
                        } else {
                            genreList.remove(Integer.valueOf(i));
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j = 0; j < genreList.size(); j++) {
                            //combine array value
                            stringBuilder.append(genreNameMultiBoxArray[genreList.get(j)]);
                            //Check condition
                            if (j != genreList.size() - 1) {
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
                        for (int j = 0; j < genreSelected.length; j++) {
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

        lvYourNovelChapter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void setControl() {
        authorController = new AuthorController(url, this);

        novelController = new NovelController(url, this);

        chapterController = new ChapterController(url, this);

        genreController = new GenreController(url, this);

        novelGenresController = new NovelGenresController(url, this);

        txtYourNovelTitle = findViewById(R.id.txtYourNovelTitle);
        txtYourNovelDesc = findViewById(R.id.txtYourNovelDesc);
        txtYourNovelAuthor = findViewById(R.id.txtYourNovelAuthor);

        tvMultiSelectGenre = findViewById(R.id.tvMultiSelectGenre);

        btnYourNovelConfirm = findViewById(R.id.btnYourNovelConfirm);

        fbtnYourNovelToDetails = findViewById(R.id.fbtnYourNovelToDetails);

        ibtnYourNovelImgPicker = findViewById(R.id.ibtnYourNovelImgPicker);

        ivCover = findViewById(R.id.imgYourNovelCover);

        lvYourNovelChapter = findViewById(R.id.lvYourNovelChapter);
        chapterController = new ChapterController(url, this);
        chapterItemAdapter = new ChapterItemAdapter(YourNovelAddActivity.this,
                R.layout.layout_item_chapter, chapterData, url);
        lvYourNovelChapter.setAdapter(chapterItemAdapter);
    }


    private void writeNovelData() {
        authorName = txtYourNovelAuthor.getText().toString().trim();
        authorID = authorData.get(authorData.size() - 1).getId() + 1;
        updateFlagAuth = true;

        if (authorMap.containsKey(authorName)) {
            authorID = authorMap.get(authorName);
            updateFlagAuth = false;
        }

        if (updateFlagAuth) {
            AuthorModel authorModel = new AuthorModel();
            authorModel.setId(authorID);
            authorModel.setName(authorName);

            authorController.insertAuthor(authorModel, new IVolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    writeData();
                }
            });
        } else {
            writeData();
        }
    }

    private void writeData() {
        NovelModel novel = new NovelModel();

        novel.setId(novelID);
        novel.setTitle(txtYourNovelTitle.getText().toString().trim());
        novel.setIdAuthor(authorID);
        novel.setCover(novel.getId() + "-Cover");
        novel.setDescription(txtYourNovelDesc.getText().toString());
        novel.setIdUser(MainActivity.loggedUser.getId());
        novel.setViewCount(0);

        bitmap = ((BitmapDrawable) ivCover.getDrawable()).getBitmap();
        if (bitmap == null) {
            Toast.makeText(YourNovelAddActivity.this, "Vui lòng chọn ảnh bìa!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            novel.setCoverImageData(imageToString(bitmap));
        }

        try {
            if (updateFlag) {
                novelController.updateNovel(novel, new IVolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        NovelGenresController controller = new NovelGenresController(url, YourNovelAddActivity.this);
                        controller.deleteGenresByIDNovel(novelID, new IVolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                insertNovelGenres(novel.getId());
                            }
                        });
                    }
                });
            } else {
                novelController.insertNovel(novel, new IVolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        insertNovelGenres(novel.getId());
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertNovelGenres(int idNovel) {
        String temp = tvMultiSelectGenre.getText().toString();
        String[] values = temp.split(",");
        for (int i=0; i < values.length; i++) {
            NovelGenresModel novelGenresModel = new NovelGenresModel();
            novelGenresModel.setNovelID(idNovel);
            novelGenresModel.setGenreID(genreMap.get(values[i].trim()));

            int finalI = i;
            NovelGenresController controller = new NovelGenresController(url, this);
            controller.insertNovelGenre(novelGenresModel, new IVolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    if (finalI == values.length - 1)
                        finish();
                }
            });
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
                ivCover.setImageBitmap(bitmap);
                ivCover.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadNovelData() {
        updateFlag = true;
        novelController.getNovelByID(novelID, new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                String imageUrl = url + "/api/truyenchu/images/";

                loadNovel = novelController.convertJSONNovel(result);

                txtYourNovelTitle.setText(loadNovel.getTitle());
                txtYourNovelDesc.setText(loadNovel.getDescription());
                Picasso.get()
                        .load(imageUrl + loadNovel.getCover() + ".jpg")
                        .resize(600, 800)
                        .centerCrop()
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                        .into(ivCover);
                ivCover.setVisibility(View.VISIBLE);

                getAuthorList();
                getGenreList();
            }
        });
        getChapterListByNovelId();
    }

    private String getAuthorNameById(int id) {
        String authorName = "";

        for (Map.Entry<String, Integer> entry : authorMap.entrySet()) {
            if (entry.getValue() == id) {
                authorName = entry.getKey();
                break;
            }
        }
        return authorName;
    }

    private void getAuthorList() {
        authorController.getAuthors(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                authorData.clear();
                authorData.addAll(authorController.convertJSONData(result));
                for (AuthorModel iAuthor : authorData) {
                    authorMap.put(iAuthor.getName(), iAuthor.getId());
                }

                authorName = getAuthorNameById(loadNovel.getIdAuthor());
                txtYourNovelAuthor.setText(authorName);
            }
        });
    }

    private void getGenreByIDNovel() {
        genreController.getGenreByNovelID(loadNovel.getId(), new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                ArrayList<GenreModel> tmp = genreController.convertJSONData(result);
                String genresStr = "";
                for (int i = 0; i < tmp.size(); i++) {
                    genresStr += tmp.get(i).getName();

                    for (int j = 0; j < genreNameMultiBoxArray.length; j++) {
                        if (genreNameMultiBoxArray[j].equalsIgnoreCase(tmp.get(i).getName())) {
                            genreList.add(j);
                            Collections.sort(genreList);
                            genreSelected[j] = true;
                            break;
                        }
                    }

                    if (i < tmp.size() - 1) {
                        genresStr += ", ";
                    }
                }

                tvMultiSelectGenre.setText(genresStr);
            }
        });
    }

    private void getNovelList() {
        novelController.getNovel(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                novelData.clear();
                novelData.addAll(novelController.convertJSONData(result));
                if (!updateFlag) {
                    novelID = novelData.get(novelData.size() - 1).getId() + 1;
                }
            }
        });
    }

    private void getGenreList() {
        // Chạy bất đồng bộ
        // Không thể xử lý array ở ngoài onSuccess
        genreController.getGenres(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                genreData.clear();
                genreData.addAll(genreController.convertJSONData(result));

                for (GenreModel iGenre : genreData) {
                    genreMap.put(iGenre.getName(), iGenre.getId());
                }

                genreNameMultiBoxArray = new String[genreMap.size()];
                genreMap.keySet().toArray(genreNameMultiBoxArray);
                genreSelected = new boolean[genreNameMultiBoxArray.length];

                if (updateFlag) {
                    getGenreByIDNovel();
                }
            }
        });
    }

    public void getChapterListByNovelId() {
        chapterController.getChaptersByNovelId(novelID, new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                chapterData.clear();
                chapterData.addAll(chapterController.convertJSONData(result));
                chapterItemAdapter.notifyDataSetChanged();
            }
        });
    }
}