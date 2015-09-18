package com.ibm.gz.learn_cloud.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.ibm.gz.learn_cloud.entire.HistoryCourse;
import com.ibm.gz.learn_cloud.entire.User;
import com.ibm.gz.learn_cloud.entire.Video;
import com.ibm.gz.learn_cloud.listener.LeftHideShow;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by host on 2015/8/9.
 */
public class HistoryFragment extends Fragment implements LeftHideShow{
    private AQuery aq;
    private List<Course> courseList;
    private ListView listView;
    private CourseAdapter courseAdapter;

    private DbUtils db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq=new AQuery(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contextView =inflater.inflate(R.layout.fragment_history,container,false);
        listView=(ListView)contextView.findViewById(R.id.listview);
        initListView();
        setListener();
        leftOn();
        return contextView;
    }

    private void initListView() {
//        Course course=new Course();
//        course.setCourse_name("第一课");
//        course.setCourse_img("http://file.bmob.cn/M01/E8/75/oYYBAFXK7nyAdbG2AAB9dJS_7aY137.jpg");
//        Video video=new Video();
//        video.setName("hellokitty");
//        video.setUri("http://www.ydtsystem.com/CardImage/21/video/20140305/20140305124807_37734.mp4");
//        course.addVideo(video);
//        course.setDetail("第一次上课请多指教");
//
//        Course course2=new Course();
//        course2.setCourse_name("第二课");
//        course2.setCourse_img("http://pic3.bbzhi.com/youxibizhi/wushi5/jingxuan_yxjx_291691_15.jpg");
//        video=new Video();
//        video.setName("helloword");
//        video.setUri("http://192.168.1.107/zl.mp4");
//        course2.addVideo(video);
//        course2.setDetail("老牌讲师");
//
//        Course son=new Course();
//        son.setCourse_name("Gson的使用方法");
//        son.setCourse_img("http://img.mukewang.com/542376b20001374c06000338-280-160.jpg");
//        video=new Video();
//        video.setName("third");
//        video.setUri("http://www.ydtsystem.com/CardImage/21/video/20140305/20140305124807_37734.mp4");
//        son.addVideo(video);
//        son.setDetail("关于微客的基本知识和进阶");
//        User teacher =new User();
//        teacher.setId(1234578);
//        teacher.setMail("hotdog@gmail.com");
//        teacher.setUsername("热狗");
//        teacher.setDetail("最热门的老师，教你微商技术");
//        teacher.setAvater("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1506454338,1185755782&fm=116&gp=0.jpg");
//        son.setTeacher(teacher);
//
//        Gson gson=new GsonBuilder().disableHtmlEscaping().create();
//        String gCourse=gson.toJson(son);
//        List<Course> courses=new ArrayList<>();
//        courses.add(course);
//        courses.add(course2);
//        courses.add(son);
//        String gCs = gson.toJson(courses);
//        LogUtil.i("---------------json object",gCourse);
//        LogUtil.i("-------json ssss",gCs);
//        Course course3=gson.fromJson(gCourse,Course.class);
//
//        courseList=new ArrayList<Course>();
//        courseList.add(course);
//        courseList.add(course2);
//        courseList.add(course3);
//        listView.setAdapter(new CourseAdapter(getActivity(), courseList));
        findHistory();

    }
    private void setListener(){
        //点击事件和长按事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(null,null,position,id);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                String[] items = {"删除"};
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteHistory(courseList.get(position).getCourse_id());
                        findHistory();
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
//        Toast.makeText(getActivity(),""+position,Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(),courseList.get(position).getCourse_name(),Toast.LENGTH_SHORT).show();
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constant.DataKey.COURSE, courseList.get(position));
        Intent intent=new Intent(getActivity(), CourseActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void findHistory(){
        if(db==null){
            db=DbUtils.create(getActivity());
        }
        try {
            List<HistoryCourse> historyCourses=db.findAll(Selector.from(HistoryCourse.class));
            if (courseList==null){
                courseList=new ArrayList<>();
            }else {
                courseList.clear();
            }
            if(historyCourses!=null){
                for(HistoryCourse h:historyCourses){
                    courseList.add(h.getCourseEntire());
                }
                if(courseAdapter==null){
                    courseAdapter=new CourseAdapter(getActivity(),courseList);
                    listView.setAdapter(courseAdapter);
                }else {
                    courseAdapter.notifyDataSetChanged();
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void deleteHistory(int id){
        try {
            HistoryCourse del=db.findFirst(Selector.from(HistoryCourse.class).where("id","=",id));
            db.delete(del);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        findHistory();
    }










    @Override
    public void leftOff(){
        LogUtil.i("left", "histroy off");
        if(aq==null){
            aq=new AQuery(getActivity());
        }
        aq.id(R.id.btn_history).background(R.color.white);//背景色
        aq.id(R.id.img_history).image(R.drawable.lesson_gray);//图标
        aq.id(R.id.text_history).getTextView().setTextColor(getResources().getColor(R.color.grey));
    }
    @Override
    public void leftOn(){
        LogUtil.i("left", "histroy on");
        aq.id(R.id.btn_history).background(R.color.light_grey);
        aq.id(R.id.img_history).image(R.drawable.lesson_red);
        aq.id(R.id.text_history).getTextView().setTextColor(getResources().getColor(R.color.text_red));
        aq.id(R.id.title_mid_text).text("历史课程");
    }
}
