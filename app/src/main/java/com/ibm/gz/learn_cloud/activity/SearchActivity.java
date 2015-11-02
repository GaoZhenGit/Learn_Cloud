package com.ibm.gz.learn_cloud.activity;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.R;

/**
 * the search course activity
 * Created by host on 2015/11/2.
 */
public class SearchActivity extends BasePageActivity {
    private AQuery aQuery;

    @Override
    protected void initData() {

    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_search);
        aQuery = new AQuery(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.btn_search_left).clicked(this,"onBackPressed");
    }
}
