package com.operaterapp.util;

/**
 * Created by Miley_Ren on 2017/5/16.
 */

public class BaseConst {
    public static final String IP = "172.29.187.30";
    public static final String PORT = "8080";

    public static final String BASE_URL = "http://"+BaseConst.IP+":"+BaseConst.PORT+"/vending";
    public static final String USER_MESSAGE = "USER";
    public static final String USER_ID = "USER_ID";
    public static final String USER_NO = "USER_NO";
    public static final String USER_NAME = "USER_NAME";
    public static final String ALL_MACHINES = "ALL_MACHINES";
    public static final String LOGIN_URL =BaseConst.BASE_URL + "/client/clientLogin";
    public static final String GET_MACHINES = BaseConst.BASE_URL + "/client/clientMachine?userId=:userId";

    public static final String SERVER_IP = "49.52.10.237";
    public static final String SERVER_BASE_URL="http://"+BaseConst.SERVER_IP+"/vending";
    public static final String GET_VERSION_SERVER_JSON = BaseConst.SERVER_BASE_URL+"/operater_version.json";
    public static final String NEW_CLIENT_APK_NAME = "OperaterApp.apk";
    public static final String DOWNLOAD_PATH = "download_cache";
    public static final String NEW_VERSION_APK_DOWNLOAD_PATH = BaseConst.SERVER_BASE_URL+ "/app-debug.apk";

    public static final int UPDATE_APP = 0;

    public static final String VERSION_DB_NAME = "version.db";
    public static final String VERSION_TABLE_NAME = "version";
    public static final String CREATE_VERSION_SQL= "create table " +VERSION_TABLE_NAME+
            "(" +
            "id integer primary key autoincrement,"+
            "applicationId text," +
            "versionCode integer," +
            "versionName text" +
            ")";
    public static final String DROP_TABLE_VERSION_SQL = "drop table if exist "+VERSION_TABLE_NAME;

}
