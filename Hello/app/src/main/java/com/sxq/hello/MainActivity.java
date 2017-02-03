package com.sxq.hello;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private Button mButton;//通过界面上的Button控件来执行任务。方法放在button的侦听方法中。一般会把控件放在成员变量中。
    private Button mButton1;
    private Button mButton2;
    private MediaPlayer mMediaPlayer;
    private String myUriStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button)findViewById(R.id.button);//注意，这一条不能写在onCreate方法外面。
        mButton1 = (Button)findViewById(R.id.button2);
        mButton2 = (Button)findViewById(R.id.button3);

        mButton.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {

                Toast.makeText(MainActivity.this,"clicked",Toast.LENGTH_SHORT).show();
                try {
                    toPlay();//把播放作为一个方法，在Button的侦听方法里调用。还要注意try的用法。
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });
        mButton1.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {

                toStop();//停止播放


            }

        });
        mButton2.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {

                toChoose();//弹出选择框，并选择。


            }

        });


    }
    // toChoose()方法才4,5行，加上后面的onActivityResult，加起来不到10行，用处很大。
    public void toChoose(){
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);//通知
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置通知铃声");
        if (myUriStr != null) {
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(myUriStr));
        }//将若干个Ringtone显示出来。
        startActivityForResult(intent, 0); //开启第二个Activity（铃声选择）




    }
    //用来将选中的铃声url保存下来。主要是这句：myUriStr = pickedUri.toString();
    //选中铃声，点击“确定”按钮之后，就会立即回调onActivityResult()方法
    /**
     * 设置铃声之后的回调函数
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            myUriStr = pickedUri.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void toPlay() throws IOException {
        if (myUriStr == null) {//如果未自定义铃声，则调用系统默认的铃声
            mMediaPlayer = MediaPlayer.create(this, getSystemDefaultRingtoneUri());
        } else {
            mMediaPlayer = MediaPlayer.create(this, Uri.parse(myUriStr));

            if(mMediaPlayer == null){
                Toast.makeText(this,"该铃声不存在，请重新选择",Toast.LENGTH_LONG).show();
                return;
            }
        }
        mMediaPlayer.setLooping(false);

        try {
            mMediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer.start();
            }
        });
    }
    private Uri getSystemDefaultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE);
    }
    public void toStop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }
}
/*
要点1：铃声管理
Android RingtoneManager铃声管理
android中的铃声通过RingtoneManager管理，RingtoneManager
1. 管理来电铃声（TYPE_RINGTONE）
2. 提示音（TYPE_NOTIFICATION）
3.闹钟铃声（TYPE_ALARM）等
================RingtoneManager的常用方法包括：
1.getRingtone()    //获取铃声
2.getDefaultUri()    //获取某一铃声类型的默认铃声
3.setActualDefaultRingtoneUri()  //为某一铃声类型设置默认铃声
4.getActualDefaultRingtoneUri(); //获取默认铃声

要点2 Intent
Android中提供一个叫Intent的类来实现屏幕之间的跳转
1， 先定义一个Intent
2， 把内容加进去
3， 启动该Activity

4， Activity的回调，当用户点击“确认”后完成的动作。






 */