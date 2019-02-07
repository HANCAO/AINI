package com.example.helps.android_final_work;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helps.android_final_work.services.MusicService;

public class MusicActivity extends AppCompatActivity {
    private static final String TAG = MusicActivity.class.getSimpleName();
    private ImageButton imageButtonPlayMusic,imageButtonPauseMusic;
    private TextView tVMusicTitle;
    private MusicService.MusicBinder binder;
    private ServiceConnection musicConn;
    private String music_url,music_title;
    private Intent intent;
    private long firstPressedTime;//第一次点击按钮时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        imageButtonPlayMusic= ((ImageButton) findViewById(R.id.imageButtonPlayMusic));
        imageButtonPauseMusic= ((ImageButton) findViewById(R.id.imageButtonPauseMusic));

        tVMusicTitle = (TextView) findViewById(R.id.tVTitle);
        
        music_url=getIntent().getStringExtra("music_url");
        music_title=getIntent().getStringExtra("music_title");

        initUI();
    }

    private void initUI() {
        tVMusicTitle.setText(music_title);
    }

    public void playMusic(View view) {

        if(musicConn==null){
            musicConn=new ServiceConnection(){

                @Override
                public void onServiceConnected(ComponentName componentName, IBinder service) {
                    binder=(MusicService.MusicBinder)service;
                    Log.i(TAG, "binder is: "+binder+music_url);
                    if(!MusicService.mediaPlayer.isPlaying())
                        binder.plays(music_url);
                    else{
                        binder.stops();
                        binder.plays(music_url);
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    Toast.makeText(getApplicationContext(),"这是干啥的了？",Toast.LENGTH_LONG).show();
                }
            };
        }else{
            /*if(System.currentTimeMillis()-firstPressedTime<2000){
                return;
            }else{
                Toast.makeText(getApplicationContext(),"不要点人家啦~",Toast.LENGTH_SHORT).show();
                firstPressedTime=System.currentTimeMillis();
            }*/
            if(MusicService.mediaPlayer.isPlaying())
                binder.plays(music_url);
            else
                binder.pauses();
        }

        intent=new Intent(this,MusicService.class);

        bindService(intent,musicConn,BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(musicConn!=null)
            unbindService(musicConn);
    }

    public void pauseMusic(View view) {
        if(musicConn!=null)
            binder.pauses();
        else{
            if(System.currentTimeMillis()-firstPressedTime<2000){
                return;
            }else{
                Toast.makeText(getApplicationContext(),"音乐没播放不用暂停~",Toast.LENGTH_SHORT).show();
                firstPressedTime=System.currentTimeMillis();
            }
        }
    }

    public void resetMusic(View view) {
        //if(MusicService.mediaPlayer.isPlaying())
        if(musicConn!=null)
            binder.resumes(music_url);
        else{
            if(System.currentTimeMillis()-firstPressedTime<2000){
                return;
            }else{
                Toast.makeText(getApplicationContext(),"你先播放试试？",Toast.LENGTH_SHORT).show();
                firstPressedTime=System.currentTimeMillis();
            }
        }
    }
}
