package com.example.mangaworld.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.AuthorItemAdapter;
import com.example.mangaworld.adapter.ChapterItemAdapter;
import com.example.mangaworld.controller.AuthorController;
import com.example.mangaworld.controller.ChapterController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.AuthorModel;
import com.example.mangaworld.model.ChapterModel;
import com.example.mangaworld.model.NovelModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class CRUDChapterActivity extends AppCompatActivity {
    EditText edIDChapter, edName, edContent;
    Button btnCommit;
    ListView listView;
    SearchView searchView;
    Spinner spinnerBook;
    ChapterModel chapter;

    SpinnerAdapter adapterBook;
    ArrayList<NovelModel> listBook = new ArrayList<>();
    ArrayList<String> listTitle;


    ArrayList<ChapterModel> chapterData = new ArrayList<>();
    ChapterController chapterController;
    NovelController novelController;
    ChapterItemAdapter chapterItemAdapter;

    String url = LoadActivity.url;
    boolean updateFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudchapter);
        setControls();
        setEvents();
        refreshListView();
    }



    private void setControls() {
        edIDChapter =findViewById(R.id.edChapterID);
        edName =findViewById(R.id.edChapterName);
        edContent =findViewById(R.id.edChapterContent);
        spinnerBook = findViewById(R.id.spinnerChapterNovel);

        searchView =findViewById(R.id.searchChapter);
        btnCommit = findViewById(R.id.btnCommitChapter);
        listView = findViewById(R.id.lvChapter);

        chapterController= new ChapterController(url, this);
        chapterItemAdapter = new ChapterItemAdapter(CRUDChapterActivity.this, R.layout.layout_item_chapter, chapterData, url);

        listTitle = new ArrayList<>();
        adapterBook = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listTitle);
        novelController = new NovelController(url, this);
        novelController.getNovel(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                listBook.addAll(novelController.convertJSONData(result));
                for (NovelModel novel : listBook) {
                    String temp = String.valueOf(novel.getTitle());
                    listTitle.add(temp);
                }
                spinnerBook.setAdapter(adapterBook);
            }
        });

    }

    private void setEvents() {
        listView.setAdapter(chapterItemAdapter);
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chapter = new ChapterModel();
                chapter.setId(Integer.parseInt(edIDChapter.getText().toString()));
                chapter.setTitle(edName.getText().toString());
                chapter.setContent(edContent.getText().toString());

                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = formatter.format(currentTime);
                chapter.setDatePost(strDate);
//                Toast.makeText(CRUDChapterActivity.this, strDate, Toast.LENGTH_SHORT).show();
//                String temp = spinnerBook.getSelectedItem().toString();
//                chapter.setNovelID(Integer.parseInt(temp));
                Integer tempID =  listBook.get(spinnerBook.getSelectedItemPosition()).getId();
                chapter.setNovelID(tempID);

                for (ChapterModel chapterModel : chapterData) {
                    if (chapterModel.getId() == chapter.getId()) {
                        updateFlag = true;
                        break;
                    }
                }
                if (updateFlag == true) {
                    chapterController.updateChapter(chapter, new IVolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            refreshListView();
                        }
                    });
                } else {
                    chapterController.insertChapter(chapter, new IVolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            refreshListView();
                        }
                    });
                }

                updateFlag = false;


            }
        });
    }


    public void loadData(ChapterModel chapter) {
        edIDChapter.setText(String.valueOf(chapter.getId()));
        edName.setText(chapter.getTitle());
        edContent.setText(chapter.getContent());
        for(NovelModel novel : listBook)
        {
            if(novel.getId()==chapter.getNovelID()){
                spinnerBook.setSelection(getIndex(spinnerBook,novel.getTitle()));
            }
        }
    }

    public static int getIndex(Spinner spinner,String s)
    {
        for(int i=0; i< spinner.getCount();i++)
        {
            if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s))
            {
                return i;
            }
        }
        return 0;
    }
    public void refreshListView()
    {
        chapterController.getChapters(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                chapterData.clear();
                chapterData.addAll(chapterController.convertJSONData(result));
                chapterItemAdapter.notifyDataSetChanged();
            }
        });
    }
}

