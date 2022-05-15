package com.example.mangaworld.controller;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mangaworld.model.GenreModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GenreController {
    private String url;
    private Activity activity;
    private String reportState = "";

    public GenreController(String url, Activity activity) {
        this.url = url;
        this.activity = activity;
    }

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

    public void insertGenre(GenreModel genre, IVolleyCallback callback) {
        APICallPost("insert", genre, callback);
    }

    public void updateGenre(GenreModel genre, IVolleyCallback callback) {
        APICallPost("update", genre, callback);
    }

    public void deleteGenre(GenreModel genre, IVolleyCallback callback) {
        APICallPost("delete", genre, callback);
    }

    public void getGenres(IVolleyCallback callback) {
        APICallGet(callback);
    }

    private void APICallPost(String type, GenreModel genre, IVolleyCallback callback) {
        String urlAPI = url + "/api/truyenchu/api_genre.php";

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
                            reportState + "thành công '" + genre.getName() + "'", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(),
                            reportState + "không thành công '" + genre.getName() + "'", Toast.LENGTH_SHORT).show();
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
                params.put("ID", String.valueOf(genre.getId()));
                params.put("Name", genre.getName());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }

    private void APICallGet(IVolleyCallback callback) {
        String urlGet = url + "/api/truyenchu/get_genre.php";
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

    public void getGenreByNovelID(int novelID, IVolleyCallback callback) {
        String urlAPI = url + "/api/truyenchu/get_genre_by_idnovel.php";

        StringRequest request = new StringRequest(Request.Method.POST, urlAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
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
                params.put("IDNovel", novelID + "");
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }


    public ArrayList<GenreModel> convertJSONData(String json) {
        ArrayList<GenreModel> data = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("genres");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                GenreModel genre = new GenreModel();
                genre.setId(object.getInt("ID"));
                genre.setName(object.getString("Genre_Name"));

                data.add(genre);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return data;
    }
}
