package com.sxq.geoquiz;

/**
 * Created by RealPlayerHD on 2017/2/3.
 */
public class TrueFalse {
    private int mQuestion;
    private boolean mTrueQuestion;
    //构造函数
    public TrueFalse(int question, boolean answer){
        mQuestion = question;
        mTrueQuestion = answer;
    }
    public int getQuestion(){
        return mQuestion;
    }
    public boolean getTrueQuestion(){
        return mTrueQuestion;
    }
    public void setQuestion(int question){
        mQuestion=question;
    }
    public void setTrueQuestion(boolean answer){
        mTrueQuestion = answer;
    }
}


/*

提出了MVC设计模式；Model-View-Controller
 */