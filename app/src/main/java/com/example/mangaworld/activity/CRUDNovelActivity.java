package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.NovelItemAdapter;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.NovelModel;

import java.util.ArrayList;

public class CRUDNovelActivity extends AppCompatActivity {

    EditText txtNovelID, txtNovelTitle, txtNovelDesc;
    Spinner spNovelAuthor;
    Button btnNovelCommit;
    ImageButton ibtnNovelImgPicker;
    SearchView searchNovel;

    View decorView;
    ListView lvNovel;

    ArrayList<NovelModel> novelData = new ArrayList<>();
    NovelController novelController;

    NovelItemAdapter novelItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudnovel);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //
    }

    private void setControl() {
        //
    }
}