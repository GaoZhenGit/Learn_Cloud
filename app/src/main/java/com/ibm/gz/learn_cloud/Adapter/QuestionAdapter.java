package com.ibm.gz.learn_cloud.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ibm.gz.learn_cloud.R;

/**
 * adapter for question dialog
 * Created by host on 2015/11/15.
 */
public class QuestionAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            if(position==0) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_question_right, parent, false);
            }else {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_question_left, parent, false);
            }
        }
        return convertView;
    }
}
