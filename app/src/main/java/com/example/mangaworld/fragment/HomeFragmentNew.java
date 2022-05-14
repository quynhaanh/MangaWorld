package com.example.mangaworld.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.AllMangaActivity;
import com.example.mangaworld.activity.ChapterActivity;
import com.example.mangaworld.activity.DetailMangaActivity;
import com.example.mangaworld.activity.LoadActivity;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.adapter.ItemClickInterface;
import com.example.mangaworld.adapter.MangaRecyclerviewAdapter;
import com.example.mangaworld.adapter.TopMangaRecyclerViewAdapter;
import com.example.mangaworld.adapter.ViewPagerSlideAdapter;
import com.example.mangaworld.model.Manga;
import com.example.mangaworld.transformer.ZoomOutPageTransformer;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;


public class HomeFragmentNew extends Fragment {
    public HomeFragmentNew() {
        // Required empty public constructor
    }
    MainActivity mainAcdsctivity;

    //slider
    private Handler mhandler = new Handler();
    private ViewPagerSlideAdapter viewPagerSlideAdapter;
    private ViewPager2 viewPager2;
    private CircleIndicator3 indicator3;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ArrayList<Manga> slideArrayList ;

    //popular
    private RecyclerView recycleViewPopulation;
    private ShimmerFrameLayout shimmerPopular;
    private TextView tvSeeAllPopular;
    private ArrayList<Manga> popularMangaArrayList;
    private MangaRecyclerviewAdapter mangaRecyclerviewAdapter;

    //manhua
    private RecyclerView recycleViewManhua;
    private ShimmerFrameLayout shimmerManhua;
    private TextView tvSeeAllManhua;
    private ArrayList<Manga> manhuaMangaArrayList;
    private MangaRecyclerviewAdapter manhuaRecyclerviewAdapter;

    //manhwa
    private RecyclerView recycleViewManhwa;
    private ShimmerFrameLayout shimmerManhwa;
    private TextView tvSeeAllManhwa;
    private ArrayList<Manga> manhwaMangaArrayList;
    private MangaRecyclerviewAdapter manhwaRecyclerviewAdapter;

