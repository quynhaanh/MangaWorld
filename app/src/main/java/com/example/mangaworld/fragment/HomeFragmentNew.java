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