package com.operaterapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.operaterapp.domain.MachineAdapter;
import com.operaterapp.util.BaseConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class MachineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine);
        String machines = getIntent().getStringExtra(BaseConst.ALL_MACHINES);

    }
    List<MachineAdapter> constructMachineAdapter(String params){
        try {
            params = URLDecoder.decode(params,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<MachineAdapter> machineAdapters = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(params);
            MachineAdapter ma = new MachineAdapter();
            for(int i=0; i<array.length();i++){
                JSONObject json = array.getJSONObject(i);
                ma.setmOperaterId(json.getInt("mOperaterId"));
                ma.setAddress(json.getString("machineAddress"));
                JSONObject machineInfo  =  json.getJSONObject("machineInfo");
                ma.setMachineId(machineInfo.getInt("machineId"));
                ma.setMachineName(machineInfo.getString("machineName"));
                ma.setMachinePannel(machineInfo.getString("machinePannel"));
                machineAdapters.add(ma);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  machineAdapters;
    }
}
