package com.ibm.gz.learn_cloud.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ibm.gz.learn_cloud.entire.CourseVideo;

import java.util.List;

/**
 * Created by host on 2015/8/9.
 */
public class videoAdapter extends BaseAdapter {
    private List<CourseVideo> videoList;
    public videoAdapter(List<CourseVideo> items){
        videoList=items;
    }

    @Override
    public int getCount() {
        return videoList.size();
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
        return null;
    }
}
