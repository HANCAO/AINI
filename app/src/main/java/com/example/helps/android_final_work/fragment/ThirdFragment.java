package com.example.helps.android_final_work.fragment;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.helps.android_final_work.Adapter.VideoListAdapter;
import com.example.helps.android_final_work.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

import static android.content.Context.SENSOR_SERVICE;


public class ThirdFragment extends Fragment {
    private static final String TAG = ThirdFragment.class.getSimpleName();
    private ListView videoListView;
    private VideoListAdapter videoListAdapter;
    private SensorEventListener sensorEventListener;
    private SensorManager sensorManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_third,container,false);
        videoListView= (ListView) (view.findViewById(R.id.video_list));

        videoListAdapter=new VideoListAdapter(getContext());

        videoListView.setAdapter(videoListAdapter);
        sensorManager=(SensorManager)getContext().getSystemService(SENSOR_SERVICE);
        sensorEventListener=new JCVideoPlayer.JCAutoFullscreenListener();
        return view;
    }

    /*public void onBackPressed(){
        if(JCVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed();
    }*/
    //按下返回键以后
    public void onBackPressed(){
        if(JCVideoPlayer.backPress()){
            return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
        JCVideoPlayer.releaseAllVideos();
        Log.i(TAG, "onPause: ");
    }
}
