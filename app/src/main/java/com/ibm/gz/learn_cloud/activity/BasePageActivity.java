package com.ibm.gz.learn_cloud.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.ibm.gz.learn_cloud.R;

/**
 * Created by host on 2015/8/5.
 */
public abstract class BasePageActivity extends FragmentActivity {
    Context mContext;
    Intent mIntent;
    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initLayoutView();
        initView();
        setListener();
        mContext=this;
        mIntent=getIntent();
        mBundle=mIntent.getExtras();
    }

    protected abstract void initData();

    protected abstract void initLayoutView();
    protected abstract void initView();
    protected abstract void setListener();
    public void ShowToast(String string){
        Toast.makeText(this, string,Toast.LENGTH_SHORT).show();
    }

}
