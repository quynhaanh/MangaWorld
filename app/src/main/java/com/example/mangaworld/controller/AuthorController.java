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
import com.example.mangaworld.model.GenreModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AuthorController {
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

    public AuthorController(String url, Activity activity) {
        this.url = url;
        this.activity = activity;
    }

    public void insertAuthor(AuthorModel author, IVolleyCallback callback) {
        APICallPost("insert", author, callback);
    }

    public void updateAuthor(AuthorModel author,IVolleyCallback callback) {
        APICallPost("update", author, callback);
    }

    public void deleteAuthor(AuthorModel author,IVolleyCallback callback) {
        APICallPost("delete", author, callback);
    }

    public void getAuthors(IVolleyCallback callback) {
        APICallGet(callback);
    }

    private void APICallPost(String type,AuthorModel author, IVolleyCallback callback) {
        String urlAPI = url + "/api/truyenchu/api_author.php";

        if (type.equals("insert")) {
            reportState = "Thêm ";
        } else if (type.equals("update")) {
            reportState = "Cập nhật ";
        } else if (type.equals("delete")) {
            reportState = "Xóa ";
        }

        StringRequest request = new StringRequest(Request.Method.POST, urlAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Success")) {
                    callback.onSuccess(response);
                    Toast.makeText(activity.getApplicationContext(),
                            reportState + "thành công '" + author.getName() + "'", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(),
                            reportState + "không thành công '" + author.getName() + "'", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Type", type);
                params.put("ID", String.valueOf(author.getId()));
                params.put("Name", author.getName());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }
    private void APICallGet(IVolleyCallback callback) {
        String urlGet = url + "/api/truyenchu/get_author.php";
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

    public ArrayList<AuthorModel> convertJSONData(String json) {
        ArrayList<AuthorModel> data = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("authors");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                AuthorModel author = new AuthorModel();
                author.setId(object.getInt("ID"));
                author.setName(object.getString("Author_Name"));

                data.add(author);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return data;
    }
}
