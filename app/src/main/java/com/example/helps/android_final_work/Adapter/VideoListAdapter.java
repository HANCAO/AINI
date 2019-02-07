package com.example.helps.android_final_work.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.helps.android_final_work.R;
import com.example.helps.android_final_work.bean.VideoConstant;
import com.squareup.picasso.Picasso;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoListAdapter extends BaseAdapter {
    private static final String TAG = VideoListAdapter.class.getSimpleName();
    int[] videoIndexs={0,1,2,3,4,5};
    Context context;
    int pager=-1;

    public VideoListAdapter(Context context) {
        this.context=context;
    }

    public VideoListAdapter(Context context,int pager) {
        this.context=context;
        this.pager=pager;
    }

    @Override
    public int getCount() {
        return pager==-1?videoIndexs.length:2;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(null==convertView){
            viewHolder=new ViewHolder();
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(R.layout.video_list,null);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.jcVideoPlayerStandard=convertView.findViewById(R.id.videoplayer);
        if(pager==-1){
            viewHolder.jcVideoPlayerStandard.setUp(
                    VideoConstant.mVideoUrls[0][position],JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    VideoConstant.mVideoTitles[0][position]);
            Log.i(TAG, "setup "+position);
            Picasso.with(convertView.getContext())
                    .load(VideoConstant.mVideoThumbs[0][position])
                    .into(viewHolder.jcVideoPlayerStandard.thumbImageView);
        }else{
            viewHolder.jcVideoPlayerStandard.setUp(
                    VideoConstant.mVideoUrls[pager][position],JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    VideoConstant.mVideoTitles[pager][position]);
            Picasso.with(convertView.getContext())
                    .load(VideoConstant.mVideoThumbs[pager][position])
                    .into(viewHolder.jcVideoPlayerStandard.thumbImageView);
        }

        return convertView;
    }


    class ViewHolder {
        JCVideoPlayerStandard jcVideoPlayerStandard;
    }
}
