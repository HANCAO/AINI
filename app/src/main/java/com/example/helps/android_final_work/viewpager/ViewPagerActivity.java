package com.example.helps.android_final_work.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.example.helps.android_final_work.R;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerActivity extends FragmentActivity{
    private ViewPager viewPager;
    private LinearLayout llIndicator;

    private PagerAdapter adapter;
    private List<Fragment>fragments= new ArrayList<Fragment>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        viewPager=(ViewPager) findViewById(R.id.viewPager);
        llIndicator=(LinearLayout) findViewById(R.id.ll_indicator);


        //创建fragment
        for (int i=0;i<3;i++){
            ContentFrament frament= new ContentFrament();
            Bundle bundle = new Bundle();
            bundle.putInt("index",i);
            frament.setArguments(bundle);
            fragments.add(frament);

        }
       adapter= new ViewPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);


        initlndicator();
    }

    private void initlndicator() {

        for (int i=0;i<fragments.size();i++){
            View view = new View(this);
        }
    }
}
