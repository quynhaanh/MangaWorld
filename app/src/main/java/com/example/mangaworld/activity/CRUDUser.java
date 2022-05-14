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
import com.example.mangaworld.adapter.ChapterItemAdapter;
import com.example.mangaworld.adapter.UserItemAdapter;
import com.example.mangaworld.controller.ChapterController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.UserController;
import com.example.mangaworld.controller.UserRoleController;
import com.example.mangaworld.model.ChapterModel;
import com.example.mangaworld.model.NovelModel;
import com.example.mangaworld.model.UserModel;
import com.example.mangaworld.model.UserRoleModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CRUDUser extends AppCompatActivity {
    EditText edID, edName, edEmail, edPass;
    Button btnCommit;
    ListView listView;
    SearchView searchView;
    Spinner spinnerRole;
    UserModel user;

    SpinnerAdapter adapterRole;
    ArrayList<UserRoleModel> listRole = new ArrayList<>();
    ArrayList<String> listRoleName = new ArrayList<>();

    ArrayList<UserModel> userData = new ArrayList<>();
    UserController userController;
    UserRoleController userRoleController;
    UserItemAdapter userItemAdapter;

    String url = LoadActivity.url;
    boolean updateFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cruduser);
        setControls();
        setEvents();
        refreshListView();

    }

    public void closeActivity(View view) {
        finish();
    }

    private void setControls() {
        edID =findViewById(R.id.edUserID);
        edName =findViewById(R.id.edUserName);
        edEmail =findViewById(R.id.edUserEmail);
        edPass =findViewById(R.id.edUserPass);
        spinnerRole =findViewById(R.id.spinnerUserRole);

        searchView =findViewById(R.id.searchUser);
        btnCommit = findViewById(R.id.btnCommitUser);
        listView = findViewById(R.id.lvUser);

        userController= new UserController(url, this);
        userItemAdapter = new UserItemAdapter(CRUDUser.this, R.layout.layout_item_user, userData, url);

        //spinner
        adapterRole = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listRoleName);
        userRoleController = new UserRoleController(url, this);
        userRoleController.getRoles(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                listRole.addAll(userRoleController.convertJSONData(result));
                for (UserRoleModel role : listRole) {
                    String temp = String.valueOf(role.getName());
                    listRoleName.add(temp);
                }
                spinnerRole.setAdapter(adapterRole);
            }
        });

    }
    private void setEvents() {
        listView.setAdapter(userItemAdapter);
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = new UserModel();
                user.setId(edID.getText().toString());
                user.setName(edName.getText().toString());
                user.setPass(edPass.getText().toString());
                user.setEmail(edEmail.getText().toString());
                Integer tempID =  listRole.get(spinnerRole.getSelectedItemPosition()).getId();
                user.setIdRole(tempID);

                for (UserModel userModel : userData) {
                    if (userModel.getId() == user.getId()) {
                        updateFlag = true;
                        break;
                    }
                }
                if (updateFlag == true) {
                    userController.updateUser(user, new IVolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            refreshListView();
                        }
                    });
                } else {
                    userController.insertUser(user, new IVolleyCallback() {
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

    public void loadData(UserModel user) {
        edID.setText(user.getId());
        edName.setText(user.getName());
        edEmail.setText(user.getEmail());
        edPass.setText(user.getPass());
        for(UserRoleModel userRoleModel : listRole)
        {
            if(user.getIdRole()==userRoleModel.getId()){
                spinnerRole.setSelection(getIndex(spinnerRole,userRoleModel.getName()));
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
        userController.getUsers(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                userData.clear();
                userData.addAll(userController.convertJSONData(result));
                userItemAdapter.notifyDataSetChanged();
            }
        });
    }
}