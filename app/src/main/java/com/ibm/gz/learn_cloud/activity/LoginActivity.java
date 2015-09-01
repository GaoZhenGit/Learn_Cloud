package com.ibm.gz.learn_cloud.activity;


import android.content.Intent;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.Constant;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.Utils.LogUtil;
import com.ibm.gz.learn_cloud.Utils.SpUtils;
import com.ibm.gz.learn_cloud.Utils.VolleyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by host on 2015/8/15.
 */
public class LoginActivity extends BasePageActivity {
    private AQuery aq;
    private EditText account;
    private EditText password;
    @Override
    protected void initData() {
        SpUtils sp=new SpUtils(this);
        boolean firstStart=sp.getValue(Constant.DataKey.FIRSTSTART,true);
        LogUtil.i("first start",firstStart+"");
        if(!firstStart){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_login);
        aq=new AQuery(this);
        account=(EditText)findViewById(R.id.login_phone_et);
        password=(EditText)findViewById(R.id.login_phone_psw_et);
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_mid_text).text("登录");
        aq.id(R.id.title_right_text).visible().text("注册");
        aq.id(R.id.title_left_tv).visible().text("退出");
        aq.id(R.id.title_left_img).visible();
    }

    @Override
    protected void setListener() {
        aq.id(R.id.login_btn).clicked(this,"aq_login");
        aq.id(R.id.title_right_btn).clicked(this, "aq_register");
        aq.id(R.id.title_left_btn).clicked(this,"finish");
    }

    public void aq_register(){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        finish();
    }

    public void aq_login(){
        String accountString=account.getText().toString();
        String passwordString=password.getText().toString();
        if(accountString==null||accountString.length()==0){
            ShowToast("请填写手机号或邮箱");
            return;
        }
        if(passwordString==null||passwordString.length()==0){
            ShowToast("请填写密码");
            return;
        }

        Map<String,String> param=new HashMap<>();
        param.put("type","login");
        if(accountString.matches("[0-9]+")) {
            //手机号登录
            param.put("phone", accountString);
            param.put("password", passwordString);
        }else {
            //邮箱登录
            param.put("email", accountString);
            param.put("password", accountString);
        }
        VolleyUtils.post(Constant.URL.Register, param, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                LogUtil.i("volley", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String state = jsonObject.optString("state");
                    if (state.equals("success")) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        ShowToast("登录成功");
                        SpUtils sp=new SpUtils(LoginActivity.this);
                        sp.setValue(Constant.DataKey.FIRSTSTART,false);
                        finish();
                    } else {
                        String reason = jsonObject.optString("reason");
                        ShowToast(reason);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }
}