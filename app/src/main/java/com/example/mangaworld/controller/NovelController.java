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
                            reportState + " Thất bại '" + novel.getTitle() + "'",
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
                params.put("ID", String.valueOf(novel.getId()));
                params.put("Title", novel.getTitle());
                params.put("IDAuthor", String.valueOf(novel.getIdAuthor()));
                params.put("Desc", novel.getDescription());
                params.put("Cover", novel.getCover());
                params.put("View", novel.getViewCount()+"");
                params.put("IDUser", novel.getIdUser());
                params.put("ImageBytes", novel.getCoverImageData());
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

    public void getMostViewNovel(IVolleyCallback callback)
    {
        String urlGet = url + "/api/truyenchu/get_most_view_novel.php";
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

    public void getNewestNovel(IVolleyCallback callback)
    {
        String urlGet = url + "/api/truyenchu/get_newest_novel.php";
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

    public void getNovelByIDUser(String idUser, IVolleyCallback callback)
    {
        String urlPost = url + "/api/truyenchu/get_novel_by_iduser.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlPost, new Response.Listener<String>() {
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
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("IDUser", idUser);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }

    public void getNovelByIDGenre(int idGenre, IVolleyCallback callback)
    {
        String urlPost = url + "/api/truyenchu/get_novel_by_idgenre.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlPost, new Response.Listener<String>() {
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
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("IDGenre", idGenre+"");

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }

    public void getNovelByID(int id, IVolleyCallback callback)
    {
        String urlPost = url + "/api/truyenchu/get_novel_by_id.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlPost, new Response.Listener<String>() {
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
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("ID", String.valueOf(id));

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }

    public void getNovelOrderByDatePost(IVolleyCallback callback)
    {
        String urlGet = url + "/api/truyenchu/get_novel_order_by_date_post.php";
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


    public void increaseNovelViewCountByID(int id)
    {
        String urlPost = url + "/api/truyenchu/increase_novel_view.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlPost, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("ID", String.valueOf(id));

                return params;
            }
        };

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
                novel.setId(object.getInt("ID"));
                novel.setTitle(object.getString("Title"));
                novel.setIdAuthor(object.getInt("ID_Author"));
                novel.setDescription(object.getString("Description"));
                novel.setCover(object.getString("Cover"));
                novel.setDatePost(object.getString("Date_Post"));
                novel.setViewCount(object.getInt("View"));
                novel.setIdUser(object.getString("ID_User"));

                data.add(novel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  data;
    }

    public NovelModel convertJSONNovel(String json)
    {
        try {
            JSONObject jsonObject = new JSONObject(json);

            NovelModel novel = new NovelModel();
            novel.setId(jsonObject.getInt("ID"));
            novel.setTitle(jsonObject.getString("Title"));
            novel.setIdAuthor(jsonObject.getInt("ID_Author"));
            novel.setDescription(jsonObject.getString("Description"));
            novel.setCover(jsonObject.getString("Cover"));
            novel.setDatePost(jsonObject.getString("Date_Post"));
            novel.setViewCount(jsonObject.getInt("View"));
            novel.setIdUser(jsonObject.getString("ID_User"));

            return novel;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
