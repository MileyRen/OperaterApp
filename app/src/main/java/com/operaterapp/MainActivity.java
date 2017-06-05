package com.operaterapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.operaterapp.DataHelper.DataHelper;
import com.operaterapp.util.BaseConst;
import com.operaterapp.util.HttpURLConnUtil;
import com.operaterapp.util.ValidateUtils;
import com.operaterapp.util.VersionActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.operaterapp.util.BaseConst.LOGIN_URL;
import static com.operaterapp.util.BaseConst.USER_ID;
import static com.operaterapp.util.BaseConst.USER_MESSAGE;
import static com.operaterapp.util.BaseConst.USER_NAME;
import static com.operaterapp.util.BaseConst.USER_NO;

public class MainActivity extends VersionActivity implements View.OnClickListener {

    EditText etName,etPass;
    Button bnLogin,bnCancel;
    Button bnCheckVersion, createDB;

    // Handler内部类，它的引用在子线程中被使用，发送mesage，被handlerMesage方法接收
    public  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case BaseConst.UPDATE_APP:
                    File file =  downloadNewVersion();
                    installAPK(file);
                    Intent intent = new Intent("android.intent.action.MAIN");
                    intent.addCategory("android.intent.category.LOGIN");
                    startActivity(intent);
                    break;
                default:break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        etName = (EditText) findViewById(R.id.userEditText);
        etPass = (EditText) findViewById(R.id.passEditText);
        bnLogin = (Button) findViewById(R.id.bnLogin);
        bnCancel = (Button) findViewById(R.id.bnCancel);
        bnCheckVersion  = (Button) findViewById(R.id.bnCheckVersion);
        createDB = (Button)findViewById(R.id.createDB);

        bnLogin.setOnClickListener(this);
        bnCancel.setOnClickListener(this);
        bnCheckVersion.setOnClickListener(this);
        createDB.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId() ){
            case R.id.bnLogin:
                if(validate()) {
                    String  res = login();
                    if(!TextUtils.isEmpty(res)){
                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(this,"登陆失败，请检查用户名密码",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.bnCancel:
                cancelLogin();
                break;
            case R.id.bnCheckVersion:
                try {
                    boolean  newVersion = CheckVersionTask();
                    if(!newVersion){
                        Message msg = new Message();
                        msg.what= BaseConst.UPDATE_APP;
                        handler.sendMessage(msg);
                    }else{
                        Toast.makeText(MainActivity.this,"已经是最新版本",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.createDB:
                DataHelper dbHelper = new DataHelper(this, BaseConst.VERSION_DB_NAME ,null,1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues value = new ContentValues();
                PackageManager packageManager = getPackageManager();
                try {
                    PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
                    value.put("id", 1);
                    value.put("versionName",packInfo.versionName);
                    value.put("versionCode", packInfo.versionCode);
                    value.put("applicationId", packInfo.packageName);

                    db.delete(BaseConst.VERSION_TABLE_NAME,"id=?",new String[]{"1"});
                    db.insert(BaseConst.VERSION_TABLE_NAME,null,value);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    public void updateVersion(){
        try {
            boolean newVersion = CheckVersionTask();
            if(newVersion){
                Toast.makeText(this,"已是最新版本",Toast.LENGTH_SHORT).show();
            }else{
                File file =  downloadNewVersion();
                if(file.length()!=0){
                    installAPK(file);
                }
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LOGIN");
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"服务器异常",Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelLogin(){
        etName.setText("");
        etPass.setText("");
    }
    public String login() {
        String userName = etName.getText().toString();
        String password = etPass.getText().toString();
        Map<String,String> map  = new HashMap<>();
        map.put("userName",userName);
        map.put("password",password);
        String response = null;
        try {
            response = HttpURLConnUtil.postByResponse(LOGIN_URL, map,"utf-8");
            JSONObject jsonObject = new JSONObject(response);
            //将用户信息放在SharedPreferences中
            SharedPreferences.Editor editor = getSharedPreferences(USER_MESSAGE,MODE_PRIVATE).edit();
            editor.putInt(USER_ID,jsonObject.getInt("userId"));
            editor.putString(USER_NO,jsonObject.getString("userNo"));
            editor.putString(USER_NAME,userName);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  response;
    }

    /**验证用户名和密码**/
    public boolean validate(){
        String userName = etName.getText().toString().trim();
        String password = etPass.getText().toString().trim();
        if(!ValidateUtils.isNotEmpty(userName)){
            Toast.makeText(MainActivity.this,"必须输入用户名",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!ValidateUtils.isNotEmpty(password)){
            Toast.makeText(MainActivity.this, "必须输入用户密码",Toast.LENGTH_SHORT).show();
            return  false;
        }
        return  true;
    }

}

