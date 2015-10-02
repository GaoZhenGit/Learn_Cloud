package com.ibm.gz.learn_cloud.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.Adapter.TabPagerAdapter;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.Utils.LogUtil;
import com.ibm.gz.learn_cloud.fragment.MessageChildFgm.RecommendFgm;
import com.ibm.gz.learn_cloud.fragment.MessageChildFgm.SysMsgFgm;
import com.ibm.gz.learn_cloud.listener.LeftHideShow;
import com.ibm.gz.learn_cloud.myview.TwoPagerTabWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by host on 2015/10/2.
 */
public class MessageFragment extends Fragment implements LeftHideShow {
    private AQuery aq;
    private View contextView;
    private ViewPager mViewPager;
    private TwoPagerTabWidget mTabWidget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq=new AQuery(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contextView =inflater.inflate(R.layout.fragment_message,container,false);
        initListView();
        leftOn();
        return contextView;
    }

    private void initListView() {
        //初始化两个fragment和tab关系
        mViewPager=(ViewPager)contextView.findViewById(R.id.viewPager);
        mTabWidget=(TwoPagerTabWidget)contextView.findViewById(R.id.two_tab);
        mTabWidget.setText_first_tab("推荐课程");
        mTabWidget.setText_second_tab("系统消息");

        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new RecommendFgm());
        fragments.add(new SysMsgFgm());
        mViewPager.setAdapter(new TabPagerAdapter(getChildFragmentManager(), fragments));
        mTabWidget.setmViewPager(mViewPager);
    }
    @Override
    public void leftOn() {
        LogUtil.i("left", "message on");
        if(aq==null){
            aq=new AQuery(getActivity());
        }
        aq.id(R.id.btn_message).background(R.color.light_grey);
        aq.id(R.id.img_message).image(R.drawable.msg_red);
        aq.id(R.id.text_message).getTextView().setTextColor(getResources().getColor(R.color.text_red));
        aq.id(R.id.title_mid_text).text("我的消息");
    }

    @Override
    public void leftOff() {
        LogUtil.i("left", "message off");
        if(aq==null){
            aq=new AQuery(getActivity());
        }
        aq.id(R.id.btn_message).background(R.color.white);//背景色
        aq.id(R.id.img_message).image(R.drawable.msg_gray);//图标
        aq.id(R.id.text_message).getTextView().setTextColor(getResources().getColor(R.color.grey));
    }
}
