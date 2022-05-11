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
import com.example.mangaworld.model.NovelGenresModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NovelGenresController {
    private String url;
    private Activity activity;
    private String reportState = "";

    public NovelGenresController(String url, Activity activity) {
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

    public void insertNovelGenre(NovelGenresModel novelGenre, IVolleyCallback callback) {
        APICallPost("insert", novelGenre, callback);
    }

    public void updateNovelGenre(NovelGenresModel novelGenre,IVolleyCallback callback) {
        APICallPost("update", novelGenre, callback);
    }

    public void deleteNovelGenre(NovelGenresModel novelGenre,IVolleyCallback callback) {
        APICallPost("delete", novelGenre, callback);
    }

    public void getNovelGenres(IVolleyCallback callback) {
        APICallGet(callback);
    }

    private void APICallPost(String type, NovelGenresModel novelGenre, IVolleyCallback callback) {
        String urlAPI = url + "/api/truyenchu/api_novel_genres.php";

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
                            reportState + "thành công '" + novelGenre.getNovelID() + "'",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(),
                            reportState + "không thành công '" + novelGenre.getNovelID() + "'",
                            Toast.LENGTH_SHORT).show();
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
                params.put("IDNovel", String.valueOf(novelGenre.getNovelID()));
                params.put("IDGenre", String.valueOf(novelGenre.getGenreID()));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }

    private void APICallGet(IVolleyCallback callback) {
        String urlGet = url + "/api/truyenchu/get_novel_genres.php";
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

    public ArrayList<NovelGenresModel> convertJSONData(String json) {
        ArrayList<NovelGenresModel> data = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("novel_genres");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                NovelGenresModel novelGenre = new NovelGenresModel();
                novelGenre.setNovelID(object.getInt("ID_Novel"));
                novelGenre.setGenreID(object.getInt("ID_Genre"));

                data.add(novelGenre);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return data;
    }
}
