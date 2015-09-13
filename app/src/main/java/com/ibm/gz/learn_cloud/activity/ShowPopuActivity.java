package com.ibm.gz.learn_cloud.activity;



import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.Constant;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.entire.PopularizationCourse;
import com.ibm.gz.learn_cloud.myview.CircleIndicators;

public class ShowPopuActivity extends BasePageActivity {
    private AQuery aq;
    private PopularizationCourse course;
    private ViewPager viewPager;
    private CircleIndicators circleIndicators;
    @Override
    protected void initData() {
        if(mBundle!=null&& mBundle.getSerializable(Constant.DataKey.POPULARIZATION)!=null){
            course=(PopularizationCourse)mBundle.getSerializable(Constant.DataKey.POPULARIZATION);
        }
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_show_popu);
        aq=new AQuery(this);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        circleIndicators=(CircleIndicators)findViewById(R.id.circle_indicators);
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_mid_text).text(course.getTitle());
        aq.id(R.id.title_left_img).visible();
        aq.id(R.id.title_left_tv).visible().text("退出");
        viewPager.setAdapter(new ShowPopuPagerAdapter());
        circleIndicators.setViewPager(viewPager);
    }

    @Override
    protected void setListener() {
        aq.id(R.id.title_left_btn).clicked(this,"finish");
    }




    /**作为viewpager的adapter
     *
     *
     *
     */
    class ShowPopuPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return course.getImages().size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            ImageView imageView = new ImageView(container.getContext());

            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT));

            new AQuery(imageView).image(course.getImages().get(position));
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
