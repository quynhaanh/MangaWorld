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
import com.example.mangaworld.model.NovelModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NovelController {
    private String url;
    private Activity activity;
    private String reportState = "";

    public NovelController(String url, Activity activity) {
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

    public void insertNovel(NovelModel novel, IVolleyCallback callback)
    {
        APICallPost("insert", novel, callback);
    }

    public void updateNovel(NovelModel novel, IVolleyCallback callback)
    {
        APICallPost("update", novel, callback);
    }

    public void deleteNovel(NovelModel novel, IVolleyCallback callback)
    {
        APICallPost("delete", novel, callback);
    }

    public void getNovel(IVolleyCallback callback){
        APICallGet(callback);
    }

    private void APICallPost(String type, NovelModel novel, IVolleyCallback callback)
    {
        String urlAPI = url + "/api/truyenchu/api_novel.php";

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
                if (response.contains("Success"))
                {
                    callback.onSuccess(response);
                    Toast.makeText(activity.getApplicationContext(),
                            reportState + " Thành công '" + novel.getTitle() + "'",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(activity.getApplicationContext(),
                            reportState + " Thành công '" + novel.getTitle() + "'",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity.getApplicationContext(),
                        error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Type", type);
                params.put("ID", String.valueOf(novel.getID()));
                params.put("Title", novel.getTitle());
                params.put("IDAuthor", String.valueOf(novel.getIDAuthor()));
                params.put("Cover", novel.getCover());
                params.put("DatePost", String.valueOf(novel.getDatePost()));
                params.put("IDUser", String.valueOf(novel.getIDUser()));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }

    private void APICallGet(IVolleyCallback callback)
    {
        String urlGet = url + "/api/truyenchu/get_novel.php";
        StringRequest request = new StringRequest(Request.Method.GET, urlGet, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity.getApplicationContext(),
                        error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }

    public ArrayList<NovelModel> convertJSONData(String json)
    {
        ArrayList<NovelModel> data = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("novels");

            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);

                NovelModel novel = new NovelModel();
                novel.setID(object.getInt("ID"));
                novel.setTitle(object.getString("Title"));
                novel.setIDAuthor(object.getInt("ID_Author"));
                novel.setCover(object.getString("Cover"));
                novel.setDatePost(object.getString("Date_Post"));
                novel.setIDUser(object.getString("ID_User"));

                data.add(novel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  data;
    }
}
