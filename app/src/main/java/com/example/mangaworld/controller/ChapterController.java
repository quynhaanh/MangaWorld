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
import com.example.mangaworld.model.ChapterModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChapterController {
    private String url;
    private Activity activity;
    private String reportState = "";

    public ChapterController(String url, Activity activity) {
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

    public void insertChapter(ChapterModel chapter, IVolleyCallback callback) {
        APICallPost("insert", chapter, callback);
    }

    public void updateChapter(ChapterModel chapter,IVolleyCallback callback) {
        APICallPost("update", chapter, callback);
    }

    public void deleteChapter(ChapterModel chapter,IVolleyCallback callback) {
        APICallPost("delete", chapter, callback);
    }

    public void getChapters(IVolleyCallback callback) {
        APICallGet(callback);
    }

    private void APICallPost(String type, ChapterModel chapter, IVolleyCallback callback) {
        String urlAPI = url + "/api/truyenchu/api_chapter.php";

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
                            reportState + "thành công '" + chapter.getTitle() + "'",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(),
                            reportState + "không thành công '" + chapter.getTitle() + "'",
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
                params.put("ID", String.valueOf(chapter.getId()));
                params.put("Title", chapter.getTitle());
                params.put("Content", chapter.getContent());
                params.put("DatePost", chapter.getDatePost());
                params.put("IDNovel", String.valueOf(chapter.getNovelID()));

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }

    private void APICallGet(IVolleyCallback callback) {
        String urlGet = url + "/api/truyenchu/get_chapter.php";
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

    public ArrayList<ChapterModel> convertJSONData(String json) {
        ArrayList<ChapterModel> data = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("chapters");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                ChapterModel chapter = new ChapterModel();
                chapter.setId(object.getInt("ID"));
                chapter.setTitle(object.getString("Title"));
                chapter.setContent(object.getString("Content"));
                chapter.setDatePost(object.getString("Date_Post"));
                chapter.setNovelID(object.getInt("ID_Novel"));

                data.add(chapter);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return data;
    }
}
