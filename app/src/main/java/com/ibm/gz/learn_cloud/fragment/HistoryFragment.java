package com.ibm.gz.learn_cloud.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibm.gz.learn_cloud.R;

/**
 * Created by host on 2015/8/9.
 */
public class HistoryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contextView = null;
        contextView=inflater.inflate(R.layout.fragment_history,container,false);
        return contextView;
    }
}
