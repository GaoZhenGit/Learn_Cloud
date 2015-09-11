package com.ibm.gz.learn_cloud.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.Utils.LogUtil;
import com.ibm.gz.learn_cloud.listener.LeftHideShow;

/**
 * Created by gz on 15/9/11.
 */
public class PopularizationFragment extends Fragment implements LeftHideShow{

    private View mView;
    private AQuery aq;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_popularization,container,false);
        initData();
        initView();
        setListener();
        leftOn();
        return mView;
    }

    private void initData() {

    }

    private void initView() {

    }

    private void setListener() {

    }


    @Override
    public void leftOff() {
        LogUtil.i("left", "popularization off");
        if(aq==null){
            aq=new AQuery(getActivity());
        }
        aq.id(R.id.btn_popularization).background(R.color.white);//背景色
        aq.id(R.id.img_popularization).image(R.drawable.popularization_gray);//图标
        aq.id(R.id.text_popularization).getTextView().setTextColor(getResources().getColor(R.color.grey));
    }

    @Override
    public void leftOn() {
        LogUtil.i("left", "popularization on");
        if(aq==null){
            aq=new AQuery(getActivity());
        }
        aq.id(R.id.btn_popularization).background(R.color.light_grey);
        aq.id(R.id.img_popularization).image(R.drawable.popularization_red);
        aq.id(R.id.text_popularization).getTextView().setTextColor(getResources().getColor(R.color.text_red));
        aq.id(R.id.title_mid_text).text("科普天地园");
    }
}
