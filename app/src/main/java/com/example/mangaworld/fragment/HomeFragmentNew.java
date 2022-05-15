package com.example.mangaworld.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.AllNovelActivity;
import com.example.mangaworld.activity.DetailNovelActivity;
import com.example.mangaworld.activity.LoadActivity;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.adapter.ItemClickInterface;
import com.example.mangaworld.adapter.NovelRecyclerviewAdapter;
import com.example.mangaworld.adapter.TopNovelRecyclerViewAdapter;
import com.example.mangaworld.adapter.ViewPagerSlideAdapter;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.NovelModel;
import com.example.mangaworld.transformer.ZoomOutPageTransformer;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;


public class HomeFragmentNew extends Fragment {
    public HomeFragmentNew() {
        // Required empty public constructor
    }

    MainActivity mainAcdsctivity;
    ArrayList<NovelModel> newestNovelData = new ArrayList<>();


    //slider
    private Handler mhandler = new Handler();
    private ViewPagerSlideAdapter viewPagerSlideAdapter;
    private ViewPager2 viewPager2;
    private CircleIndicator3 indicator3;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ArrayList<NovelModel> slideArrayList;

    //popular
    private RecyclerView recycleViewPopulation;
    private ShimmerFrameLayout shimmerPopular;
    private TextView tvSeeAllPopular;
    private ArrayList<NovelModel> popularMangaArrayList;
    private NovelRecyclerviewAdapter novelRecyclerviewAdapter;

    //manhua
    private RecyclerView recycleViewManhua;
    private ShimmerFrameLayout shimmerManhua;
    private TextView tvSeeAllManhua;
    private ArrayList<NovelModel> manhuaMangaArrayList;
    private NovelRecyclerviewAdapter manhuaRecyclerviewAdapter;

    //manhwa
    private RecyclerView recycleViewManhwa;
    private ShimmerFrameLayout shimmerManhwa;
    private TextView tvSeeAllManhwa;
    private ArrayList<NovelModel> manhwaMangaArrayList;
    private NovelRecyclerviewAdapter manhwaRecyclerviewAdapter;

    //top manga
    private RecyclerView recycleViewTopNovel;
    private ShimmerFrameLayout shimmerTopNovel;
    private TextView tvSeeAllTop;
    private ArrayList<NovelModel> topNovelArrayList;
    private TopNovelRecyclerViewAdapter topNovelRecyclerviewAdapter;

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //nếu slide di chuyển đến cuối thì set lại về đầu
            if (viewPager2.getCurrentItem() == slideArrayList.size() - 1) {
                viewPager2.setCurrentItem(0);
            } else {
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

        initNewestData();
        initPopularData();
//        LoadManhua(view);
//        LoadManhwa(view);
//        LoadTopManga(view);


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
//        shimmerManhua.stopShimmer();
//        shimmerManhua.setVisibility(View.GONE);
//        shimmerManhwa.stopShimmer();
//        shimmerManhwa.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        mhandler.removeCallbacks(runnable);
    }

    public void LoadSlide(View view) {
        viewPager2 = view.findViewById(R.id.viewPagerTitle);
        indicator3 = view.findViewById(R.id.indicator3);
        shimmerFrameLayout = view.findViewById(R.id.contentShimmerRecommended);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        slideArrayList = new ArrayList<>();

        viewPagerSlideAdapter = new ViewPagerSlideAdapter(slideArrayList);
        //click vào từng slider
        viewPagerSlideAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailNovelActivity.class);
                intent.putExtra("idNovel", slideArrayList.get(position).getId());
                startActivity(intent);
            }
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

    public void LoadPopular(View view) {
        recycleViewPopulation = view.findViewById(R.id.recycleViewPopulation);
        tvSeeAllPopular = view.findViewById(R.id.btnPopular);
        shimmerPopular = view.findViewById(R.id.contentShimmerPopular);
        shimmerPopular.startShimmer();
        shimmerPopular.setVisibility(View.VISIBLE);
        popularMangaArrayList = new ArrayList<>();


        novelRecyclerviewAdapter = new NovelRecyclerviewAdapter(popularMangaArrayList);
        //click vào từng nút +
        novelRecyclerviewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailNovelActivity.class);
                intent.putExtra("idNovel", popularMangaArrayList.get(position).getId());
                startActivity(intent);
            }
        });
        recycleViewPopulation.setAdapter(novelRecyclerviewAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleViewPopulation.setLayoutManager(layoutManager);

        tvSeeAllPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //truyền qua cho nó key để nhận biết dữ liệu khi dùng chung màn hình
                //vd số 1 là see all popular, số 2 là see all manhhua, số 3 là see all manhwa
                Intent intent = new Intent(getContext(), AllNovelActivity.class);
                intent.putExtra("checkNovel", "popular");
                startActivity(intent);
            }
        });

        shimmerPopular.stopShimmer();
        shimmerPopular.setVisibility(View.GONE);
    }

