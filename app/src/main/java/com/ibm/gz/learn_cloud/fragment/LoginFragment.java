package com.ibm.gz.learn_cloud.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.Constant;
import com.ibm.gz.learn_cloud.R;

public class LoginFragment extends Fragment {

    private String LoginType;

    private AQuery aq;

    private OnFragmentInteractionListener mListener;
    /*
    *
    *
    *
     */
    public static LoginFragment newInstance(String loginType) {
        LoginFragment fragment = new LoginFragment();
        Bundle bundle=new Bundle();
        bundle.putString(Constant.CODE.KeyValue,loginType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public LoginFragment(){
        aq=new AQuery(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            LoginType=getArguments().getString(Constant.CODE.KeyValue);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contextView=null;
        switch (LoginType) {
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
                aq_regist();
            }
        });
    }


    //邮箱注册登录页面设置
    private void setEmailLoginView(View contextView){

    }

    public void aq_regist(){
        Log.i("--tag--","regist");
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
