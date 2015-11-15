package com.ibm.gz.learn_cloud.activity;

import android.widget.ListView;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.Adapter.QuestionAdapter;
import com.ibm.gz.learn_cloud.R;

/**
 * ask question to teacher
 * Created by host on 2015/11/14.
 */
public class QuestionActivity extends BasePageActivity {
    private AQuery aQuery;
    private boolean isTeacher = true;

    private ListView listView;
    private QuestionAdapter questionAdapter;


    @Override
    protected void initData() {

    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_question);
        aQuery = new AQuery(this);
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.listview);
        questionAdapter = new QuestionAdapter();
        listView.setAdapter(questionAdapter);
        listView.setDivider(null);
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.title_left_btn).clicked(this, "onBackPressed");
        aQuery.id(R.id.title_left).clicked(this, "changeToTeacher");
        aQuery.id(R.id.title_right).clicked(this, "changeToStudent");
    }

    public void changeToTeacher() {
        if (isTeacher) {
            return;
        }
        aQuery.id(R.id.title_left).backgroundColorId(R.color.white);
        aQuery.id(R.id.title_right).background(R.drawable.title_stock);
        isTeacher = true;
    }

    public void changeToStudent() {
        if (!isTeacher) {
            return;
        }
        aQuery.id(R.id.title_right).backgroundColorId(R.color.white);
        aQuery.id(R.id.title_left).background(R.drawable.title_stock);
        isTeacher = false;
    }
}
