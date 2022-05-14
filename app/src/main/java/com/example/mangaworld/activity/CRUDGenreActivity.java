package com.example.mangaworld.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.GenreItemAdapter;
import com.example.mangaworld.controller.GenreController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.model.GenreModel;

import java.util.ArrayList;

public class CRUDGenreActivity extends AppCompatActivity {
    EditText edID, edName;
    Button btnCommit;
    ListView listView;
    SearchView searchView;

    GenreController genreController;
    ArrayList<GenreModel> genreData = new ArrayList<>();
    GenreItemAdapter genreItemAdapter;

    String url = LoadActivity.url;
    boolean updateFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_genre);
        setControls();
        setEvents();
        refreshListView();
    }


    private void setControls() {
        edID = findViewById(R.id.edTypeID);
        edName = findViewById(R.id.edTypeName);

        searchView = findViewById(R.id.searchType);
        btnCommit = findViewById(R.id.btnCommitType);
        listView = findViewById(R.id.lvType);

        genreController = new GenreController(url, this);
        genreItemAdapter = new GenreItemAdapter(CRUDGenreActivity.this, R.layout.layout_item_genre, genreData, url);
    }

    private void setEvents() {
        listView.setAdapter(genreItemAdapter);

        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenreModel genre = new GenreModel();
                genre.setId(Integer.parseInt(edID.getText().toString()));
                genre.setName(edName.getText().toString());

                for (GenreModel typeModel : genreData) {
                    if (typeModel.getId()==genre.getId()) {
                        updateFlag = true;
                        break;
                    }
                }
                if (updateFlag == true) {
                    genreController.updateGenre(genre, new IVolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            refreshListView();
                        }
                    });
                } else {
                    genreController.insertGenre(genre, new IVolleyCallback() {
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

    public void loadData(GenreModel genre) {
        edID.setText(String.valueOf(genre.getId()));
        edName.setText(genre.getName());

    }

    public void refreshListView() {
        genreController.getGenres(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                genreData.clear();
                genreData.addAll(genreController.convertJSONData(result));
                genreItemAdapter.notifyDataSetChanged();
            }
        });
    }
    public void closeActivity(View view) {
        finish();
    }
}