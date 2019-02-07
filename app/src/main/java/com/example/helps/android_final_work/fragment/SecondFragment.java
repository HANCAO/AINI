package com.example.helps.android_final_work.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.helps.android_final_work.MusicActivity;
import com.example.helps.android_final_work.R;
import com.example.helps.android_final_work.bean.Music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SecondFragment extends Fragment {

    private ListView listView;

    public  ArrayList<Music> musicList = null; //音乐信息列表
    private ArrayList<Map<String, Object>> listems = null;//需要显示在listview里的信息

    private int[] icons={R.drawable.music_icon,R.drawable.music_cover_1};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.music_demo,container,false);

        listView= ((ListView) view. findViewById(R.id.music_list));

        //listViewAdapter=new ListViewAdapter();
        //listView.setAdapter(listViewAdapter);
        musicList  = scanAllAudioFiles();

        listems = new ArrayList<Map<String, Object>>();
        for (Iterator iterator = musicList.iterator(); iterator.hasNext();) {
            Map<String, Object> map = new HashMap<String, Object>();
            Music mp3Info = (Music) iterator.next();
            map.put("title", mp3Info.getTitle());
            map.put("artist", mp3Info.getArtist());
            map.put("album", mp3Info.getAlbum());
            map.put("duration", mp3Info.getTime());
            map.put("size", mp3Info.getSize());
            map.put("url", mp3Info.getUrl());

            map.put("bitmap", icons[0]);

            listems.add(map);

        }

        final SimpleAdapter mSimpleAdapter = new SimpleAdapter(
                getContext(),
                listems,
                R.layout.music_list_demo,
                new String[] {"bitmap","title","artist","duration"},
                new int[] {R.id.cover,R.id.tVMusicName,R.id.tVMusicContent,R.id.duration}
        );
        listView.setAdapter(mSimpleAdapter);
        mSimpleAdapter.notifyDataSetChanged();

        /*getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSimpleAdapter.notifyDataSetChanged();
            }
        });*/

        //mSimpleAdapter.notifyDataSetChanged();

        //ListView item的点击事件
        clickListViewItem(view);

        return view;
    }

    private void clickListViewItem(View view) {

        listView= ((ListView) view. findViewById(R.id.music_list));
        //ListView item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /*currPosition=position;
                playMusicItem(currPosition);*/
                Intent intent=new Intent(getContext(),MusicActivity.class);
                intent.putExtra("music_url",musicList.get(position).getUrl());
                intent.putExtra("music_title",musicList.get(position).getTitle());
                intent.putExtra("music_artist",musicList.get(position).getArtist());
                intent.putExtra("music_album",musicList.get(position).getAlbum());
                intent.putExtra("music_size",musicList.get(position).getSize());

                startActivity(intent);
            }
        });
    }

    public ArrayList<Music> scanAllAudioFiles(){
        ArrayList<Music> musicArrayList=new ArrayList<Music>();

        Cursor cursor=getContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                int id=cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                String title=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String artist=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String url=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                int time=cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                Long size=cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                int albumId=cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                String album=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));

                if(size>800*1024){
                    Music music=new Music();
                    music.setId(id);
                    music.setTitle(title);
                    music.setArtist(artist);
                    music.setUrl(url);
                    music.setTime(time);
                    music.setSize(size);
                    music.setAlbumId(albumId);
                    music.setAlbum(album);

                    musicArrayList.add(music);
                }

                cursor.moveToNext();
            }
        }
        return musicArrayList;
    }
}
