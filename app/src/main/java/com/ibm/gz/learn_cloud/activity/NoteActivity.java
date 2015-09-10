package com.ibm.gz.learn_cloud.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.Constant;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.entire.Note;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NoteActivity extends BasePageActivity {
    private AQuery aq;
    private EditText editText;
    private Note note;
    private String courseName;
    private DbUtils db;
    boolean isNewNote=false;

    @Override
    protected void initData() {
        db=DbUtils.create(this);
        if(mBundle!=null&&mBundle.getSerializable(Constant.DataKey.NOTE)!=null){
            note=(Note)mBundle.getSerializable(Constant.DataKey.NOTE);
            isNewNote=false;
        }else {
            note=new Note();
            courseName=mBundle.getString(Constant.DataKey.COURSE);
            isNewNote=true;
        }
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_note);
        aq=new AQuery(this);
        editText=(EditText)findViewById(R.id.note_text);
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_mid_text).text("笔记");
        aq.id(R.id.title_left_img).visible();
        aq.id(R.id.title_left_tv).visible().text("退出");
        aq.id(R.id.title_right_text).visible().text("保存");

        //如果是已有的笔记，则显示出来
        if(!isNewNote){
            aq.id(R.id.note_date).text(note.getTime());
            aq.id(R.id.note_text).text(note.getText());
        }else {
            aq.id(R.id.note_date).gone();
        }
    }

    @Override
    protected void setListener() {
        aq.id(R.id.title_left_btn).clicked(this, "finish");
        aq.id(R.id.title_right_btn).clicked(this, "aq_save");
    }

    public void aq_save(){
        String text=editText.getText().toString();
        if(text==null||text.length()==0){
            ShowToast("请输入内容");
            return;
        }

        if(isNewNote){
            try {
                note.setText(text);
                note.setCourseName(courseName);
                SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                note.setTime(formats.format(Calendar.getInstance().getTime()));
                db.save(note);
                ShowToast("保存成功");
                finish();
            } catch (DbException e) {
                e.printStackTrace();
            }
        }else {
            try {
                note.setText(text);
                db.update(note,"text");
                ShowToast("更新成功");
                finish();
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }
}
