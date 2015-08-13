package com.ibm.gz.learn_cloud.activity;


import android.os.Bundle;

import com.ibm.gz.learn_cloud.Constant;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.entire.Course;
import com.ibm.gz.learn_cloud.entire.User;

public class CourseActivity extends BasePageActivity {
    private Course course;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
    }

    @Override
    protected void initData() {
        course=(Course)mBundle.getSerializable(Constant.DataKey.COURSE);
        user=(User)mBundle.getSerializable(Constant.DataKey.USER);
    }

    @Override
    protected void initLayoutView() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }


}
