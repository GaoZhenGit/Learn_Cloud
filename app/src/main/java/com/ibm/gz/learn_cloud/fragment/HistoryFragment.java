package com.ibm.gz.learn_cloud.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
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

/**
 * Created by host on 2015/8/9.
 */
public class HistoryFragment extends ListFragment {
    private AQuery aq;
    private List<Course> courseList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq=new AQuery(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contextView =inflater.inflate(R.layout.fragment_history,container,false);
        initListView();

        return contextView;
    }

    private void initListView() {
        Course course=new Course();
        course.setCourse_name("第一课");
        course.setCourse_img("http://file.bmob.cn/M01/E8/75/oYYBAFXK7nyAdbG2AAB9dJS_7aY137.jpg");
        course.setDetail("第一次上课请多指教");
        Course course2=new Course();
        course2.setCourse_name("第二课");
        course2.setCourse_img("http://pic3.bbzhi.com/youxibizhi/wushi5/jingxuan_yxjx_291691_15.jpg");
        course2.setDetail("老牌讲师");
        courseList=new ArrayList<Course>();
        courseList.add(course);
        courseList.add(course2);
        setListAdapter(new CourseAdapter(getActivity(), courseList));
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(),""+position,Toast.LENGTH_SHORT).show();
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
        aq.id(R.id.text_history).getTextView().setTextColor(getResources().getColor(R.color.text_red));
        aq.id(R.id.title_mid_text).text("历史课程");
    }
}
