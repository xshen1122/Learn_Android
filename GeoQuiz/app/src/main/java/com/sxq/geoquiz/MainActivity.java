package com.sxq.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private final static String KEY_INDEX = "index";
    private Button btn1;
    private Button btn2;
   // private Button btn3;
   // private Button btn4;
    private ImageButton btn3;
    private ImageButton btn4;
    private TextView tv;
    private boolean userPressedTrue;
//    private String[] list = {"Haikou is in Hainan Province?",
//                              "Shanghai is the largest city in China?",
//                              "Changjiang is the longest river in the world?",
//                              "Beijing has more than 40 million people",
//                              "Mohe is the northest city in China?"};
//    private String[] answer = {"yes","no","no","no","yes"};
//    private Stack player = new Stack();
//    private Stack ques = new Stack();
//    private int number = 0;
//    private String correct = "";
//    private int score = 0;
//在这里写死了，TrueFalse数组的顺序。
    private TrueFalse[] bank = new TrueFalse[]{
            new TrueFalse(R.string.question_city,true),
            new TrueFalse(R.string.question_country,false),
            new TrueFalse(R.string.question_river,false)
    };
    private int index = 0;



    public void onSavedInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.d("SXQ","onSavedInstancedState");
        savedInstanceState.putInt(KEY_INDEX,index);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("SXQ","onCreate is called.");
        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (ImageButton) findViewById(R.id.imageButton);
        btn4 = (ImageButton) findViewById(R.id.imageButton2);
        tv = (TextView) findViewById(R.id.tv);
        //push_stack();
        if(savedInstanceState != null){
            index = savedInstanceState.getInt(KEY_INDEX);
        }
       update_question();



        tv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                index = (index + 1)%bank.length; //为了保证循环显示
                update_question();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                userPressedTrue = true;
                check_answer();

//                if (correct.equals("yes")){
//                    //Toast.makeText(MainActivity.this,"You are Correct",Toast.LENGTH_SHORT).show();
//                    score = score+ 20;
//                }

                //
            }

        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                userPressedTrue = false;
                check_answer();
//                if(correct.equals("no")){
//                    //Toast.makeText(MainActivity.this,"You are Corrct",Toast.LENGTH_SHORT).show();
//                    score = score + 20;
//                }
                //Toast.makeText(MainActivity.this,"You are Wrong",Toast.LENGTH_SHORT).show();
            }

        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                index = (index + 1)%bank.length; //为了保证循环显示
                update_question();
                btn4.setEnabled(true);

//                //do something here.
//                number ++;
//                if (number == 6){
//                    tv.setText("GAME OVER,your score is " + Integer.toString(score));
//                    btn3.setEnabled(false);
//
//                }
//                else
//                {
//                tv.setText(ques.pop().toString());
//                correct = player.pop().toString();
//                }
//                //Toast.makeText(MainActivity.this,"You are Wrong",Toast.LENGTH_SHORT).show();
            }

        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Log.d("SXQ",Integer.toString(index));

                index = Math.abs((index - 1))%bank.length; //为了保证循环显示
                update_question();

//                if(correct.equals("no")){
//                    //Toast.makeText(MainActivity.this,"You are Corrct",Toast.LENGTH_SHORT).show();
//                    score = score + 20;
//                }
                //Toast.makeText(MainActivity.this,"You are Wrong",Toast.LENGTH_SHORT).show();
            }

        });

    }
//    public void push_stack(){
//        for (int i = 0;i<5;i++){
//            player.push(answer[i]);
//            ques.push(list[i]);
//        }
//
//    }
    public void update_question(){
        int question = bank[index].getQuestion();
        tv.setText(question);//因为question已经写死在strings.xml里面了，所以只需要提供index即可。

    }
    public void check_answer(){
        boolean ans = bank[index].getTrueQuestion();
        String msgId = "";
        if(userPressedTrue == ans)
            msgId="Correct";
        else
            msgId = "Wrong";
        Toast.makeText(this, msgId,Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("SXQ","onStart is called.");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SXQ","onPause is called.");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("SXQ","onRestart is called");

    }
    protected  void onResume(){
        super.onResume();
        Log.d("SXQ","onResume is called");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("SXQ","onDestroy is called.");
    }

    @Override
    protected  void onStop(){
        super.onStop();
        Log.d("SXQ","onStop is called");
    }



}

/*
1. 用Stack更方便，用pop方法就可以连续取出元素
2. 用List的话，还要用循环来弄，不方便
3. 事件驱动，应用等待某个特定事件的发生，也可以说该应用正在“监听”特定事件。为响应某个事件而
创建的对象叫做监听器（listener）。监听器是实现特定监听器接口的对象，用来监听某类事件的
发生。
4. 无需自己编写，只需要实现View.OnClickListener接口。
本书所有的监听器都作为匿名内部类来实现。这样做的好处有二。其一，在大量代码块中，
监听器方法的实现一目了然；其二，匿名内部类的使用只出现在一个地方，因此可以减少一些命
名类的使用。

5. 用了MVC方法，创建了TrueFalse类。然后bank是TrueFalse类数组。

6. 封装update_question()方法，为了减少冗余代码

7. 封装check_answer()方法，在点击Yes button和点击 NO button时均有使用。
 */