//    public void LoadManhua(View view){
//        recycleViewManhua = view.findViewById(R.id.recycleViewManhua);
//        tvSeeAllManhua = view.findViewById(R.id.btnManhua);
//        shimmerManhua = view.findViewById(R.id.contentShimmerManhua);
//        shimmerManhua.startShimmer();
//        shimmerManhua.setVisibility(View.VISIBLE);
//        manhuaMangaArrayList = new ArrayList<>();
//
////        Manga slide = new Manga(1, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507319/Mangaworld/MV5BODcwNWE3OTMtMDc3MS00NDFjLWE1OTAtNDU3NjgxODMxY2UyXkEyXkFqcGdeQXVyNTAyODkwOQ_._V1_FMjpg_UX1000__ij1oft.jpg","Vua hải tặc", 30000, "Phiêu lưu");
////        Manga slide1 = new Manga(2, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507145/Mangaworld/Naruto_Volume_1_manga_cover_q2cere.jpg","Naruto", 50000, "Hành động");
////        Manga slide2 = new Manga(3, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507429/Mangaworld/conan_24_-_main_poster_1__vijsfb.jpg","Thám tử lừng danh conan", 80000, "Trinh thám");
////        Manga slide3 = new Manga(4, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507420/Mangaworld/tranh-dan-tuong-3d-cartoon-songoku-scaled_cv67o3.jpg","Bảy viên ngọc rồng Z", 10000, "Hành động");
////        manhuaMangaArrayList.add(slide);
////        manhuaMangaArrayList.add(slide1);
////        manhuaMangaArrayList.add(slide2);
////        manhuaMangaArrayList.add(slide3);
//
//        NovelController controller = new NovelController(LoadActivity.url, getActivity());
//        controller.getMostViewNovel(new IVolleyCallback() {
//            @Override
//            public void onSuccess(String result) {
//                manhuaMangaArrayList.clear();
//                manhuaMangaArrayList.addAll(controller.convertJSONData(result));
//                manhuaRecyclerviewAdapter.notifyDataSetChanged();
//            }
//        });
//
//        manhuaRecyclerviewAdapter = new MangaRecyclerviewAdapter(manhuaMangaArrayList);
//        //click vào từng nút +
//        manhuaRecyclerviewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
//            @Override
//            public void onClick(View view, int position) {
//                Intent intent = new Intent(getContext(), DetailMangaActivity.class);
//                intent.putExtra("idManga", manhuaMangaArrayList.get(position).getId());
//                startActivity(intent);            }
//        });
//        recycleViewManhua.setAdapter(manhuaRecyclerviewAdapter);
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        recycleViewManhua.setLayoutManager(layoutManager);
//
//        tvSeeAllManhua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //truyền qua cho nó 1 chữ số để nhận biết dữ liệu khi dùng chung màn hình
//                //vd số 1 là see all popular, số 2 là see all manhhua, số 3 là see all manhwa
//                Intent intent = new Intent(getContext(), AllMangaActivity.class);
//                intent.putExtra("checkManga", "manhua");
//                startActivity(intent);
//            }
//        });
//
//        shimmerManhua.stopShimmer();
//        shimmerManhua.setVisibility(View.GONE);
//    }
//
//    public void LoadManhwa(View view){
//        recycleViewManhwa = view.findViewById(R.id.recycleViewManhwa);
//        tvSeeAllManhwa = view.findViewById(R.id.btnManhwa);
//        shimmerManhwa = view.findViewById(R.id.contentShimmerManhwa);
//        shimmerManhwa.startShimmer();
//        shimmerManhwa.setVisibility(View.VISIBLE);
//        manhwaMangaArrayList = new ArrayList<>();
//
////        Manga slide = new Manga(1, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507319/Mangaworld/MV5BODcwNWE3OTMtMDc3MS00NDFjLWE1OTAtNDU3NjgxODMxY2UyXkEyXkFqcGdeQXVyNTAyODkwOQ_._V1_FMjpg_UX1000__ij1oft.jpg","Vua hải tặc", 30000, "Phiêu lưu");
////        Manga slide1 = new Manga(2, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507145/Mangaworld/Naruto_Volume_1_manga_cover_q2cere.jpg","Naruto", 50000, "Hành động");
////        Manga slide2 = new Manga(3, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507429/Mangaworld/conan_24_-_main_poster_1__vijsfb.jpg","Thám tử lừng danh conan", 80000, "Trinh thám");
////        Manga slide3 = new Manga(4, "https://res.cloudinary.com/dmfrvd4tl/image/upload/v1652507420/Mangaworld/tranh-dan-tuong-3d-cartoon-songoku-scaled_cv67o3.jpg","Bảy viên ngọc rồng Z", 10000, "Hành động");
////        manhwaMangaArrayList.add(slide);
////        manhwaMangaArrayList.add(slide1);
////        manhwaMangaArrayList.add(slide2);
////        manhwaMangaArrayList.add(slide3);
//
//        NovelController controller = new NovelController(LoadActivity.url, getActivity());
//        controller.getMostViewNovel(new IVolleyCallback() {
//            @Override
//            public void onSuccess(String result) {
//                manhwaMangaArrayList.clear();
//                manhwaMangaArrayList.addAll(controller.convertJSONData(result));
//                manhwaRecyclerviewAdapter.notifyDataSetChanged();
//            }
//        });
//
//        manhwaRecyclerviewAdapter = new MangaRecyclerviewAdapter(manhwaMangaArrayList);
//        //click vào từng nút +
//        manhwaRecyclerviewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
//            @Override
//            public void onClick(View view, int position) {
//                Intent intent = new Intent(getContext(), DetailMangaActivity.class);
//                intent.putExtra("idManga", manhwaMangaArrayList.get(position).getId());
//                startActivity(intent);            }
//        });
//        recycleViewManhwa.setAdapter(manhwaRecyclerviewAdapter);
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        recycleViewManhwa.setLayoutManager(layoutManager);
//
//        tvSeeAllManhwa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //truyền qua cho nó 1 chữ số để nhận biết dữ liệu khi dùng chung màn hình
//                //vd số 1 là see all popular, số 2 là see all manhhua, số 3 là see all manhwa
//                Intent intent = new Intent(getContext(), AllMangaActivity.class);
//                intent.putExtra("checkManga", "manhwa");
//                startActivity(intent);
//            }
//        });
//
//        shimmerManhwa.stopShimmer();
//        shimmerManhwa.setVisibility(View.GONE);
//    }
//
//    private void LoadTopManga(View view) {
//        recycleViewTopNovel = view.findViewById(R.id.recycleViewGenres);
//        tvSeeAllTop = view.findViewById(R.id.btnTop);
//        shimmerTopNovel = view.findViewById(R.id.contentShimmerGener);
//        shimmerTopNovel.startShimmer();
//        shimmerTopNovel.setVisibility(View.VISIBLE);
//        topNovelArrayList = new ArrayList<>();
//
//        NovelController controller = new NovelController(LoadActivity.url, getActivity());
//        controller.getMostViewNovel(new IVolleyCallback() {
//            @Override
//            public void onSuccess(String result) {
//                topNovelArrayList.clear();
//                topNovelArrayList.addAll(controller.convertJSONData(result));
//                topNovelRecyclerviewAdapter.notifyDataSetChanged();
//            }
//        });
//
//        topNovelRecyclerviewAdapter = new TopNovelRecyclerViewAdapter(topNovelArrayList);
//        //click vào từng nút +
//        topNovelRecyclerviewAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
//            @Override
//            public void onClick(View view, int position) {
//                Intent intent = new Intent(getContext(), DetailMangaActivity.class);
//                intent.putExtra("idManga", topNovelArrayList.get(position).getId());
//                startActivity(intent);
//            }
//        });
//        recycleViewTopNovel.setAdapter(topNovelRecyclerviewAdapter);
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        recycleViewTopNovel.setLayoutManager(layoutManager);
//
//        tvSeeAllTop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //truyền qua cho nó 1 chữ số để nhận biết dữ liệu khi dùng chung màn hình
//                //vd số 1 là see all popular, số 2 là see all manhhua, số 3 là see all manhwa
//                Intent intent = new Intent(getContext(), AllMangaActivity.class);
//                intent.putExtra("checkManga", "top");
//                startActivity(intent);
//            }
//        });
//
//        //tắt shimmer
//        shimmerTopNovel.stopShimmer();
//        shimmerTopNovel.setVisibility(View.GONE);
//    }

    private void initNewestData() {
        NovelController controller = new NovelController(LoadActivity.url, getActivity());
        controller.getNewestNovel(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                newestNovelData.clear();
                newestNovelData.addAll(controller.convertJSONData(result));

                slideArrayList.clear();
                for (int i = 0; i < 4; i++) {
                    if (i > newestNovelData.size() - 1)
                        break;

                    slideArrayList.add(newestNovelData.get(i));
                }

                viewPagerSlideAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initPopularData() {
        NovelController controller = new NovelController(LoadActivity.url, getActivity());
        controller.getMostViewNovel(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                popularMangaArrayList.clear();
                ArrayList<NovelModel> tmp = controller.convertJSONData(result);

                for(int i=0;i<4;i++)
                {
                    if (i > tmp.size() - 1)
                        break;

                    popularMangaArrayList.add(tmp.get(i));
                }

                novelRecyclerviewAdapter.notifyDataSetChanged();
            }
        });
    }
}