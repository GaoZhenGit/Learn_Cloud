package com.ibm.gz.learn_cloud.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.Utils.LogUtil;
import com.ibm.gz.learn_cloud.myview.CircleIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by host on 2015/8/14.
 */
public class FirstPageFragment extends Fragment {
    private AQuery aq;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private List<String> images;
    private Timer timer;

    private int possition=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq=new AQuery(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contextView =inflater.inflate(R.layout.fragment_firstpage,container,false);
        initListView(contextView);

        return contextView;
    }

    private void initListView(View contextView) {
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
                        possition = (++possition) % viewPager.getAdapter().getCount();
                        viewPager.setCurrentItem(possition);
                        LogUtil.i("page", possition + "");
                    }
                });

            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 1000, 3000);
    }





    @Override
    public void onPause(){
        super.onPause();
        aq.id(R.id.btn_firstpage).background(R.color.white);//背景色
        aq.id(R.id.img_firstpage).image(R.drawable.lesson_gray);//图标
        aq.id(R.id.text_firstpage).getTextView().setTextColor(getResources().getColor(R.color.grey));

        timer.cancel();
        timer=null;
        System.gc();
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
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(container.getContext());

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));

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
