package com.ibm.gz.learn_cloud.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

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
import com.ibm.gz.learn_cloud.Utils.SpUtils;
import com.ibm.gz.learn_cloud.Utils.VolleyUtils;
import com.ibm.gz.learn_cloud.activity.CourseActivity;
import com.ibm.gz.learn_cloud.entire.Course;
import com.ibm.gz.learn_cloud.listener.LeftHideShow;
import com.ibm.gz.learn_cloud.myview.CircleIndicators;
import com.ibm.gz.learn_cloud.myview.ScrollListView;

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
public class FirstPageFragment extends Fragment implements LeftHideShow {
    private AQuery aq;
    private ViewPager viewPager;
    private CircleIndicators circleIndicator;
    private View contextView;
    private PullToRefreshScrollView scrollView;
    private CourseAdapter courseAdapter;
    private FirstViewPagerAdapter firstViewPagerAdapter;
    private ScrollListView listView;
    private SpUtils sp;
    private Gson gson;

    private List<Course> courses;//首页推荐课程
    private List<Course> lineCourses;//viewPager的课程
    private Timer timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq = new AQuery(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contextView = inflater.inflate(R.layout.fragment_firstpage, container, false);
        listView=(ScrollListView)contextView.findViewById(R.id.course_listview);
        sp=new SpUtils(getActivity());
        gson=new GsonBuilder().disableHtmlEscaping().create();


        leftOn();//在左侧菜单中字体图片颜色改为红
        initRefreshView();//初始化下拉上拉界面
        initListView();//初始化课程列表，包括获取本地缓存资源
        initImageView();//初始化横栏界面，包括获取网络缓存资源
        return contextView;
    }

