package com.ibm.gz.learn_cloud.activity;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.listener.OnTabSelectedListener;
import com.ibm.gz.learn_cloud.myview.TwoPagerTabWidget;

import java.util.List;


public class MainActivity extends BasePageActivity {
    private AQuery aq;
    private TwoPagerTabWidget tab;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        aq=new AQuery(this);
        aq.id(R.id.title_mid_text).text("快速注册");
        aq.id(R.id.title_left_img).visible();
        aq.id(R.id.title_left_tv).visible().text("退出");
        tab=(TwoPagerTabWidget)findViewById(R.id.two_tab);
        tab.setText_first_tab("邮箱注册");
        tab.setText_second_tab("手机注册");


        ShowToast("hi");
    }

    @Override
    protected void setListener() {
        tab.setmOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onSelected(List<View> tabViews, int position) {
                switch (position){
                    case 0:
                        ShowToast("0");
                        break;
                    case 1:
                        ShowToast("1");
                        break;
                }
            }
        });
    }

    public void aq_back(){
        finish();
    }
}
