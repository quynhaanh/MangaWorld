package com.example.mangaworld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.CRUDUserActivity;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.UserController;
import com.example.mangaworld.controller.UserRoleController;
import com.example.mangaworld.model.UserModel;
import com.example.mangaworld.model.UserRoleModel;

import java.util.ArrayList;

public class UserItemAdapter extends ArrayAdapter {
    ArrayList<UserModel> data;
    ArrayList<UserModel> datatmp = new ArrayList<>();
    int resource;
    Context context;
    String url;
    public UserItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<UserModel> data, String url) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
        this.url = url;
        datatmp.addAll(data);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView tvID = convertView.findViewById(R.id.tvItemIDUser);
        TextView tvName = convertView.findViewById(R.id.tvItemNameUser);
        TextView tvEmail = convertView.findViewById(R.id.tvItemEmailUser);
        TextView tvRole = convertView.findViewById(R.id.tvItemRoleUser);

        ImageButton btnSua = convertView.findViewById(R.id.btnItemSuaUser);
        ImageButton btnXoa = convertView.findViewById(R.id.btnItemXoaUser);

        UserModel user = data.get(position);
        tvID.setText(String.valueOf(user.getId()));
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        //tvRole.setText(user.getIdRole());

        ArrayList<UserRoleModel> listRole = new ArrayList<>();
        UserRoleController userRoleController = new UserRoleController(url, (CRUDUserActivity) context);
        userRoleController.getRoles(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                listRole.addAll(userRoleController.convertJSONData(result));
                for (UserRoleModel role : listRole) {
                    if(user.getIdRole()==role.getId())
                    {
                        tvRole.setText(role.getName());
                    }
                }
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CRUDUserActivity) context).loadData(user);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserController userController = new UserController(url, (CRUDUserActivity) context);
                userController.deleteUser(user, new IVolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        ((CRUDUserActivity) context).refreshListView();
                    }
                });
            }
        });
        return convertView;
    }
    public void search(String searchString) {
        if(datatmp.size() < data.size())
        {
            datatmp.clear();
            datatmp.addAll(data);
        }
        data.clear();
        searchString = searchString.toLowerCase();
        if (searchString.length()==0) {
            data.addAll(datatmp);
        } else {
            for (UserModel userModel : datatmp) {
                if (userModel.getId().toLowerCase().contains(searchString) || userModel.getName().toLowerCase().contains(searchString) || userModel.getEmail().toLowerCase().contains(searchString)) {
                    data.add(userModel);
                }
            }
        }
        notifyDataSetChanged();
    }
}
