package com.example.mangaworld.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.adapter.ViewPagerSlideAdapter;
import com.example.mangaworld.model.Slide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;


public class HomeFragmentNew extends Fragment {
    public HomeFragmentNew() {
        // Required empty public constructor
    }
    MainActivity mainAcdsctivity;

    public HomeFragmentNew(MainActivity mainActivity) {
        this.mainAcdsctivity = mainActivity;
    }

    public static HomeFragmentNew newInstance(String str, String str2, MainActivity mainActivity) {
        HomeFragmentNew mainFragment = new HomeFragmentNew(mainActivity);
        Bundle bundle = new Bundle();
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    public static HomeFragmentNew newInstance(String param1, String param2) {
        HomeFragmentNew fragment = new HomeFragmentNew();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_new, container, false);
        LoadSlide(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        shimmerFrameLayout.stopShimmer();
//        shimmerFrameLayout.setVisibility(View.GONE);
    }

    public void LoadSlide(View view){
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPagerTitle);
        CircleIndicator3 indicator3 = view.findViewById(R.id.indicator3);
        ShimmerFrameLayout shimmerFrameLayout = view.findViewById(R.id.contentShimmerRecommended);
        ArrayList<Slide> slideArrayList = new ArrayList<>();
        Slide slide = new Slide("https://res.cloudinary.com/dmfrvd4tl/image/upload/v1638689117/cqihlyiwovjgksu3jnmy.jpg","Tiệc tùng thôi nào");
        Slide slide1 = new Slide("https://res.cloudinary.com/dmfrvd4tl/image/upload/v1650651552/lbm9xyslmguocztmrdvu.jpg","Chị đẹp");
        Slide slide2 = new Slide("https://res.cloudinary.com/dmfrvd4tl/image/upload/v1650650848/rgvre1n9fmnhrpnlrd82.jpg","Quang nè");
        Slide slide3 = new Slide("https://res.cloudinary.com/dmfrvd4tl/image/upload/v1638689117/cqihlyiwovjgksu3jnmy.jpg","Tiệc tùng thôi phần 2");
        slideArrayList.add(slide);
        slideArrayList.add(slide1);
        slideArrayList.add(slide2);
        slideArrayList.add(slide3);

        ViewPagerSlideAdapter viewPagerSlideAdapter = new ViewPagerSlideAdapter(slideArrayList);
        viewPager2.setAdapter(viewPagerSlideAdapter);
        indicator3.setViewPager(viewPager2);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

}