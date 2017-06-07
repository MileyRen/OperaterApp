package com.operaterapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //开机启动软件和service
        Intent startIntent = new Intent(context, MainActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //开机启动Service
        Intent intentService = new Intent(context, VersionDbService.class);
        context.startService(intentService);

        context.startActivity(startIntent);
     }
}
