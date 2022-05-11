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

import java.util.Map;

public class YourNovelController {
    private String url;
    private Activity activity;
    private String reportState = "";

    public YourNovelController(String url, Activity activity) {
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

    public void insertYourNovel(NovelModel novel, IVolleyCallback callback)
    {

    }

    public void updateYourNovel(NovelModel novel, IVolleyCallback callback)
    {

    }

    public void deleteYourNovel(NovelModel novel, IVolleyCallback callback)
    {

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

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }
}
