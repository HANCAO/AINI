package com.example.helps.android_final_work.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class MusicService extends Service {
    private static final String TAG = MusicService.class.getSimpleName();
    public static MediaPlayer mediaPlayer;

    public class MusicBinder extends Binder{
        /**判断是否正在播放
         *
         */
        public boolean isPlaying(){
            return mediaPlayer.isPlaying();
        }
        /**
         * 设置歌曲播放进度，单位毫秒
         */
        public void seekTo(int mesc){
            mediaPlayer.seekTo(mesc);
        }
        /**
         * 播放音乐
         */
        public void plays(String path){
            play(path);
        }
        /**
         * 暂停音乐
         */
        public void pauses(){
            pause();
        }
        /**
         * 重新播放
         */
        public void resumes(String path){
            resume(path);
        }
        //停止播放
        public void stops(){
            stop();
        }
        //获取当前播放进度
        public int getCurrentPosition(){
            return getCurrentProgress();
        }
        //获取音乐文件长度
        public int getMusicWidth(){
            return getMusicLength();
        }
    }

    public MusicService() {
        mediaPlayer=new MediaPlayer();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            //mediaPlayer=null;
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }

    private int getMusicLength() {
        //if(mediaPlayer!=null){
            return mediaPlayer.getDuration();
        //}
        //return 0;
    }

    private void stop() {
        if(mediaPlayer!=null){
            Log.i(TAG, "停止播放");
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }else{
            Toast.makeText(getApplicationContext(),"已经停止",Toast.LENGTH_SHORT).show();
        }
    }

    private void resume(String path) {
        if(mediaPlayer!=null){
            Log.i(TAG, "重新开始播放");
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        }
    }

    private void pause() {
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            Log.i(TAG, "播放暂停");
            mediaPlayer.pause();
        }else if(mediaPlayer!=null&&(!mediaPlayer.isPlaying())){
            mediaPlayer.start();
        }
    }

    private void play(String path) {
        try {
            if(mediaPlayer!=null){
                //指定参数为音频文件
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                //指定播放路径
                mediaPlayer.setDataSource(path);
                //准备播放
                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        //开始播放
                        mediaPlayer.start();
                    }
                });
            }else{
                int position=getCurrentProgress();
                mediaPlayer.seekTo(position);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前进度
     * @return 音乐文件当前进度
     */
    private int getCurrentProgress() {
        try {
            if(mediaPlayer!=null){
                if(mediaPlayer.isPlaying()){
                    Log.i(TAG, "获取当前进度"+mediaPlayer.getCurrentPosition());
                    return mediaPlayer.getCurrentPosition();
                }else if(!mediaPlayer.isPlaying()){
                    return mediaPlayer.getCurrentPosition();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
