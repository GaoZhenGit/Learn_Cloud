package com.ibm.gz.learn_cloud.fragment.FirstPageChildFgm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.entire.Course;

/**
 * Created by host on 2015/8/18.
 */
public class ChapterFgm extends Fragment {
    private Course course;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contextView =inflater.inflate(R.layout.fragment_charpter,container,false);
        initData();
        initView();
        return contextView;
    }

    private void initData() {

    }

    private void initView() {

    }
}
