package com.ibm.gz.learn_cloud.activity;


import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.R;

public class MainActivity extends BasePageActivity {
    private DrawerLayout mDrawerLayout;
    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_main);
        aq=new AQuery(this);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_mid_text).text("");
        aq.id(R.id.title_right_img).visible().image(R.drawable.search);
        aq.id(R.id.title_right_text).gone();
    }

    @Override
    protected void setListener() {
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                aq.id(R.id.title_mid_text).text("个人中心");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                aq.id(R.id.title_mid_text).text("");
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int key, KeyEvent event){
        switch (key) {
            case KeyEvent.KEYCODE_MENU:
                break;
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;

            default:
                break;
        }
        return false;
    }


}
