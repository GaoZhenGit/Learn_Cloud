package com.ibm.gz.learn_cloud.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.R;

/**
 * Created by host on 2015/8/9.
 */
public class HistoryFragment extends Fragment {
    private AQuery aq;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq=new AQuery(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contextView =inflater.inflate(R.layout.fragment_history,container,false);
        return contextView;
    }
    @Override
    public void onPause(){
        super.onPause();
        aq.id(R.id.btn_history).background(R.color.white);//背景色
        aq.id(R.id.img_history).image(R.drawable.lesson_gray);//图标
        aq.id(R.id.text_history).getTextView().setTextColor(getResources().getColor(R.color.grey));
    }
    @Override
    public void onResume(){
        super.onResume();
        aq.id(R.id.btn_history).background(R.color.light_grey);
        aq.id(R.id.img_history).image(R.drawable.lesson_red);
//        aq.id(R.id.text_history).textColor(R.color.red);
        aq.id(R.id.text_history).getTextView().setTextColor(getResources().getColor(R.color.red));
        aq.id(R.id.title_mid_text).text("历史课程");
    }
}
