package com.ibm.gz.learn_cloud.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.ibm.gz.learn_cloud.Adapter.CourseAdapter;
import com.ibm.gz.learn_cloud.Constant;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.Utils.DensityUtil;
import com.ibm.gz.learn_cloud.Utils.LogUtil;
import com.ibm.gz.learn_cloud.Utils.VolleyUtils;
import com.ibm.gz.learn_cloud.activity.CourseActivity;
import com.ibm.gz.learn_cloud.entire.Course;
import com.ibm.gz.learn_cloud.listener.LeftHideShow;
import com.ibm.gz.learn_cloud.myview.CircleIndicator;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by host on 2015/8/14.
 */
public class FirstPageFragment extends ListFragment implements LeftHideShow {
    private AQuery aq;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private View contextView;
    private PullToRefreshScrollView scrollView;

    private List<Course> courses;//首页推荐课程
    private List<Course> lineCourses;
    private Timer timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq=new AQuery(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contextView =inflater.inflate(R.layout.fragment_firstpage,container,false);
        initImageView();
        initListView();
        leftOn();
        scrollView=(PullToRefreshScrollView)contextView.findViewById(R.id.pull_scroll);
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                LogUtil.i("reflesh", "pull to reflesh");
                requestFirstPageCourse();
            }
        });
        return contextView;
    }

    private void initListView() {
        courses=new ArrayList<Course>();
        requestFirstPageCourse();
    }
    private void refleshData(List<Course> list){
        if(getListAdapter()==null){
            courses.addAll(list);
            setListAdapter(new CourseAdapter(getActivity(), courses));
            DensityUtil.setListViewHeightBasedOnChildren(getListView());
        }else {
            courses.clear();
            courses.addAll(list);
            ((BaseAdapter)getListAdapter()).notifyDataSetChanged();
            DensityUtil.setListViewHeightBasedOnChildren((ListView) contextView.findViewById(android.R.id.list));
        }
        scrollView.onRefreshComplete();
    }

    //初始化横栏图片视频
    private void initImageView() {
//        images=new ArrayList<>();
//        images.add("http://img.mukewang.com/55cabf1100013e0806000338-240-135.jpg");
//        images.add("http://img.mukewang.com/55c33e400001a88f06000338-240-135.jpg");
//        images.add("http://img.mukewang.com/55a5f5f8000161a806000338-240-135.jpg");
//        images.add("http://img.mukewang.com/55badcc300017b7006000338-240-135.jpg");
//        images.add("http://img.mukewang.com/55c17abe0001ffd506000338-240-135.jpg");
//        images.add("http://img.mukewang.com/55c16f5a000159d406000338-240-135.jpg");
        viewPager=(ViewPager)contextView.findViewById(R.id.viewPager);
        circleIndicator=(CircleIndicator)contextView.findViewById(R.id.indicator);

        Map<String,String> param=new HashMap<>();
        param.put("type", "firstpagecourse");
        VolleyUtils.post("http://1.marketonhand.sinaapp.com/requestTest.php", param, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    String[] checks = response.split("\\]");
                    JSONArray jsonArray = new JSONArray(checks[0] + "]");
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    List<Course> courses = gson.fromJson(jsonArray.toString(), new TypeToken<List<Course>>() {
                    }.getType());
                    lineCourses = courses;
                    viewPager.setAdapter(new FirstViewPagerAdapter(lineCourses));
                    circleIndicator.setViewPager(viewPager);

                    //定时翻页
                    TimerTask timerTask=new TimerTask() {
                        @Override
                        public void run() {
                            if(getActivity()==null){
                                return;
                            }
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

    //请求网络访问获得首页视频数据
    private void requestFirstPageCourse(){
        LogUtil.i("first page","request");
        Map<String,String> param=new HashMap<>();
        param.put("type", "firstpagecourse");
        VolleyUtils.post("http://1.marketonhand.sinaapp.com/requestTest.php", param, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    String[] checks = response.split("\\]");
                    JSONArray jsonArray = new JSONArray(checks[0] + "]");
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    List<Course> courses = gson.fromJson(jsonArray.toString(), new TypeToken<List<Course>>() {
                    }.getType());
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

    //这个是视频点击的事件
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constant.DataKey.COURSE, courses.get(position));
        Intent intent=new Intent(getActivity(), CourseActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    @Override
    public void onDestroy(){
        LogUtil.i("---------------", "first page destroy");
        super.onDestroy();
    }

    @Override
    public void leftOff(){
        LogUtil.i("left", "first page off");
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
    public void leftOn(){
        LogUtil.i("left", "first page on");
        aq.id(R.id.btn_firstpage).background(R.color.light_grey);
        aq.id(R.id.img_firstpage).image(R.drawable.lesson_red);
        aq.id(R.id.text_firstpage).getTextView().setTextColor(getResources().getColor(R.color.text_red));
        aq.id(R.id.title_mid_text).text("首页");
    }

    /*作为viewpager的adapter
     *
     *
     */
    class FirstViewPagerAdapter extends PagerAdapter{
        private List<Course> lineCourse;
        private AQuery aq;

        public FirstViewPagerAdapter(List<Course> lineCourse){
            this.lineCourse=lineCourse;
        }

        @Override
        public int getCount() {
            return lineCourse.size();
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
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(Constant.DataKey.COURSE, courses.get(position));
                    Intent intent=new Intent(getActivity(), CourseActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            new AQuery(imageView).image(lineCourse.get(position).getCourse_img());

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
