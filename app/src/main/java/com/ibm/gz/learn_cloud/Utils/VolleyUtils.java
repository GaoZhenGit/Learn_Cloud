package com.ibm.gz.learn_cloud.Utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by host on 2015/8/14.
 */
public class VolleyUtils {
    private static RequestQueue mQueue;
    private static boolean isInit=false;
    public static RequestQueue getmQueue() throws Exception {
        if(isInit) {
            return mQueue;
        }else{
            throw new Exception("volley has not init");
        }
    }
    public static void init(Context context){
        mQueue=Volley.newRequestQueue(context);
        isInit=true;
    }
}
