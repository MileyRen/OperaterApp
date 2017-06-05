package com.operaterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.operaterapp.util.BaseConst;


public  class BaseActivity extends AppCompatActivity {

    public void logout(){
        SharedPreferences.Editor editor = getSharedPreferences(BaseConst.USER_MESSAGE,MODE_PRIVATE).edit();
        editor.putInt(BaseConst.USER_ID,0);
        editor.putString(BaseConst.USER_NO,"");
        editor.putString(BaseConst.USER_NAME,"");
        editor.commit();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LOGIN");
        startActivity(intent);
    }
    public void getUser(){
        Intent intent = new Intent("android.intent.action.GET_USER");
        intent.addCategory("android.intent.category.GET_USER");
        startActivity(intent);
    }
}
