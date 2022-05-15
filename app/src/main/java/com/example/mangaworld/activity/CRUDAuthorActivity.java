package com.example.mangaworld.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

//import com.example.crudappdoctruyen.Adapter.AuthorAdapter;
//import com.example.crudappdoctruyen.DAO.AuthorDAO;
//import com.example.crudappdoctruyen.Model.AuthorModel;
//import com.example.crudappdoctruyen.R;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.AuthorItemAdapter;
import com.example.mangaworld.adapter.GenreItemAdapter;
import com.example.mangaworld.controller.AuthorController;
import com.example.mangaworld.controller.GenreController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.model.AuthorModel;
import com.example.mangaworld.model.GenreModel;

import java.util.ArrayList;

public class CRUDAuthorActivity extends AppCompatActivity {
    EditText edID, edName;
    ImageButton btnCommit;
    ListView listView;
    SearchView searchView;

    AuthorController authorController;
    ArrayList<AuthorModel> authordata = new ArrayList<>();
    AuthorItemAdapter authorItemAdapter;

    String url = LoadActivity.url;
    boolean updateFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudauthor);
        setControls();
        setEvents();
        refreshListView();

    }
    private void setControls()
    {
        edID =findViewById(R.id.edAuthorID);
        edName =findViewById(R.id.edAuthorName);

        searchView =findViewById(R.id.searchAuthor);
        btnCommit = findViewById(R.id.btnCommitAuthor);
        listView = findViewById(R.id.lvAuthor);

        authorController = new AuthorController(url, this);
        authorItemAdapter = new AuthorItemAdapter(CRUDAuthorActivity.this, R.layout.layout_item_author, authordata, url);
    }
    private void setEvents() {

        listView.setAdapter(authorItemAdapter);

        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthorModel author = new AuthorModel();
                author.setId(Integer.parseInt(edID.getText().toString()));
                author.setName(edName.getText().toString());

                for (AuthorModel authorModel : authordata) {
                    if (authorModel.getId()==author.getId()) {
                        updateFlag = true;
                        break;
                    }
                }
                if (updateFlag == true) {
                    authorController.updateAuthor(author, new IVolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            refreshListView();
                        }
                    });
                } else {
                    authorController.insertAuthor(author, new IVolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            refreshListView();
                        }
                    });
                }

                updateFlag = false;


            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                authorItemAdapter.search(newText);
                return false;
            }
        });
    }
    public void loadData(AuthorModel author) {
        edID.setText(author.getId());
        edName.setText(author.getName());

    }
    public void refreshListView()
    {
        authorController.getAuthors(new IVolleyCallback() {
        @Override
        public void onSuccess(String result) {
            authordata.clear();
            authordata.addAll(authorController.convertJSONData(result));
            authorItemAdapter.notifyDataSetChanged();
        }
    });
    }


    public void closeActivity(View view) {
        finish();
    }
}