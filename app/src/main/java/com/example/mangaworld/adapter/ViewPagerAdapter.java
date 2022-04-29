package com.example.mangaworld.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> list;
    public ViewPagerAdapter(List<Fragment> fragmentList, @NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.list = fragmentList;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void removeAllFragment(){
        for(int i=0; i<list.size(); i++){
            list.get(i).onDestroy();
        }
    }
}
