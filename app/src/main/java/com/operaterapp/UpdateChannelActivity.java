package com.operaterapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.operaterapp.util.BaseConst;
import com.operaterapp.util.HttpURLConnUtil;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import static com.operaterapp.util.BaseConst.LOGIN_URL;

public class UpdateChannelActivity extends AppCompatActivity implements View.OnClickListener{

    EditText machine_Name,channelNo,stockNumAdd;
    Button addStock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_channel);
        machine_Name = (EditText) findViewById(R.id.machine_Name);
        channelNo = (EditText) findViewById(R.id.channelNo);
        stockNumAdd = (EditText) findViewById(R.id.stockNumAdd);
        addStock = (Button) findViewById(R.id.addStock);

        addStock.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId() ){
            case R.id.addStock:
                addStock();
                break;
            default:break;
        }
    }


    /***添加库存*/
    public void addStock(){
        String machine = machine_Name.getText().toString();
        String channel = channelNo.getText().toString();
        String stockNum = stockNumAdd.getText().toString();
        SharedPreferences msg = getSharedPreferences(BaseConst.USER_MESSAGE,MODE_PRIVATE);
        Integer userId = msg.getInt(BaseConst.USER_ID,0);
        Map<String,String> map = new HashMap<String,String>();
        map.put("machineName", machine);
        map.put("channelNo",channel);
        map.put("stockNum",stockNum);
        map.put("userId",String.valueOf(userId));
        String response = null;
        String url = BaseConst.ADD_STOCK_URL;
        try {
            response = HttpURLConnUtil.postByResponse(url, map,"utf-8");
            if(response.equals("success")){
                Toast.makeText(this,"更新货道成功",Toast.LENGTH_SHORT).show();
                machine_Name.setText("");
                channelNo.setText("");
                stockNumAdd.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

