package com.ibm.gz.learn_cloud.Utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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
    public static void post(String httpurl,final Map<String,String> params,final NetworkListener networkListener){
        StringRequest stringRequest = new StringRequest8(Request.Method.POST,httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtil.i("volley", "response -> " + response);
                        networkListener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.i("volley error", error.getMessage());
                networkListener.onFail(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                return params;
            }
        };
        mQueue.add(stringRequest);
    }

    public interface NetworkListener{
        void onSuccess(String response);
        void onFail(String error);
    }
}
