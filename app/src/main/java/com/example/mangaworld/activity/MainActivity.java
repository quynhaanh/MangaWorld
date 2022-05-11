package com.example.mangaworld.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mangaworld.R;
import com.example.mangaworld.controller.GenreController;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.UserController;
import com.example.mangaworld.fragment.AccountInfoFragment;
import com.example.mangaworld.fragment.GenreFragment;
import com.example.mangaworld.fragment.HomeFragmentNew;
import com.example.mangaworld.fragment.LibraryFragment;
import com.example.mangaworld.fragment.AccountFragment;
import com.example.mangaworld.model.UserModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private Toolbar toolbar;
    private String userRole;
    private String url = "http://192.168.1.6";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            String str = "";
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle(R.string.home);
                    MainActivity.this.openFragment(HomeFragmentNew.newInstance(str, str, MainActivity.this));
                    return true;

                case R.id.navigation_map:
                    toolbar.setTitle(R.string.genres);
                    MainActivity.this.openFragment(GenreFragment.newInstance(str, str));
                    return true;

                case R.id.navigation_world:
                    toolbar.setTitle(R.string.search);
                    MainActivity.this.openFragment(LibraryFragment.newInstance(str, str));
                    return true;

                case R.id.navigation_walk:
                    toolbar.setTitle(R.string.account);
                    if (userRole != null) {
                        MainActivity.this.openFragment(AccountInfoFragment.newInstance(str, str, userRole));
                    } else {
                        MainActivity.this.openFragment(AccountFragment.newInstance(str, str));
                    }
                    return true;

                default:
                    return false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.toolbar = initToolbar();
        userRole = null;

        initBottomNavigation();
    }

    private void initBottomNavigation() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        this.bottomNavigation = bottomNavigationView;
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(this.navigationItemSelectedListener);
        //để chọn cho chắc cái icon home, mở luon cái home, toolbar set luôn chữ home
        String str2 = "";
        openFragment(HomeFragmentNew.newInstance(str2, str2, MainActivity.this));
    }

    private Toolbar initToolbar() {
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar2);
        return toolbar2;
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.nav_host_fragment, fragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
    }

    public void setUserRole(String role) {
        this.userRole = role;
    }

    public int sendOTPCode(String email) {
        Random random = new Random();
        int code = random.nextInt(8999) + 1000;

        String urlAPI = url + "/api/truyenchu/send_mail.php";

        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("code", code + "");
                return params;
            }
        };
        request.add(stringRequest);

        return code;
    }

    public void login(UserModel user)
    {
        String urlAPI = url+"/api/truyenchu/send_mail.php";
//        UserController controller = new UserController(this);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Thoát ứng dụng");
        builder.setMessage("Bạn có muốn thoát ứng dụng?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MainActivity.this.finish();
                System.exit(1);
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}