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

import java.util.ArrayList;

public class CRUDBookTypes extends AppCompatActivity {
    EditText edID, edName;
    Button btnCommit;
    ListView listView;
    SearchView searchView;

//    BookTypeModel type = new BookTypeModel();
//    BookTypeDAO typeDAO = new BookTypeDAO();
//    ArrayList<BookTypeModel> typeData = new ArrayList<BookTypeModel>();
//    BookTypeAdapter bookTypeAdapter;
//    boolean updateFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudbook_types);
//        setControls();
//        setEvents();
//        refreshListView();
    }



//    private void setControls()
//    {
//        edID =findViewById(R.id.edTypeID);
//        edName =findViewById(R.id.edTypeName);
//
//        searchView =findViewById(R.id.searchType);
//        btnCommit = findViewById(R.id.btnCommitType);
//        listView = findViewById(R.id.lvType);
//
//        bookTypeAdapter = new BookTypeAdapter(this, R.layout.layout_item_type, typeData);
//    }
//    private void setEvents() {
//        listView.setAdapter(bookTypeAdapter);
//
//        btnCommit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                type.setId(edID.getText().toString());
//                type.setTypeName(edName.getText().toString());
//
//                for (BookTypeModel typeModel : typeData) {
//                    if (typeModel.getId().equalsIgnoreCase(type.getId())) {
//                        updateFlag = true;
//                        break;
//                    }
//                }
//                if (updateFlag == true) {
//                    typeDAO.updateType(type);
//                    Toast.makeText(CRUDBookTypes.this, "Update success" + type.getTypeName(), Toast.LENGTH_SHORT).show();
//                } else {
//                    if (typeDAO.addType(type) == 1) {
//                        Toast.makeText(CRUDBookTypes.this, "Add success" + type.getTypeName(), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(CRUDBookTypes.this, "Add Fail", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                updateFlag = false;
//                refreshListView();
//            }
//        });
//
//
//    }
//
//    public void loadData(BookTypeModel type) {
//        edID.setText(type.getId());
//        edName.setText(type.getTypeName());
//
//    }
//
//    public void refreshListView()
//    {
//        typeData.clear();
//        typeData.addAll(typeDAO.getAllType());
//        bookTypeAdapter.notifyDataSetChanged();
//    }
}