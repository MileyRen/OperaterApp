package com.operaterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.operaterapp.util.BaseConst;
import com.operaterapp.util.HttpURLConnUtil;
import com.operaterapp.util.ValidateUtils;


public class MenuActivity extends BaseActivity implements View.OnClickListener {

    TextView userNameText;
    Button machine_list, update_channel,bnLogout,bnUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        isLogin();
        machine_list = (Button)findViewById(R.id.machine_list);
        update_channel = (Button)findViewById(R.id.update_channel);
        bnUser = (Button)findViewById(R.id.bnUser);
        bnLogout = (Button)findViewById(R.id.bnLogout);

        machine_list.setOnClickListener(this);
        update_channel.setOnClickListener(this);
        bnUser.setOnClickListener(this);
        bnLogout.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.machine_list:
                String machines = getAllMachine();
                Intent intent = new Intent(MenuActivity.this, MachineActivity.class);
                intent.putExtra(BaseConst.ALL_MACHINES, machines);
                startActivity(intent);
                break;
            case R.id.bnLogout:
                logout();
                break;
            case R.id.bnUser:
                getUser();
                break;
            case R.id.update_channel:
                Intent update = new Intent(this,UpdateChannelActivity.class);
                startActivity(update);
                break;
            default:break;
        }
    }
    //查找所有售货机
    public String getAllMachine(){
        SharedPreferences msg = getSharedPreferences(BaseConst.USER_MESSAGE,MODE_PRIVATE);
        int userId = msg.getInt(BaseConst.USER_ID,0);
        String url = BaseConst.GET_MACHINES.replace(":userId", userId+"");
        String response = null;
        try {
            response = HttpURLConnUtil.getByResponse(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  response;
    }

    //判断用户是否登录
    public boolean isLogin(){
        SharedPreferences msg = getSharedPreferences(BaseConst.USER_MESSAGE,MODE_PRIVATE);
        Integer userId = msg.getInt(BaseConst.USER_ID,0);
        Boolean response =  ValidateUtils.isLogin(userId);
        if(response){
            userNameText = (TextView)findViewById(R.id.userName);
            userNameText.setText("欢迎"+msg.getString(BaseConst.USER_NAME,"")+"!");
        }else{
            Intent intent = new Intent(MenuActivity.this,MainActivity.class);
            startActivity(intent);
        }
        return response;
    }

}
