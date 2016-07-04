package com.kevin.zwk.kt_0701_yinshipin_1;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_play;
    private String path;
    private Context mContext = this;
    private Button button_stop;
    private MediaPlayer player;
    private ListView listView;
    private SeekBar seekBar;
    private TextView time;
    private TextView time2;
    private List<MusicBean> allAudioPaths = new ArrayList<>();
    private ContentResolver contentResolver;
    private SimpleDateFormat format = new SimpleDateFormat("mm:ss");
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (player != null) {
                        seekBar.setMax(player.getDuration());
                        String stime = format.format(new Date(player.getDuration()));
                        time.setText("总时长：" + stime);
                    }
                    break;
                case 2:
                    if (player != null) {

                        seekBar.setProgress(player.getCurrentPosition());
                        String stime2 = format.format(new Date(player.getCurrentPosition()));
                        time2.setText("已播放：" + stime2);
                        Log.i("tag", "------" + player.getCurrentPosition());
                        handler.sendEmptyMessageDelayed(2, 1000);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentResolver = getContentResolver();
        time = (TextView) findViewById(R.id.time);
        time2 = (TextView) findViewById(R.id.time2);
        button_play = (Button) findViewById(R.id.button_play);
        button_stop = (Button) findViewById(R.id.button_stop);
        listView = (ListView) findViewById(R.id.listView);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        button_play.setOnClickListener(this);
        button_stop.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser == true) {
                    player.seekTo(progress);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        String[] data = new String[]{MediaStore.Audio.Media._ID
                , MediaStore.Audio.Media.DISPLAY_NAME
                , MediaStore.Audio.Media.DATA
                , MediaStore.Audio.Media.DURATION};
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                , data, null, null, null);

        while (cursor.moveToNext()) {
            Log.d("", "音频的id" + cursor.getString(0) + "   音频名称" + cursor.getString(1) + "   音频绝对路径" + cursor.getString(2));
            allAudioPaths.add(new MusicBean(cursor.getString(1), cursor.getString(2)));
        }


        MyAdapter adapter = new MyAdapter(mContext, allAudioPaths);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                path = allAudioPaths.get(position).getPath();
                if (player != null) {
                    player.stop();
                    //释放
                    player.release();
                    player = null;
                }
                if (player == null) {
                    play();
                }


            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_play:
                if (player == null) {
                    play();


                } else {
                    Snackbar.make(v, "正在播放", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_stop:
                if (player != null) {
                    player.stop();
                    //释放
                    player.release();
                    player = null;
                } else {
                    Snackbar.make(v, "已经停止", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void play() {
        //创建mediaplay

        player = new MediaPlayer();
        //重置，第一次可以不用重置
        player.reset();
        if (path != null) {
            try {
                //设置要播放文件的路径

                player.setDataSource(path);
                //player准备好的监听，prepared 准备
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        Log.i("tag", "------" + mp.getDuration());
                        handler.sendEmptyMessage(1);
                        handler.sendEmptyMessage(2);

                    }
                });
                //异步加载
                player.prepareAsync();
            } catch (
                    IOException e
                    )

            {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(MainActivity.this, "请选择歌曲", Toast.LENGTH_SHORT).show();
        }
    }


}
