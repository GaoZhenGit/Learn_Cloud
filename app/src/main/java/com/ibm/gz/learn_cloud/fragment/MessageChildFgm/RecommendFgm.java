package com.ibm.gz.learn_cloud.fragment.MessageChildFgm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ibm.gz.learn_cloud.Adapter.CourseAdapter;
import com.ibm.gz.learn_cloud.Constant;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.Utils.VolleyUtils;
import com.ibm.gz.learn_cloud.entire.Course;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by host on 2015/10/2.
 */
public class RecommendFgm extends Fragment {
    private View contextView;
    private CourseAdapter courseAdapter;
    private PullToRefreshListView listView;
    private List<Course> courseList;
    private Gson gson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson=new GsonBuilder().disableHtmlEscaping().create();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contextView =inflater.inflate(R.layout.fragment_recommend,container,false);
        initListView();
        return contextView;
    }

    private void initListView() {
        listView=(PullToRefreshListView)contextView.findViewById(R.id.listview);
        requestNet();
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                requestNet();
            }
        });
        // 设置PullRefreshListView下拉加载时的加载提示
        listView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新...");
    }

    //请求内部刷新
    private void requestRefresh(List<Course> courseList){
        if(this.courseList==null){
            this.courseList=new ArrayList<>();
        }
        this.courseList.clear();
        this.courseList.addAll(courseList);
        if(courseAdapter==null){//
            courseAdapter=new CourseAdapter(getActivity(),this.courseList);
            listView.setAdapter(courseAdapter);
        }else {
            courseAdapter.notifyDataSetChanged();
        }
        listView.onRefreshComplete();
    }
    //请求网络数据（刷新）
    private void requestNet(){
        Map<String, String> param = new HashMap<>();
        param.put("type", "firstpagecourse");
        VolleyUtils.post(Constant.URL.RequestCourse, param, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<Course> courses = gson.fromJson(jsonArray.toString(), new TypeToken<List<Course>>() {
                    }.getType());
                    requestRefresh(courses);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }
}
