package com.ibm.gz.learn_cloud.fragment.FirstPageChildFgm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by host on 2015/8/18.
 */
public class ChapterFgm extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView contextView =new TextView(getActivity());
        contextView.setText("章节");
        return contextView;
    }
}