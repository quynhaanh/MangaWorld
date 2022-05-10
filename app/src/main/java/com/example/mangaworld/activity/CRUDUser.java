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

public class CRUDUser extends AppCompatActivity {
    EditText edID, edName, edEmail, edPass;
    Button btnCommit;
    ListView listView;
    SearchView searchView;
    Spinner spinnerRole;
//    UserModel userModel = new UserModel();
//
//    SpinnerAdapter adapterRole;
//    ArrayList<String> listRole ;
//
//    ArrayList<UserModel> userData = new ArrayList<>();
//    UserDao userDao;
//    UserAdapter userAdapter;

    boolean updateFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cruduser);
//        setControls();
//        setEvents();
//        refreshListView();

    }

//    private void setEvents() {
//        listView.setAdapter(userAdapter);
//
//        btnCommit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                userModel.setId(edID.getText().toString());
//                userModel.setUserName(edName.getText().toString());
//                userModel.setEmail(edEmail.getText().toString());
//                userModel.setPassword(edPass.getText().toString());
//                userModel.setRole(String.valueOf(spinnerRole.getSelectedItemPosition() +1));
//
//                for (UserModel user : userData) {
//                    if (user.getId().equalsIgnoreCase(userModel.getId())) {
//                        updateFlag = true;
//                        break;
//                    }
//                }
//
//                if (updateFlag == true) {
//                    userDao.updateUser(userModel);
//                    Toast.makeText(CRUDUser.this, "Update success" + userModel.getUserName(), Toast.LENGTH_SHORT).show();
//                } else {
//                    if(userDao.addUser(userModel)==1)
//                    {
//                        Toast.makeText(CRUDUser.this, "Add success" + userModel.getUserName(), Toast.LENGTH_SHORT).show();
//                    }else
//                    {
//                        Toast.makeText(CRUDUser.this, "Add Fail", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                updateFlag = false;
//                refreshListView();
//
//            }
//        });
//    }
//
//    private void setControls() {
//    edID =findViewById(R.id.edUserID);
//    edName =findViewById(R.id.edUserName);
//    edEmail =findViewById(R.id.edUserEmail);
//    edPass =findViewById(R.id.edUserPass);
//    spinnerRole =findViewById(R.id.spinnerUserRole);
//
//    searchView =findViewById(R.id.searchUser);
//    btnCommit = findViewById(R.id.btnCommitUser);
//    listView = findViewById(R.id.lvUser);
//
//    //spinner
//    listRole= getRoleList();
//    adapterRole = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listRole);
//    spinnerRole.setAdapter(adapterRole);
//
//    userDao = new UserDao();
//    userAdapter = new UserAdapter(this, R.layout.layout_item_user, userData);
//
//
//    }
//    public ArrayList<String> getRoleList()
//    {
//        ArrayList<String> list = new ArrayList<String>();
//        list.add("Admin");
//        list.add("User");
//        return list;
//    }
//
//    //đẩy thông tin lên editText
//    public void loadData(UserModel user) {
//        edID.setText(user.getId());
//        edName.setText(user.getUserName());
//        edEmail.setText(user.getEmail());
//        edPass.setText(user.getPassword());
//        spinnerRole.setSelection(Integer.parseInt(user.getRole())-1) ;
//
//    }
//
//    public void refreshListView()
//    {
//        userData.clear();
//        userData.addAll(userDao.getAllUser());
//        userAdapter.notifyDataSetChanged();
//    }
}