    //top manga
    private RecyclerView recycleViewTopManga;
    private ShimmerFrameLayout shimmerTopManga;
    private TextView tvSeeAllTop;
    private ArrayList<Manga> topMangaArrayList;
    private TopMangaRecyclerViewAdapter topMangaRecyclerviewAdapter;

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //nếu slide di chuyển đến cuối thì set lại về đầu
            if(viewPager2.getCurrentItem() == slideArrayList.size() - 1){
                viewPager2.setCurrentItem(0);
            }
            else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };

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
        LoadPopular(view);
        LoadManhua(view);
        LoadManhwa(view);
        LoadTopManga(view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        //giữ slide đã hiện trước đó khi quay lại màn hình này
        mhandler.postDelayed(runnable, 3000);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerPopular.stopShimmer();
        shimmerPopular.setVisibility(View.GONE);
        shimmerManhua.stopShimmer();
        shimmerManhua.setVisibility(View.GONE);
        shimmerManhwa.stopShimmer();
        shimmerManhwa.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        mhandler.removeCallbacks(runnable);
    }

    public void LoadSlide(View view){
        viewPager2 = view.findViewById(R.id.viewPagerTitle);
        indicator3 = view.findViewById(R.id.indicator3);
        shimmerFrameLayout = view.findViewById(R.id.contentShimmerRecommended);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        slideArrayList = new ArrayList<>();

//        Manga slide = new Manga("https://res.cloudinary.com/dmfrvd4tl/image/upload/v1638689117/cqihlyiwovjgksu3jnmy.jpg","Tiệc tùng thôi nào");
        Manga slide = new Manga(LoadActivity.url + "/api/truyenchu/Upload/81HBvFY7sjL.jpg","Tiệc tùng thôi nào");
        Manga slide1 = new Manga("https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507157/Mangaworld/World-Trigger-Season-2-release-date-WorTri-anime-sequel-confirmed-at-Jump-Festa-2020_aeub2y.jpg","World Trigger Season 2");
        Manga slide2 = new Manga("https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507206/Mangaworld/a14168d0bfc3099b_ad6fcc5373b42db5_13092816427815313185710_oahbmz.jpg","Hiệp sĩ xương du hành");
        Manga slide3 = new Manga("https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507216/Mangaworld/maxresdefault-06275585_hgqx60.jpg","Spy x Family");
        slideArrayList.add(slide);
        slideArrayList.add(slide1);
        slideArrayList.add(slide2);
        slideArrayList.add(slide3);

        viewPagerSlideAdapter = new ViewPagerSlideAdapter(slideArrayList);
        //click vào từng slider
        viewPagerSlideAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailMangaActivity.class);
                intent.putExtra("idManga", slideArrayList.get(position).getIdManga());
                startActivity(intent);            }
        });
        viewPager2.setAdapter(viewPagerSlideAdapter);
        indicator3.setViewPager(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mhandler.removeCallbacks(runnable);
                mhandler.postDelayed(runnable, 3000);
            }
        });
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());

        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    public void LoadPopular(View view){
        recycleViewPopulation = view.findViewById(R.id.recycleViewPopulation);
        tvSeeAllPopular = view.findViewById(R.id.btnPopular);
        shimmerPopular = view.findViewById(R.id.contentShimmerPopular);
        shimmerPopular.startShimmer();
        shimmerPopular.setVisibility(View.VISIBLE);
        popularMangaArrayList = new ArrayList<>();

        Manga slide = new Manga(1, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507319/Mangaworld/MV5BODcwNWE3OTMtMDc3MS00NDFjLWE1OTAtNDU3NjgxODMxY2UyXkEyXkFqcGdeQXVyNTAyODkwOQ_._V1_FMjpg_UX1000__ij1oft.jpg","Vua hải tặc", 30000, "Phiêu lưu");
        Manga slide1 = new Manga(2, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507145/Mangaworld/Naruto_Volume_1_manga_cover_q2cere.jpg","Naruto", 50000, "Hành động");
        Manga slide2 = new Manga(3, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507429/Mangaworld/conan_24_-_main_poster_1__vijsfb.jpg","Thám tử lừng danh conan", 80000, "Trinh thám");
        Manga slide3 = new Manga(4, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507420/Mangaworld/tranh-dan-tuong-3d-cartoon-songoku-scaled_cv67o3.jpg","Bảy viên ngọc rồng Z", 10000, "Hành động");
        popularMangaArrayList.add(slide);
        popularMangaArrayList.add(slide1);
        popularMangaArrayList.add(slide2);
        popularMangaArrayList.add(slide3);

        mangaRecyclerviewAdapter = new MangaRecyclerviewAdapter(popularMangaArrayList);
        //click vào từng nút +
        mangaRecyclerviewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailMangaActivity.class);
                intent.putExtra("idManga", popularMangaArrayList.get(position).getIdManga());
                startActivity(intent);            }
        });
        recycleViewPopulation.setAdapter(mangaRecyclerviewAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleViewPopulation.setLayoutManager(layoutManager);

        tvSeeAllPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //truyền qua cho nó key để nhận biết dữ liệu khi dùng chung màn hình
                //vd số 1 là see all popular, số 2 là see all manhhua, số 3 là see all manhwa
                Intent intent = new Intent(getContext(), AllMangaActivity.class);
                intent.putExtra("checkManga", "popular");
                startActivity(intent);
            }
        });

        shimmerPopular.stopShimmer();
        shimmerPopular.setVisibility(View.GONE);
    }

    public void LoadManhua(View view){
        recycleViewManhua = view.findViewById(R.id.recycleViewManhua);
        tvSeeAllManhua = view.findViewById(R.id.btnManhua);
        shimmerManhua = view.findViewById(R.id.contentShimmerManhua);
        shimmerManhua.startShimmer();
        shimmerManhua.setVisibility(View.VISIBLE);
        manhuaMangaArrayList = new ArrayList<>();

        Manga slide = new Manga(1, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507319/Mangaworld/MV5BODcwNWE3OTMtMDc3MS00NDFjLWE1OTAtNDU3NjgxODMxY2UyXkEyXkFqcGdeQXVyNTAyODkwOQ_._V1_FMjpg_UX1000__ij1oft.jpg","Vua hải tặc", 30000, "Phiêu lưu");
        Manga slide1 = new Manga(2, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507145/Mangaworld/Naruto_Volume_1_manga_cover_q2cere.jpg","Naruto", 50000, "Hành động");
        Manga slide2 = new Manga(3, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507429/Mangaworld/conan_24_-_main_poster_1__vijsfb.jpg","Thám tử lừng danh conan", 80000, "Trinh thám");
        Manga slide3 = new Manga(4, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507420/Mangaworld/tranh-dan-tuong-3d-cartoon-songoku-scaled_cv67o3.jpg","Bảy viên ngọc rồng Z", 10000, "Hành động");
        manhuaMangaArrayList.add(slide);
        manhuaMangaArrayList.add(slide1);
        manhuaMangaArrayList.add(slide2);
        manhuaMangaArrayList.add(slide3);

        manhuaRecyclerviewAdapter = new MangaRecyclerviewAdapter(manhuaMangaArrayList);
        //click vào từng nút +
        manhuaRecyclerviewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailMangaActivity.class);
                intent.putExtra("idManga", manhuaMangaArrayList.get(position).getIdManga());
                startActivity(intent);            }
        });
        recycleViewManhua.setAdapter(manhuaRecyclerviewAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleViewManhua.setLayoutManager(layoutManager);

        tvSeeAllManhua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //truyền qua cho nó 1 chữ số để nhận biết dữ liệu khi dùng chung màn hình
                //vd số 1 là see all popular, số 2 là see all manhhua, số 3 là see all manhwa
                Intent intent = new Intent(getContext(), AllMangaActivity.class);
                intent.putExtra("checkManga", "manhua");
                startActivity(intent);
            }
        });

        shimmerManhua.stopShimmer();
        shimmerManhua.setVisibility(View.GONE);
    }

    public void LoadManhwa(View view){
        recycleViewManhwa = view.findViewById(R.id.recycleViewManhwa);
        tvSeeAllManhwa = view.findViewById(R.id.btnManhwa);
        shimmerManhwa = view.findViewById(R.id.contentShimmerManhwa);
        shimmerManhwa.startShimmer();
        shimmerManhwa.setVisibility(View.VISIBLE);
        manhwaMangaArrayList = new ArrayList<>();

        Manga slide = new Manga(1, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507319/Mangaworld/MV5BODcwNWE3OTMtMDc3MS00NDFjLWE1OTAtNDU3NjgxODMxY2UyXkEyXkFqcGdeQXVyNTAyODkwOQ_._V1_FMjpg_UX1000__ij1oft.jpg","Vua hải tặc", 30000, "Phiêu lưu");
        Manga slide1 = new Manga(2, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507145/Mangaworld/Naruto_Volume_1_manga_cover_q2cere.jpg","Naruto", 50000, "Hành động");
        Manga slide2 = new Manga(3, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507429/Mangaworld/conan_24_-_main_poster_1__vijsfb.jpg","Thám tử lừng danh conan", 80000, "Trinh thám");
        Manga slide3 = new Manga(4, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507420/Mangaworld/tranh-dan-tuong-3d-cartoon-songoku-scaled_cv67o3.jpg","Bảy viên ngọc rồng Z", 10000, "Hành động");
        manhwaMangaArrayList.add(slide);
        manhwaMangaArrayList.add(slide1);
        manhwaMangaArrayList.add(slide2);
        manhwaMangaArrayList.add(slide3);

        manhwaRecyclerviewAdapter = new MangaRecyclerviewAdapter(manhwaMangaArrayList);
        //click vào từng nút +
        manhwaRecyclerviewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailMangaActivity.class);
                intent.putExtra("idManga", manhwaMangaArrayList.get(position).getIdManga());
                startActivity(intent);            }
        });
        recycleViewManhwa.setAdapter(manhwaRecyclerviewAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleViewManhwa.setLayoutManager(layoutManager);

        tvSeeAllManhwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //truyền qua cho nó 1 chữ số để nhận biết dữ liệu khi dùng chung màn hình
                //vd số 1 là see all popular, số 2 là see all manhhua, số 3 là see all manhwa
                Intent intent = new Intent(getContext(), AllMangaActivity.class);
                intent.putExtra("checkManga", "manhwa");
                startActivity(intent);
            }
        });

        shimmerManhwa.stopShimmer();
        shimmerManhwa.setVisibility(View.GONE);
    }

    private void LoadTopManga(View view) {
        recycleViewTopManga = view.findViewById(R.id.recycleViewGenres);
        tvSeeAllTop = view.findViewById(R.id.btnTop);
        shimmerTopManga = view.findViewById(R.id.contentShimmerGener);
        shimmerTopManga.startShimmer();
        shimmerTopManga.setVisibility(View.VISIBLE);
        topMangaArrayList = new ArrayList<>();

        Manga slide = new Manga(LoadActivity.url + "/api/truyenchu/Upload/81HBvFY7sjL.jpg","Tiệc tùng thôi nào");
        Manga slide1 = new Manga("https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507157/Mangaworld/World-Trigger-Season-2-release-date-WorTri-anime-sequel-confirmed-at-Jump-Festa-2020_aeub2y.jpg","World Trigger Season 2");
        Manga slide2 = new Manga("https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507206/Mangaworld/a14168d0bfc3099b_ad6fcc5373b42db5_13092816427815313185710_oahbmz.jpg","Hiệp sĩ xương du hành");
        Manga slide3 = new Manga("https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507216/Mangaworld/maxresdefault-06275585_hgqx60.jpg","Spy x Family");
        topMangaArrayList.add(slide);
        topMangaArrayList.add(slide1);
        topMangaArrayList.add(slide2);
        topMangaArrayList.add(slide3);

        topMangaRecyclerviewAdapter = new TopMangaRecyclerViewAdapter(topMangaArrayList);
        //click vào từng nút +
        topMangaRecyclerviewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailMangaActivity.class);
                intent.putExtra("idManga", topMangaArrayList.get(position).getIdManga());
                startActivity(intent);
            }
        });
        recycleViewTopManga.setAdapter(topMangaRecyclerviewAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleViewTopManga.setLayoutManager(layoutManager);

        tvSeeAllTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //truyền qua cho nó 1 chữ số để nhận biết dữ liệu khi dùng chung màn hình
                //vd số 1 là see all popular, số 2 là see all manhhua, số 3 là see all manhwa
                Intent intent = new Intent(getContext(), AllMangaActivity.class);
                intent.putExtra("checkManga", "top");
                startActivity(intent);
            }
        });

        //tắt shimmer
        shimmerTopManga.stopShimmer();
        shimmerTopManga.setVisibility(View.GONE);
    }
}