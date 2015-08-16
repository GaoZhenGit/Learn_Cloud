package com.ibm.gz.learn_cloud.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.Constant;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.Utils.DensityUtil;
import com.ibm.gz.learn_cloud.Utils.LogUtil;
import com.ibm.gz.learn_cloud.entire.Course;
import com.ibm.gz.learn_cloud.myview.FullScreenVideoView;

public class VideoActivity extends BasePageActivity implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private AQuery aq;
    private FullScreenVideoView videoView;
    private MediaController mediaController;

    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {

        if (mBundle != null && mBundle.getSerializable(Constant.DataKey.COURSE) != null) {
            course = (Course) mBundle.getSerializable(Constant.DataKey.COURSE);
        }
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_video);
        aq = new AQuery(this);

        videoView = (FullScreenVideoView) findViewById(R.id.video_view);
        mediaController = new MediaController(this);

        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        mediaController.setAnchorView(videoView);

//        Uri uri = Uri.parse("http://www.ydtsystem.com/CardImage/21/video/20140305/20140305124807_37734.mp4");
        Uri uri =Uri.parse("http://192.168.1.107/zl.mp4");
        videoView.setVideoURI(uri);
        videoView.setOnErrorListener(this);
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_mid_text).text(course.getCourse_name());
        aq.id(R.id.title_left_img).visible();
        aq.id(R.id.title_left_tv).visible().text("退出");
//        setPortraitView();
    }

    @Override
    protected void setListener() {
        aq.id(R.id.btn_start).clicked(this, "aq_start_video");
        aq.id(R.id.btn_stop).clicked(this, "aq_stop_video");
        aq.id(R.id.btn_pause).clicked(this,"aq_pause_video");
        aq.id(R.id.btn_landscape).clicked(this, "aq_landscape");
        aq.id(R.id.title_left_btn).clicked(this, "finish");
    }

    public void aq_start_video() {
        videoView.start();
        mediaController.show();
    }
    public void aq_pause_video(){
        videoView.pause();
    }

    public void aq_stop_video() {
        videoView.invalidate();
    }

    public void aq_landscape() {
        switch (getRequestedOrientation()) {
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                setPortraitView();
                break;
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                setLandscapeView();
                break;
        }
    }


    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        ShowToast(what + "");
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LogUtil.i("现在是横屏");
            setLandscapeView();
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            LogUtil.i("现在是竖屏");
            setPortraitView();
        }
    }

    private void setPortraitView(){//竖屏界面转换
        aq.id(R.id.btn_start).visible();
        aq.id(R.id.btn_stop).visible();
        aq.id(R.id.btn_landscape).visible();
        aq.id(R.id.title_bar).visible();
        RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)findViewById(R.id.relativeLayout).getLayoutParams();
        layoutParams.addRule(RelativeLayout.BELOW, R.id.title_bar);
        layoutParams.width=DensityUtil.getWindowWidth(this);
        layoutParams.height=DensityUtil.dip2px(this, 200);
        DensityUtil.showTitle(this);
        videoView.setVideoHeight(DensityUtil.dip2px(this, 200));
        videoView.setVideoWidth(DensityUtil.getWindowWidth(this));
    }

    private void setLandscapeView(){//横屏界面设置
        aq.id(R.id.btn_start).gone();
        aq.id(R.id.btn_stop).gone();
        aq.id(R.id.btn_landscape).gone();
        aq.id(R.id.title_bar).gone();
        RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)findViewById(R.id.relativeLayout).getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutParams.height=RelativeLayout.LayoutParams.MATCH_PARENT;
        layoutParams.width=RelativeLayout.LayoutParams.MATCH_PARENT;
        DensityUtil.hideTitle(this);
        videoView.setVideoWidth(DensityUtil.getWindowWidth(this));
        videoView.setVideoHeight(DensityUtil.getWindowHeight(this));
    }

    @Override
    public void onBackPressed(){
        switch (getRequestedOrientation()) {
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                setPortraitView();
                break;
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                finish();
                break;
        }
    }
}
