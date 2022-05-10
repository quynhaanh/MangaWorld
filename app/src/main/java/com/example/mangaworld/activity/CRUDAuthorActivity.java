package com.example.mangaworld.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

//import com.example.crudappdoctruyen.Adapter.AuthorAdapter;
//import com.example.crudappdoctruyen.DAO.AuthorDAO;
//import com.example.crudappdoctruyen.Model.AuthorModel;
//import com.example.crudappdoctruyen.R;

import com.example.mangaworld.R;

import java.util.ArrayList;

public class CRUDAuthorActivity extends AppCompatActivity {
    EditText edID, edName;
    Button btnCommit;
    ListView listView;
    SearchView searchView;

//    boolean updateFlag = false;
//    AuthorModel author = new AuthorModel();
//    AuthorDAO authorDAO = new AuthorDAO();
//    ArrayList<AuthorModel> authorData = new ArrayList<AuthorModel>();
//    AuthorAdapter authorAdapter;
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
    }
    private void setEvents() {
//        listView.setAdapter(authorAdapter);
//        btnCommit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                author.setId(edID.getText().toString());
//                author.setAuthorName(edName.getText().toString());
//
//                for (AuthorModel authorModel : authorData) {
//                    if (authorModel.getId().equalsIgnoreCase(author.getId())) {
//                        updateFlag = true;
//                        break;
//                    }
//                }
//                if (updateFlag == true) {
//                    authorDAO.updateAuthor(author);
//                    Toast.makeText(CRUDAuthor.this, "Update success" + author.getAuthorName(), Toast.LENGTH_SHORT).show();
//                } else {
//                    if (authorDAO.addAuthor(author) == 1) {
//                        Toast.makeText(CRUDAuthor.this, "Add success" + author.getAuthorName(), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(CRUDAuthor.this, "Add Fail", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                updateFlag = false;
//                refreshListView();
//
//            }
//        });

    }
//    public void loadData(AuthorModel author) {
//        edID.setText(author.getId());
//        edName.setText(author.getAuthorName());
//
//    }
    public void refreshListView()
    {
//        authorData.clear();
//        authorData.addAll(authorDAO.getAllAuthor());
//        authorAdapter.notifyDataSetChanged();
    }
}