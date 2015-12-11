package com.ibm.gz.learn_cloud.Application;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;

import com.ibm.gz.learn_cloud.Utils.ActivityManagerUtils;
import com.ibm.gz.learn_cloud.Utils.LogUtil;
import com.ibm.gz.learn_cloud.Utils.VolleyUtils;

/**
 * Created by host on 2015/8/14.
 */
public class CloudApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        VolleyUtils.init(this);
        LogUtil.i("init","application");
        startNotifiService();
    }

    public void exit(){
        ActivityManagerUtils.getInstance().removeAllActivity();
    }

    private void startNotifiService(){
        Intent intent=new Intent();
        intent.setAction("notifi");
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
        AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
        manager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 20000, pendingIntent);
    }
}
