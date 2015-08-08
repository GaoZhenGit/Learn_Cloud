package com.ibm.gz.learn_cloud.activity;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.Adapter.TabPagerAdapter;
import com.ibm.gz.learn_cloud.Constant;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.fragment.LoginFragment;
import com.ibm.gz.learn_cloud.listener.OnTabSelectedListener;
import com.ibm.gz.learn_cloud.myview.TwoPagerTabWidget;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BasePageActivity {
    private AQuery aq;
    private TwoPagerTabWidget mTabWidget;
    private ViewPager mViewPager;
    List<Fragment> fragments;


    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_main);
        fragments=new ArrayList<>();
        fragments.add(LoginFragment.newInstance(Constant.FragmentType.PhoneLogin));
        fragments.add(LoginFragment.newInstance(Constant.FragmentType.EmailLogin));
    }

    @Override
    protected void initView() {

        aq=new AQuery(this);
        mViewPager=(ViewPager)findViewById(R.id.viewPager);
        aq.id(R.id.title_mid_text).text("快速注册");
        aq.id(R.id.title_left_img).visible();
        aq.id(R.id.title_left_tv).visible().text("退出");
        aq.id(R.id.title_right_img).gone();
        aq.id(R.id.title_right_text).text("登录");
        mTabWidget=(TwoPagerTabWidget)findViewById(R.id.two_tab);
        mTabWidget.setText_first_tab("手机注册");
        mTabWidget.setText_second_tab("邮箱注册");

        mViewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), fragments));
        mTabWidget.setmViewPager(mViewPager);


        //ShowToast("hi");
    }

    @Override
    protected void setListener() {
        mTabWidget.setmOnTabSelectedListener(new OnTabSelectedListener() {
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
        aq.id(R.id.title_left_btn).clicked(this, "aq_back");
        aq.id(R.id.title_right_btn).clicked(this,"");
    }

    public void aq_back(){
        finish();
    }
}
