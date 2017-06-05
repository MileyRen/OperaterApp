package com.operaterapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.operaterapp.util.BaseConst;
import com.operaterapp.util.ValidateUtils;


public class UserActivity extends AppCompatActivity {

    TextView user_name,user_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        user_name = (TextView)findViewById(R.id.userName);
        user_no = (TextView) findViewById(R.id.user_no);

        SharedPreferences msg = getSharedPreferences(BaseConst.USER_MESSAGE,MODE_PRIVATE);
        Integer userId = msg.getInt(BaseConst.USER_ID,0);
        if(ValidateUtils.isLogin(userId)){

        String userNo = msg.getString(BaseConst.USER_NO,"");
        String userName = msg.getString(BaseConst.USER_NAME,"");
        user_name.setText(userName);
        user_no.setText(userNo);}
    }
}
