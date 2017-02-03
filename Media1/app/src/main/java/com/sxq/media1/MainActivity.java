package com.sxq.media1;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity  {
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        btn = (Button) findViewById(R.id.button);
        //在create界面事仅仅产生surfaceView和button
        //原因是在surfaceHolder还没准备好的时候，我就调用了MediaPlayer的start()方法。
        btn.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"clicked",Toast.LENGTH_SHORT).show();
                play();
            }

        });
    }


    /**
     * 播放音乐
     */
    private void play() {

            try {
                //过程如下：
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                /* 设置Video影片以SurfaceHolder播放 */
                mediaPlayer.setDisplay(surfaceView.getHolder());

                //mediaPlayer.setDataSource("http://192.168.1.106:8080/static/1.mp4");
                mediaPlayer.setDataSource("http://192.168.1.106:8080/static/movie/movie.m3u8");
                mediaPlayer.prepare(); // might take long! (for buffering, etc)
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        btn.setEnabled(true);
                    }
                } );

                btn.setEnabled(false);//一旦开始播放，play 按钮置为"不可点击"
            } catch (Exception e) {
                Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


    }

}
/*
如何使用SurfaceView, SurfaceHolder
跟你说简单点吧，surface是用来绘图的，比如照相录像界面，
而surfaceView是用来显示surface所绘制的图像，
你可以通过SurfaceHolder来访问这个surface，
而SurfaceView.getHolder()方法就是用来返回SurfaceHolder对象以便访问surface,
而holder.addCallback(this)是因为当前Activity实现了一个接口SurfaceHolder.Callback，
所以this也是Callback的一个对象，在surface的各个生命周期(create change destroy)中会调用你重写的那三个方法。
正因为你调用了holder.addCallback(this)，就将当前重写的三个方法与surface关联起来了

Player.Java是本文的核心，Player.java实现了“进度条更新”、“数据缓冲”、“SurfaceHolder生命周期”等功能，
其中“SurfaceHolder生命周期”是视频与音频播放的最大区别，通过surfaceCreated()、surfaceDestroyed()、surfaceChanged()
可以创建/释放某些资源。

以上为最简单的播放.抛开了上面复杂的SurfaceHolder用法。
一个surfaceview + 一个play button
1. 在onCreate方法中，获取控件id
2. 在onCreate方法中，侦听play button ，如果用户点击，则执行play()方发

3. play()方法完成创建MediaPlayer，并将其显示在surfaceView上。
 */