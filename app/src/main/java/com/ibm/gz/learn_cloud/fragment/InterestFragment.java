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
 * Created by gz on 15/9/30.
 */
public class InterestFragment extends Fragment implements LeftHideShow {
    private AQuery aq;
    private View contextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq=new AQuery(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contextView =inflater.inflate(R.layout.fragment_interest,container,false);
        initListView();
        leftOn();
        return contextView;
    }

    private void initListView() {

    }


    @Override
    public void leftOn() {
        LogUtil.i("left", "interest on");
        if(aq==null){
            aq=new AQuery(getActivity());
        }
        aq.id(R.id.btn_interest).background(R.color.light_grey);
        aq.id(R.id.img_interest).image(R.drawable.collect_red);
        aq.id(R.id.text_interest).getTextView().setTextColor(getResources().getColor(R.color.text_red));
        aq.id(R.id.title_mid_text).text("兴趣标签");
    }

    @Override
    public void leftOff() {
        LogUtil.i("left", "interest off");
        if(aq==null){
            aq=new AQuery(getActivity());
        }
        aq.id(R.id.btn_interest).background(R.color.white);//背景色
        aq.id(R.id.img_interest).image(R.drawable.collect_gray);//图标
        aq.id(R.id.text_interest).getTextView().setTextColor(getResources().getColor(R.color.grey));
    }
}
