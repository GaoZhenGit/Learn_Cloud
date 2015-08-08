package com.ibm.gz.learn_cloud.activity;


import android.os.Bundle;
import android.widget.Gallery;

import com.ibm.gz.learn_cloud.Adapter.ImageGalleryAdapter;
import com.ibm.gz.learn_cloud.R;

public class MainActivity extends BasePageActivity {
    Gallery gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        gallery=(Gallery)findViewById(R.id.main_gallery);
        gallery.setAdapter(new ImageGalleryAdapter(this));

    }

    @Override
    protected void setListener() {

    }


}
