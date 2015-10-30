package com.ibm.gz.learn_cloud.Utils;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ibm.gz.learn_cloud.Constant;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gz on 15/9/1.
 */
public class StringRequest8 extends StringRequest {
    private Context context;

    public StringRequest8(int method, Context context, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.context = context;
    }

    public StringRequest8(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String str = null;
        try {
            str = new String(response.data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> header = new HashMap<>();
        String cookieFromResponse;
        SpUtils spUtils = new SpUtils(context);
        cookieFromResponse = spUtils.getValue(Constant.DataKey.SESS, null);
        try {
            if (cookieFromResponse != null && cookieFromResponse.length() != 0) {
                cookieFromResponse.replace("\u003d", "=");
                LogUtil.i("replace string", cookieFromResponse);
                header.put("Cookie", cookieFromResponse);
                LogUtil.i("send header", header.toString());
            }
            return header;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
