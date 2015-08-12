package com.ibm.gz.learn_cloud.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.R;

public class CollectFragment extends Fragment {
    private AQuery aq;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq=new AQuery(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contextView =inflater.inflate(R.layout.fragment_collect,container,false);
        return contextView;
    }





    @Override
    public void onPause(){
        super.onPause();
        aq.id(R.id.btn_collect_course).background(R.color.white);//背景色
        aq.id(R.id.img_collect).image(R.drawable.collect_gray);//图标
        aq.id(R.id.text_collect).getTextView().setTextColor(getResources().getColor(R.color.grey));
    }
    @Override
    public void onResume() {
        super.onResume();
        aq.id(R.id.btn_collect_course).background(R.color.light_grey);
        aq.id(R.id.img_collect).image(R.drawable.collect_red);
        aq.id(R.id.text_collect).getTextView().setTextColor(getResources().getColor(R.color.red));
    }
}