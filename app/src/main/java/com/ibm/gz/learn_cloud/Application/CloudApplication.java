package com.ibm.gz.learn_cloud.Application;

import android.app.Application;

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
    }
}
