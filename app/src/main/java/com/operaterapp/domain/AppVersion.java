package com.operaterapp.domain;

/**
 * Created by Miley_Ren on 2017/6/3.
 */

public class AppVersion {
    private String applicationId;
    private Integer versionCode;
    private String versionName;
    public AppVersion(){
        super();
    }
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
