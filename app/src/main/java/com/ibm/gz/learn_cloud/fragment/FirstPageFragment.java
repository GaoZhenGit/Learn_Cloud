package com.ibm.gz.learn_cloud.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ibm.gz.learn_cloud.Adapter.CourseAdapter;
import com.ibm.gz.learn_cloud.Constant;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.Utils.LogUtil;
import com.ibm.gz.learn_cloud.Utils.VolleyUtils;
import com.ibm.gz.learn_cloud.entire.Course;
import com.ibm.gz.learn_cloud.myview.CircleIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by host on 2015/8/14.
 */
public class FirstPageFragment extends ListFragment {
    private AQuery aq;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;


    private List<String> images;//上方的滑动图片资源
    private List<Course> courses;//首页推荐课程
    private Timer timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq=new AQuery(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contextView =inflater.inflate(R.layout.fragment_firstpage,container,false);
        initImageView(contextView);
        initListView(contextView);

        return contextView;
    }

    private void initListView(View contextView) {
        Course course=new Course();
        course.setCourse_name("HTML");
        course.setCourse_img("http://img.mukewang.com/55add9c50001040d06000338-280-160.jpg");
        course.setDetail("html+css+JavaScript");

        Course course2=new Course();
        course2.setCourse_name("与MySQL的零距离接触");
        course2.setCourse_img("http://img.mukewang.com/53b3d133000158e206000338-280-160.jpg");
        course2.setDetail("不花钱的关系数据库，你懂的");

        Course course3=new Course();
        course3.setCourse_name("css扁平化风格博客");
        course3.setCourse_img("http://img.mukewang.com/559b904a0001a9ed06000338-280-160.jpg");
        course3.setDetail("使用css3和html搭建超酷扁平化风格博客");

        Course course4=new Course();
        course4.setCourse_name("Sass入门篇");
        course4.setCourse_img("http://img.mukewang.com/55cc0ac30001a73a06000338-280-160.jpg");
        course4.setDetail("Sass让你摆脱重复编写css代码的工作");
        courses=new ArrayList<Course>();
        courses.add(course);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        refleshData(courses);
    }
    private void refleshData(List<Course> list){
        if(getListAdapter()==null){
            setListAdapter(new CourseAdapter(getActivity(), courses));
        }else {
            courses.clear();
            courses.addAll(list);
            getListAdapter().notify();
        }
    }

    private void initImageView(View contextView) {
        images=new ArrayList<>();
        images.add("http://img.mukewang.com/55cabf1100013e0806000338-240-135.jpg");
        images.add("http://img.mukewang.com/55c33e400001a88f06000338-240-135.jpg");
        images.add("http://img.mukewang.com/55a5f5f8000161a806000338-240-135.jpg");
        images.add("http://img.mukewang.com/55badcc300017b7006000338-240-135.jpg");
        images.add("http://img.mukewang.com/55c17abe0001ffd506000338-240-135.jpg");
        images.add("http://img.mukewang.com/55c16f5a000159d406000338-240-135.jpg");

        viewPager=(ViewPager)contextView.findViewById(R.id.viewPager);
        circleIndicator=(CircleIndicator)contextView.findViewById(R.id.indicator);

        viewPager.setAdapter(new FirstViewPagerAdapter(images));
        circleIndicator.setViewPager(viewPager);

        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int possition = (viewPager.getCurrentItem()+1) % viewPager.getAdapter().getCount();
                        viewPager.setCurrentItem(possition);
                        LogUtil.i("page", possition + "");
                    }
                });

            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 1000, 10000);
    }





    @Override
    public void onPause(){
        super.onPause();
        aq.id(R.id.btn_firstpage).background(R.color.white);//背景色
        aq.id(R.id.img_firstpage).image(R.drawable.lesson_gray);//图标
        aq.id(R.id.text_firstpage).getTextView().setTextColor(getResources().getColor(R.color.grey));

        if(timer!=null) {
            timer.cancel();
            timer = null;
            System.gc();
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        aq.id(R.id.btn_firstpage).background(R.color.light_grey);
        aq.id(R.id.img_firstpage).image(R.drawable.lesson_red);
//        aq.id(R.id.text_history).textColor(R.color.red);
        aq.id(R.id.text_firstpage).getTextView().setTextColor(getResources().getColor(R.color.text_red));
        aq.id(R.id.title_mid_text).text("首页");
    }

    private void requestFirstPageCourse(){
        Map<String,String> param=new HashMap<>();
        param.put("type","firstpagecourse");
        VolleyUtils.post("http://1.marketonhand.sinaapp.com/requestTest.php", param, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    String[] checks=response.split("\\]");
                    JSONObject jsonObject=new JSONObject(checks[0]+"]");
                    Gson gson= new GsonBuilder().disableHtmlEscaping().create();
                    List<Course> courses=gson.fromJson(jsonObject.toString(), new TypeToken<List<Course>>() {}.getType());
                    refleshData(courses);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }


    /*作为viewpager的adapter
     *
     *
     */
    class FirstViewPagerAdapter extends PagerAdapter{
        private List<String> images;
        private AQuery aq;

        public FirstViewPagerAdapter(List<String> images){
            this.images=images;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            ImageView imageView = new ImageView(container.getContext());

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//设置图片点击事件
                    LogUtil.i(position+"");
                }
            });

            new AQuery(imageView).image(images.get(position));

            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
