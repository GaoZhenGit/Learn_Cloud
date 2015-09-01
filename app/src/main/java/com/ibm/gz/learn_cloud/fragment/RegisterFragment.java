package com.ibm.gz.learn_cloud.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.ibm.gz.learn_cloud.Constant;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.Utils.LogUtil;
import com.ibm.gz.learn_cloud.Utils.VolleyUtils;
import com.ibm.gz.learn_cloud.entire.Course;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    private String RegisterType;
    private EditText phone;
    private EditText pwd;

    private AQuery aq;

    private OnFragmentInteractionListener mListener;
    /*
    *
    *
    *
     */
    public static RegisterFragment newInstance(String loginType) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle bundle=new Bundle();
        bundle.putString(Constant.CODE.KeyValue,loginType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public RegisterFragment(){
        aq=new AQuery(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            RegisterType=getArguments().getString(Constant.CODE.KeyValue);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contextView=null;
        switch (RegisterType) {
            case Constant.FragmentType.PhoneLogin:
                contextView=inflater.inflate(R.layout.fragment_phone, container, false);
                setPhoneLoginView(contextView);
                break;
            case Constant.FragmentType.EmailLogin:
                contextView=inflater.inflate(R.layout.fragment_email, container, false);
                setEmailLoginView(contextView);
                break;
        }

        return contextView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //手机注册登陆界面设置
    private void setPhoneLoginView(View contextView){
        Button button=(Button)contextView.findViewById(R.id.regist_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aq_phone_regist();
            }
        });
        phone=new AQuery(contextView).id(R.id.login_phone_et).getEditText();
        pwd=new AQuery(contextView).id(R.id.login_phone_psw_et).getEditText();
    }


    //邮箱注册登录页面设置
    private void setEmailLoginView(View contextView){
        Button button=(Button)contextView.findViewById(R.id.regist_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aq_email_regist();
            }
        });
    }

    public void aq_phone_regist(){
        LogUtil.i("--tag--","regist");
        if (phone==null){
            LogUtil.i("--tag--","null");
            return;
        }
        LogUtil.i("phone", phone.getText().toString());
        LogUtil.i("psw", pwd.getText().toString());
        Map<String,String> param=new HashMap<String,String>();
        param.put("phone",phone.getText().toString());
        param.put("password",pwd.getText().toString());
        VolleyUtils.post("http://marketonhand.sinaapp.com/home_register.php", param, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

    public void aq_email_regist(){
        Gson gson=new Gson();
        Course course=new Course();
        course.setCourse_name("第一课");
        course.setCourse_id(2342);
        course.setDetail("这个是gson测试，哈哈哈");
        String son=gson.toJson(course);
        Toast.makeText(getActivity(),son,Toast.LENGTH_LONG).show();
        LogUtil.i("gson",son);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
