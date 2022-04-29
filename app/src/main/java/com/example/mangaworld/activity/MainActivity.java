package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mangaworld.R;
import com.example.mangaworld.fragment.GenreFragment;
import com.example.mangaworld.fragment.HomeFragmentNew;
import com.example.mangaworld.fragment.LibraryFragment;
import com.example.mangaworld.fragment.AccountFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private Toolbar toolbar;
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
                    MainActivity.this.openFragment(AccountFragment.newInstance(str, str));
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

        initBottomNavigation();
    }

    private void initBottomNavigation(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        this.bottomNavigation = bottomNavigationView;
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(this.navigationItemSelectedListener);
        //để chọn cho chắc cái icon home, mở luon cái home, toolbar set luôn chữ home
        String str2 = "";
        openFragment(HomeFragmentNew.newInstance(str2 ,str2, MainActivity.this));
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