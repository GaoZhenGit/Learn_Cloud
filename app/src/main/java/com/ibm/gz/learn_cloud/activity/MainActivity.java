package com.ibm.gz.learn_cloud.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.Constant;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.fragment.CollectFragment;
import com.ibm.gz.learn_cloud.fragment.HistoryFragment;
import com.ibm.gz.learn_cloud.fragment.LoginFragment;

public class MainActivity extends BasePageActivity {
    private DrawerLayout mDrawerLayout;
    private AQuery aq;

    private Fragment currentFragment;
    //左侧每个fragment的引用
    private HistoryFragment historyFragment;
    private CollectFragment collectFragment;



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
        aq.id(R.id.title_left_img).visible().image(R.drawable.menu);
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
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        //每个左侧按设置监听器
        aq.id(R.id.btn_history).clicked(this, "aq_history").click();
        aq.id(R.id.btn_collect_course).clicked(this, "aq_collect_course");
        aq.id(R.id.title_left_img).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
    }

    //用来取消所有左侧按钮被选中状态
//    public void recoverLeftButton(){
//        aq.id(R.id.btn_history).background(R.color.white);
//        aq.id(R.id.btn_collect_course).background(R.color.white);
//        aq.id(R.id.btn_popularization).background(R.color.white);
//        aq.id(R.id.btn_interest).background(R.color.white);
//        aq.id(R.id.btn_message).background(R.color.white);
//        aq.id(R.id.btn_my_note).background(R.color.white);
//        aq.id(R.id.btn_setting).background(R.color.white);
//    }

    //历史课程
    public void aq_history(){
        ShowToast("history");
        if(historyFragment==null){
            historyFragment=new HistoryFragment();
        }
        currentFragment=historyFragment;
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_framelayout, historyFragment).commit();
    }
    //收藏课程
    public void aq_collect_course(){
        aq.id(R.id.btn_collect_course).background(R.color.light_grey);
        ShowToast("collect");
        if(collectFragment==null){
            collectFragment=new CollectFragment();
        }
        currentFragment=collectFragment;
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_framelayout, collectFragment).commit();
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
