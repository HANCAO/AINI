package com.example.helps.android_final_work;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.helps.android_final_work.fragment.FirstFragment;
import com.example.helps.android_final_work.fragment.SecondFragment;
import com.example.helps.android_final_work.fragment.ThirdFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationBar bottom_navigation_bar;
    private int lastSelectedPosition=1;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;

    private long firstPressedReturnTime;//定义第一次点击Return键的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"},0);
        ActivityCompat.requestPermissions(this,new String[]{"android.permission.READ_EXTERNAL_STORAGE"},0);

        bottom_navigation_bar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        initBottomNavigationBar();

    }

    private void setDefaultFragment(){
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();

        firstFragment=new FirstFragment();
        transaction.add(R.id.main_frame_layout,firstFragment);
        transaction.commit();
    }

    private void initBottomNavigationBar() {

        bottom_navigation_bar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottom_navigation_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);

        //设置默认颜色
        bottom_navigation_bar
                .setInActiveColor(R.color.colorInActive)//设置未选中的Item的颜色，包括图片和文字
                .setActiveColor(R.color.colorPrimary)
                .setBarBackgroundColor(R.color.colorBarBg);//设置整个控件的背景色        ;
        //添加选项
        bottom_navigation_bar.addItem(new BottomNavigationItem(R.drawable.music, ""))
                .addItem(new BottomNavigationItem(R.drawable.az_action, "").setInactiveIcon(ContextCompat.getDrawable(this,R.drawable.az_sleep)))
                .addItem(new BottomNavigationItem(R.drawable.video, ""))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();//初始化BottomNavigationButton,所有设置需在调用该方法前完成


        setDefaultFragment();

        bottom_navigation_bar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                lastSelectedPosition=position;
                transaction=fragmentManager.beginTransaction();
                hideFragment(transaction);

                switch (position){
                    case 1:
                        if(firstFragment==null){
                            firstFragment=new FirstFragment();
                            transaction.add(R.id.main_frame_layout,firstFragment);
                        }else{
                            transaction.show(firstFragment);
                        }
                        break;
                    case 0:
                        if(secondFragment==null){
                            secondFragment=new SecondFragment();
                            transaction.add(R.id.main_frame_layout,secondFragment);
                        }else{
                            transaction.show(secondFragment);
                        }
                        break;
                    case 2:
                        if(thirdFragment==null){
                            thirdFragment=new ThirdFragment();
                            transaction.add(R.id.main_frame_layout,thirdFragment);
                        }else{
                            transaction.show(thirdFragment);
                        }
                        break;
                }
                transaction.commit();
            }

            @Override
            public void onTabUnselected(int position) {//选中 -> 未选中
            }

            @Override
            public void onTabReselected(int position) {//选中 -> 选中
            }
        });

    }

    private void hideFragment(FragmentTransaction transaction) {
        if(firstFragment!=null){
            transaction.hide(firstFragment);
        }
        if(secondFragment!=null){
            transaction.hide(secondFragment);
        }
        if(thirdFragment!=null){
            transaction.hide(thirdFragment);
        }
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-firstPressedReturnTime<2000){
            super.onBackPressed();
        }else{
            Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
            firstPressedReturnTime=System.currentTimeMillis();
        }
    }

    //在Fragment中实现监听返回键
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            if(thirdFragment instanceof ThirdFragment){
                ((ThirdFragment)thirdFragment).onBackPressed();
            }
        }
        return super.onKeyDown(keyCode,event);
    }
}
