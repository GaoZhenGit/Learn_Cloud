package com.ibm.gz.learn_cloud.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.gz.learn_cloud.Adapter.CourseAdapter;
import com.ibm.gz.learn_cloud.Constant;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.Utils.LogUtil;
import com.ibm.gz.learn_cloud.activity.CourseActivity;
import com.ibm.gz.learn_cloud.entire.Course;
import com.ibm.gz.learn_cloud.entire.User;

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
        course.setCourse_video("http://www.ydtsystem.com/CardImage/21/video/20140305/20140305124807_37734.mp4");
        course.setDetail("第一次上课请多指教");

        Course course2=new Course();
        course2.setCourse_name("第二课");
        course2.setCourse_img("http://pic3.bbzhi.com/youxibizhi/wushi5/jingxuan_yxjx_291691_15.jpg");
        course2.setCourse_video("http://192.168.1.107/zl.mp4");
        course2.setDetail("老牌讲师");

        Course son=new Course();
        son.setCourse_name("Gson的使用方法");
        son.setCourse_img("http://img.mukewang.com/542376b20001374c06000338-280-160.jpg");
        son.setCourse_video("http://www.ydtsystem.com/CardImage/21/video/20140305/20140305124807_37734.mp4");
        son.setDetail("关于微客的基本知识和进阶");
        User teacher =new User();
        teacher.setU_id(1234578);
        teacher.setUser_mail("hotdog@gmail.com");
        teacher.setUsername("热狗");
        teacher.setDetail("最热门的老师，教你微商技术");
        teacher.setAvater("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1506454338,1185755782&fm=116&gp=0.jpg");
        son.setTeacher(teacher);

        Gson gson=new GsonBuilder().disableHtmlEscaping().create();
        String gCourse=gson.toJson(son);
        List<Course> courses=new ArrayList<>();
        courses.add(course);
        courses.add(course2);
        courses.add(son);
        String gCs=gson.toJson(courses);
        LogUtil.i("---------------json object",gCourse);
        LogUtil.i("-------json ssss",gCs);
        Course course3=gson.fromJson(gCourse,Course.class);

        courseList=new ArrayList<Course>();
        courseList.add(course);
        courseList.add(course2);
        courseList.add(course3);
        setListAdapter(new CourseAdapter(getActivity(), courseList));
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
//        Toast.makeText(getActivity(),""+position,Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(),courseList.get(position).getCourse_name(),Toast.LENGTH_SHORT).show();
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constant.DataKey.COURSE,courseList.get(position));
        Intent intent=new Intent(getActivity(), CourseActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
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
