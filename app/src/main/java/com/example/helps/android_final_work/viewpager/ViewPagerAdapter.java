package com.example.helps.android_final_work.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
    }
    public ViewPagerAdapter(FragmentManager fm,List<Fragment>fragments){
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public int getCount(){
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
