package com.example.helps.android_final_work.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helps.android_final_work.R;
import com.example.helps.android_final_work.bean.ImageResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FirstFragment extends Fragment {

    private static final String TAG = FirstFragment.class.getSimpleName();
    private ImageView imageView;
    private TextView tVCopyright;
    private TextView tVTitle;
    private TextView tVDate;
    private TextView tVTheme;
    private ImageResult imageResult;
    protected static final int SUCCESS=1;
    protected static final int FALL=2;

    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                //加载网络成功进行UI的更新，处理得到图片资源
                case SUCCESS:
                    byte[] picture=(byte[])msg.obj;
                    Bitmap bitmap=BitmapFactory.decodeByteArray(picture,0,picture.length);
                    imageView.setImageBitmap(bitmap);
                    break;
                case FALL:
                    Toast.makeText(getContext(),"显示图片失败",Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        //获取布局文件
        View view=inflater.inflate(R.layout.fragment_first,container,false);
        imageView=(ImageView)view.findViewById(R.id.imageView);
        tVCopyright=(TextView)view.findViewById(R.id.tVCopyright);
        tVDate=(TextView)view.findViewById(R.id.tVDate);
        tVTitle=(TextView)view.findViewById(R.id.tVTitle);
        tVTheme=(TextView)view.findViewById(R.id.tVTheme);

        Typeface typeface=Typeface.createFromAsset(view.getContext().getAssets(),"STXINGKA.TTF");
        tVTheme.setTypeface(typeface);
        
        getandsetIntentImageInfo();
        
        return view;
    }

    private void getandsetIntentImageInfo() {
        String url_essay="https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";

        OkHttpClient client = new OkHttpClient();
        final Gson gson = new Gson();
        Request request = new Request.Builder()
                .url(url_essay)
                .build();

        imageResult=new ImageResult();
        /**
         * Okhttp对象的方法
         */
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String returnMessage=response.body().string();
                Log.i(TAG, "返回的结果为：\n"+returnMessage);

                Map map = gson.fromJson(returnMessage, Map.class);

                List<Map> images=(List<Map>)map.get("images");
                String date=(String)images.get(0).get("enddate");
                String url=(String)images.get(0).get("url");
                String copyright=(String)images.get(0).get("copyright");
                String title=(String)images.get(0).get("title");

                imageResult.setCopyright(copyright);
                imageResult.setDate(date);
                imageResult.setTitle(title);
                imageResult.setUrl(url);

                showIntentImage("http://s.cn.bing.net"+imageResult.getUrl());
                
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tVCopyright.setText(imageResult.getCopyright());
                        tVDate.setText(imageResult.getDate());
                        tVTitle.setText(imageResult.getTitle());
                    }
                });
            }
        });
    }

    private void showIntentImage(String imageurl) {
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(imageurl).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] picture=response.body().bytes();
                Message message=handler.obtainMessage();
                message.obj=picture;
                message.what=SUCCESS;
                handler.sendMessage(message);
            }
        });
    }
}
