package com.ibm.gz.learn_cloud.activity;


import android.content.Intent;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.R;

/**
 * Created by host on 2015/8/15.
 */
public class LoginActivity extends BasePageActivity {
    private AQuery aq;
    @Override
    protected void initData() {
        aq=new AQuery(this);
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_login);
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
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        ShowToast("登录成功");
        finish();
    }
}
