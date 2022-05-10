package com.example.mangaworld.activity;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class CRUDChapter extends AppCompatActivity {
    EditText edIDChapter, edName, edContent;
    Button btnCommit;
    ListView listView;
    SearchView searchView;
    Spinner spinnerBook;
//    ChapterModel chapter = new ChapterModel();
//
//    SpinnerAdapter adapterBook;
//    ArrayList<String> listBook;
//
//    ArrayList<ChapterModel> chapterData = new ArrayList<>();
//    ChapterDAO chapterDAO = new ChapterDAO();
//    ChapterAdapter chapterAdapter;
//    boolean updateFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudchapter);
//        setControls();
//        setEvents();
//        refreshListView();
    }

//    private void setEvents() {
//        listView.setAdapter(chapterAdapter);
//        btnCommit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                chapter.setIDChapter(edIDChapter.getText().toString());
//                chapter.setName(edName.getText().toString());
//                chapter.setContent(edContent.getText().toString());
//                String temp = spinnerBook.getSelectedItem().toString();
//                chapter.setIDBook(chapterDAO.getIDbyName(temp));
//                for (ChapterModel chapterModel : chapterData) {
//                    if (chapter.getIDChapter().equalsIgnoreCase(chapterModel.getIDChapter())) {
//                        updateFlag = true;
//                        break;
//                    }
//                }
//
//                if (updateFlag == true) {
//                    chapterDAO.updateChapter(chapter);
//                    Toast.makeText(CRUDChapter.this, "Update success" + chapter.getName(), Toast.LENGTH_SHORT).show();
//                } else {
//                    if(chapterDAO.addChapter(chapter)==1)
//                    {
//                        Toast.makeText(CRUDChapter.this, "Add success" + chapter.getName(), Toast.LENGTH_SHORT).show();
//                    }else
//                    {
//                        Toast.makeText(CRUDChapter.this, "Add Fail", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                updateFlag = false;
//                refreshListView();
//            }
//        });
//    }
//
//    private void setControls() {
//        edIDChapter =findViewById(R.id.edChapterID);
//        edName =findViewById(R.id.edChapterName);
//        edContent =findViewById(R.id.edChapterContent);
//        spinnerBook = findViewById(R.id.spinnerBooks);
//
//        searchView =findViewById(R.id.searchChapter);
//        btnCommit = findViewById(R.id.btnCommitChapter);
//        listView = findViewById(R.id.lvChapter);
//
//        listBook = getBookList();
//        adapterBook = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listBook);
//        spinnerBook.setAdapter(adapterBook);
//        chapterAdapter = new ChapterAdapter(this, R.layout.layout_item_chapter, chapterData);
//    }
//
//    private ArrayList<String> getBookList() {
//        HashMap<String,String> map = chapterDAO.getAllBookTitle();
//        Collection<String> values = map.values();
//        ArrayList<String> list = new ArrayList<>(values);
//        return list;
//    }
//
//
//    public void loadData(ChapterModel chapter) {
//        edIDChapter.setText(chapter.getIDChapter());
//        edName.setText(chapter.getName());
//        edContent.setText(chapter.getContent());
//
//        HashMap<String,String> listBook;
//        ChapterDAO chapterDAO = new ChapterDAO();
//        listBook = chapterDAO.getAllBookTitle();
//        spinnerBook.setSelection(getIndex(spinnerBook,listBook.get(chapter.getIDBook())));
//    }
//
//    public static int getIndex(Spinner spinner,String s)
//    {
//        for(int i=0; i< spinner.getCount();i++)
//        {
//            if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s))
//            {
//                return i;
//            }
//        }
//        return 0;
//    }
//    public void refreshListView()
//    {
//        chapterData.clear();
//        chapterData.addAll(chapterDAO.getAllChapter());
//        chapterAdapter.notifyDataSetChanged();
//    }
}