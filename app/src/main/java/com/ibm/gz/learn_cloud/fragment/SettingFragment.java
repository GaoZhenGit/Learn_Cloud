package com.ibm.gz.learn_cloud.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.gz.learn_cloud.Constant;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.Utils.ActivityManagerUtils;
import com.ibm.gz.learn_cloud.Utils.LogUtil;
import com.ibm.gz.learn_cloud.Utils.SpUtils;
import com.ibm.gz.learn_cloud.activity.LoginActivity;
import com.ibm.gz.learn_cloud.entire.User;
import com.ibm.gz.learn_cloud.listener.LeftHideShow;

/**
 * Created by host on 2015/9/4.
 */
public class SettingFragment extends Fragment implements LeftHideShow{
    View convertView;
    User user;
    AQuery aq;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        convertView=inflater.inflate(R.layout.setting_fragment,container,false);
        aq=new AQuery(getActivity());
        fetchUser();
        initView();
        setListener();
        leftOn();
        return convertView;
    }

    private void fetchUser() {
        SpUtils sp=new SpUtils(getActivity());
        String userJson=sp.getValue(Constant.DataKey.USER,"{}");
        Gson gson=new GsonBuilder().disableHtmlEscaping().create();
        user=gson.fromJson(userJson,User.class);
    }

    private void initView() {
        AQuery inaq=new AQuery(convertView);
        inaq.id(R.id.setting_name).text(user.getUsername());
        inaq.id(R.id.setting_detail).text(user.getDetail());
        if(user.getPhone()!=null){
            inaq.id(R.id.setting_phone).text(user.getPhone());
        }
        if(user.getMail()!=null){
            inaq.id(R.id.setting_mail).text(user.getMail());
        }
    }

    private void setListener() {
        AQuery inaq=new AQuery(convertView);
        inaq.id(R.id.btn_setting_logoff).clicked(this, "aq_logoff");
    }

    public void aq_logoff(){
        SpUtils sp=new SpUtils(getActivity());
        sp.setValue(Constant.DataKey.FIRSTSTART,true);
        sp.setValue(Constant.DataKey.USER,null);
        ActivityManagerUtils.getInstance().removeAllActivity();
        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public void leftOn() {
        LogUtil.i("left", "first page on");
        aq.id(R.id.btn_setting).background(R.color.light_grey);
        aq.id(R.id.img_setting).image(R.drawable.setting_red);
        aq.id(R.id.text_setting).getTextView().setTextColor(getResources().getColor(R.color.text_red));
        aq.id(R.id.title_mid_text).text("个人设置");
    }

    @Override
    public void leftOff() {
        aq.id(R.id.btn_setting).background(R.color.white);//背景色
        aq.id(R.id.img_setting).image(R.drawable.setting_gray);//图标
        aq.id(R.id.text_setting).getTextView().setTextColor(getResources().getColor(R.color.grey));
    }
}
