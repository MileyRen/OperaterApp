package com.operaterapp;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.operaterapp.DataHelper.DataHelper;
import com.operaterapp.util.BaseConst;

public class VersionDbService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
         super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flag,int startId){
        //将当前版本信息插入数据库
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
        Toast.makeText(this,"Service已启动", Toast.LENGTH_SHORT).show();
        Log.d("Version","Service已启动");
        return super.onStartCommand(intent,flag,startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