    private void initRefreshView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //这个是视频点击的事件
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.DataKey.COURSE, courses.get(position));
                Intent intent = new Intent(getActivity(), CourseActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        scrollView = (PullToRefreshScrollView) contextView.findViewById(R.id.pull_scroll);
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                LogUtil.i("reflesh", "pull down reflesh");
                requestFirstPageCourse();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                LogUtil.i("reflesh", "pull up refresh");
                requestLoadMore();
            }

        });
        //设置PullRefreshListView上提加载时的加载提示
        scrollView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
        scrollView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        scrollView.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载更多...");
        // 设置PullRefreshListView下拉加载时的加载提示
        scrollView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        scrollView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        scrollView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新...");
    }

    private void initListView() {
        courses = new ArrayList<>();
//        requestFirstPageCourse();
        //从缓存中取出首页课程
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        SpUtils sp = new SpUtils(getActivity());
        String coursecache = sp.getValue(Constant.DataKey.COURSE_LIST_CACHE, "");
//        LogUtil.i("---------------course cache",coursecache);
        //从json解析出来
        List<Course> temp= gson.fromJson(coursecache, new TypeToken<List<Course>>() {
        }.getType());
        if(temp==null){//第一次进入应用，没有数据就请求网络
            requestFirstPageCourse();
        }else {//否则从缓存中获取
            refleshData(temp, true);
        }
    }

    private void refleshData(List<Course> list, boolean isReflesh) {
        if (courseAdapter == null) {
            courses.addAll(list);
            courseAdapter=new CourseAdapter(getActivity(), courses);
            listView.setAdapter(courseAdapter);
        } else {
            if (isReflesh)//刷新则清空
                courses.clear();
//            LogUtil.i("-----count-----",courses.size()+"");
            courses.addAll(list);
            courseAdapter.notifyDataSetChanged();
        }

        //刷新后，将记录缓存
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String refreshCache = gson.toJson(courses);
        SpUtils sp = new SpUtils(getActivity());
        sp.setValue(Constant.DataKey.COURSE_LIST_CACHE, refreshCache);
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
        viewPager = (ViewPager) contextView.findViewById(R.id.viewPager);
        circleIndicator = (CircleIndicators) contextView.findViewById(R.id.indicator);

        //先从缓存中获取横栏数据
        String lineCache=sp.getValue(Constant.DataKey.COURSE_LINE_CACHE,"");
        List<Course> temp=gson.fromJson(lineCache,new TypeToken<List<Course>>(){
        }.getType());

        //如果不是第一次进入应用，则缓存数据不为空，则可以直接初始化横栏界面（不然circleindicator会有错，内容不能为空）
        if(temp!=null){
            lineCourses=temp;
            firstViewPagerAdapter=new FirstViewPagerAdapter();
            viewPager.setAdapter(firstViewPagerAdapter);
            circleIndicator.setViewPager(viewPager);
        }

        //先释放listview焦点，再滑动到顶端，就这样做就好了，别问为什么，不然无效
        listView.setFocusable(false);
        scrollView.scrollTo(0,0);
        //请求网络更新横栏数据
        Map<String, String> param = new HashMap<>();
        param.put("type", "firstpagecourse");
        VolleyUtils.post("http://1.marketonhand.sinaapp.com/requestTest.php", param, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    String[] checks = response.split("\\]");
                    JSONArray jsonArray = new JSONArray(response);
                    lineCourses= gson.fromJson(jsonArray.toString(), new TypeToken<List<Course>>() {
                    }.getType());

                    if(firstViewPagerAdapter==null){//第一次进入应用在这里初始化横栏界面
                        firstViewPagerAdapter=new FirstViewPagerAdapter();
                        viewPager.setAdapter(firstViewPagerAdapter);
                        circleIndicator.setViewPager(viewPager);
                    }else {//否则为更新数据adapter
                        firstViewPagerAdapter.notifyDataSetChanged();
                    }

                    //存储缓存
                    sp.setValue(Constant.DataKey.COURSE_LINE_CACHE,jsonArray.toString());
                    //定时翻页
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            if (getActivity() == null) {
                                return;
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int possition = (viewPager.getCurrentItem() + 1) % viewPager.getAdapter().getCount();
                                    viewPager.setCurrentItem(possition);
                                    LogUtil.i("page", possition + "");
                                }
                            });

                        }
                    };
                    timer = new Timer();
                    timer.schedule(timerTask, 3000, 10000);
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
    private void requestFirstPageCourse() {
        LogUtil.i("first page", "request");
        Map<String, String> param = new HashMap<>();
        param.put("type", "firstpagecourse");
        VolleyUtils.post(Constant.URL.RequestCourse, param, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    String[] checks = response.split("\\]");
                    JSONArray jsonArray = new JSONArray(response);
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    List<Course> courses = gson.fromJson(jsonArray.toString(), new TypeToken<List<Course>>() {
                    }.getType());
                    refleshData(courses, true);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

    //加载更多请求
    public void requestLoadMore() {
        LogUtil.i("first page", "request more");
        Map<String, String> param = new HashMap<>();
        param.put("type", "firstpagecourse_more");
        VolleyUtils.post("http://1.marketonhand.sinaapp.com/requestTest.php", param, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    String[] checks = response.split("\\]");
                    JSONArray jsonArray = new JSONArray(response);
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    List<Course> courses = gson.fromJson(jsonArray.toString(), new TypeToken<List<Course>>() {
                    }.getType());
                    refleshData(courses, false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }




    @Override
    public void onDestroy() {
        LogUtil.i("---------------", "first page destroy");
        super.onDestroy();
    }

    @Override
    public void leftOff() {
        LogUtil.i("left", "first page off");
        aq.id(R.id.btn_firstpage).background(R.color.white);//背景色
        aq.id(R.id.img_firstpage).image(R.drawable.lesson_gray);//图标
        aq.id(R.id.text_firstpage).getTextView().setTextColor(getResources().getColor(R.color.grey));

        if (timer != null) {
            timer.cancel();
            timer = null;
            System.gc();
        }
    }

    @Override
    public void leftOn() {
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
    class FirstViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return lineCourses.size();
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
                    LogUtil.i(position + "");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.DataKey.COURSE, lineCourses.get(position));
                    Intent intent = new Intent(getActivity(), CourseActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            new AQuery(imageView).image(lineCourses.get(position).getCourse_img());

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
