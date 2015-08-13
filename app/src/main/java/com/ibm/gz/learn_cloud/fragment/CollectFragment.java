package com.ibm.gz.learn_cloud.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.Adapter.CourseAdapter;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.entire.Course;

import java.util.ArrayList;
import java.util.List;

public class CollectFragment extends ListFragment {
    private AQuery aq;
    private List<Course> collectList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq=new AQuery(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contextView =inflater.inflate(R.layout.fragment_collect,container,false);
        initListView();
        return contextView;
    }


    private void initListView() {
        Course course=new Course();
        course.setCourse_name("HTML");
        course.setCourse_img("http://img.mukewang.com/55add9c50001040d06000338-280-160.jpg");
        course.setDetail("html+css+JavaScript");
        Course course2=new Course();
        course2.setCourse_name("与MySQL的零距离接触");
        course2.setCourse_img("http://img.mukewang.com/53b3d133000158e206000338-280-160.jpg");
        course2.setDetail("不花钱的关系数据库，你懂的");
        collectList=new ArrayList<Course>();
        collectList.add(course);
        collectList.add(course2);
        setListAdapter(new CourseAdapter(getActivity(), collectList));
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
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
        aq.id(R.id.text_collect).getTextView().setTextColor(getResources().getColor(R.color.text_red));
    }
}