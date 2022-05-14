package com.example.mangaworld.controller;

import android.app.Activity;
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
import com.example.mangaworld.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserController {
    private String url;
    private Activity activity;
    private String reportState = "";

    public UserController(String url, Activity activity) {
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

    public void insertUser(UserModel user, IVolleyCallback callback) {
        APICallPost("insert", user, callback);
    }

    public void updateUser(UserModel user, IVolleyCallback callback) {
        APICallPost("update", user, callback);
    }

    public void deleteUser(UserModel user, IVolleyCallback callback) {
        APICallPost("delete", user, callback);
    }

    public void getUsers(IVolleyCallback callback) {
        APICallGet(callback);
    }

    private void APICallPost(String type, UserModel user, IVolleyCallback callback) {
        String urlAPI = url + "/api/truyenchu/api_user.php";

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
                    Log.d("user", reportState + "thành công '" + user.getName() + "'");
                } else {
                    Log.d("user", reportState + "không thành công '" + user.getName());
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
                params.put("ID", user.getId());
                params.put("Name", user.getName());
                params.put("Pass", user.getPass());
                params.put("Email", user.getEmail());
                params.put("IDRole", user.getIdRole() + "");
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }

    private void APICallGet(IVolleyCallback callback) {
        String urlGet = url + "/api/truyenchu/get_user.php";
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

    public void getUserByEmail(String email, IVolleyCallback callback) {
        String urlGet = url + "/api/truyenchu/get_user_by_email.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlGet, new Response.Listener<String>() {
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
                params.put("Email", email);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }

    public void getUserByID(String userID, IVolleyCallback callback) {
        String urlGet = url + "/api/truyenchu/get_user_by_id.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlGet, new Response.Listener<String>() {
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
                params.put("ID", userID);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }


    public void login(UserModel user, IVolleyCallback callback) {
        String urlGet = url + "/api/truyenchu/get_user.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlGet, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Error")) {
                    Toast.makeText(activity.getApplicationContext(), "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                } else {
                    callback.onSuccess(response);
                    Toast.makeText(activity.getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
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
                params.put("ID", user.getId());
                params.put("Pass", user.getPass());

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(request);
    }

    public ArrayList<UserModel> convertJSONData(String json) {
        ArrayList<UserModel> data = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("users");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                UserModel user = new UserModel();
                user.setId(object.getString("ID"));
                user.setName(object.getString("Name"));
                user.setEmail(object.getString("Email"));
                user.setPass(object.getString("Pass"));
                user.setIdRole(object.getInt("ID_Role"));

                data.add(user);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return data;
    }

    public UserModel convertJSONUser(String json) {
        UserModel user = new UserModel();
        try {
            JSONObject jsonObject = new JSONObject(json);

            user.setId(jsonObject.getString("ID"));
            user.setName(jsonObject.getString("Name"));
            user.setEmail(jsonObject.getString("Email"));
            user.setPass(jsonObject.getString("Pass"));
            user.setIdRole(jsonObject.getInt("ID_Role"));

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return user;
    }
}
