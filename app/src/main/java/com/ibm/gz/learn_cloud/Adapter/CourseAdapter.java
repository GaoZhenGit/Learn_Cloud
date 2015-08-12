package com.ibm.gz.learn_cloud.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.entire.Course;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by host on 2015/8/9.
 */
public class CourseAdapter extends BaseAdapter {

    private Context context;
    private List<Course> videoList;
    private LayoutInflater listContainer;
    private AQuery aq;

    public CourseAdapter(Context context,List<Course> items){
        videoList=items;
        this.context=context;
        listContainer=LayoutInflater.from(context);
        aq=new AQuery(context);
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
        View contextView=listContainer.inflate(R.layout.course_adapter,null);
        aq=new AQuery(contextView);
        Course course=videoList.get(position);
        aq.id(R.id.img_video).image(course.getCourse_img());
        aq.id(R.id.text_video).text(course.getCourse_name());
        return contextView;
    }

    public void refresh(){
        this.notifyDataSetChanged();
    }
}
