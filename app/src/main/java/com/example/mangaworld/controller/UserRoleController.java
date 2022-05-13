package com.example.mangaworld.controller;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mangaworld.model.AuthorModel;
import com.example.mangaworld.model.UserRoleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRoleController {
    private String url;
    private Activity activity;
    private String reportState = "";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public UserRoleController() {
    }

    public UserRoleController(String url, Activity activity) {
        this.url = url;
        this.activity = activity;
    }


    public void getRoles(IVolleyCallback callback) {
        APICallGet(callback);
    }

    private void APICallGet(IVolleyCallback callback) {
        String urlGet = url + "/api/truyenchu/get_role.php";
        StringRequest request = new StringRequest(Request.Method.GET, urlGet, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }

    public ArrayList<UserRoleModel> convertJSONData(String json) {
        ArrayList<UserRoleModel> data = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("roles");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                UserRoleModel role = new UserRoleModel();
                role.setId(object.getInt("ID"));
                role.setName(object.getString("Role_Name"));

                data.add(role);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return data;
    }
}
