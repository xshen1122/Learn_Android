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
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置通知铃声");
        if (myUriStr != null) {
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(myUriStr));
        }//选中的内容放到myUriStr里面。
        startActivityForResult(intent, 0);




    }
    //用来将选中的铃声url保存下来。主要是这句：myUriStr = pickedUri.toString();
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
