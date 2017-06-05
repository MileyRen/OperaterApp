package com.operaterapp.util;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class VersionActivity extends AppCompatActivity {

    public boolean CheckVersionTask() throws Exception {
        FutureTask<Boolean> task = new FutureTask<Boolean>(
                new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        String localVersion = getVersionName().trim();
                        String serverVersion = getVersion();
                        JSONObject serverVersionJson = new JSONObject(serverVersion);
                        serverVersion = serverVersionJson.getString("versionName").trim();
                        if(localVersion.equals(serverVersion)){
                            return  true;
                        }else{
                            return false;
                        }
                    }
                }
        );
        new Thread(task).start();
        return task.get();
    }
    /************************测试版本*********************************/
    /**获取本地版本*/
    public String getVersionName(){
        String versionName = "";
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
            versionName = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
    /**获取服务器上的apk版本**/
    public String getVersion(){
        String URL = BaseConst.GET_VERSION_SERVER_JSON;
        String response=null;
        try {
            response = HttpURLConnUtil.getByResponse(URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
       return  response;
    }
    /**获取服务器上的最新版本apk**/
    public File downloadNewVersion(){
        String URL = BaseConst.NEW_VERSION_APK_DOWNLOAD_PATH;
        File file = null;
        try {
            file = HttpURLConnUtil.downloadTask(URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    //安装
    public void installAPK(File file){
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //执行数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());//提示完成、打开
    }
    /*********************************************************/

}
