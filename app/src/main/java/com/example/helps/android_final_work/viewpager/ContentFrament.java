package com.example.helps.android_final_work.viewpager;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.helps.android_final_work.MainActivity;
import com.example.helps.android_final_work.R;


public class ContentFrament extends Fragment {
    private int[]bgRes={R.drawable.f_pictue,R.drawable.e_pictue,R.drawable.s_picrue};//轮播图
    private AlphaAnimation alphaAnimation;//启动页动画
    private Button btn;//点击启动Button
    private RelativeLayout rl;//定义整个布局
    private int index;//轮播图ID号
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_content,null);
        btn=(Button)view.findViewById(R.id.btn);
        rl = (RelativeLayout)view.findViewById(R.id.rl);
        index = getArguments().getInt("index");

        alphaAnimation=new AlphaAnimation(0.3f,1.0f);
        alphaAnimation.setDuration(3000);
        view.startAnimation(alphaAnimation);

        rl.setBackgroundResource(bgRes[index]);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        btn.setVisibility(index==2?View.VISIBLE:View.GONE);

        return view;
    }
}
