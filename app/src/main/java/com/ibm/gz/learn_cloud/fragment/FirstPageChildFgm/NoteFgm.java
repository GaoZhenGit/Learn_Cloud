package com.ibm.gz.learn_cloud.fragment.FirstPageChildFgm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.Adapter.NoteAdapter;
import com.ibm.gz.learn_cloud.Constant;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.Utils.LogUtil;
import com.ibm.gz.learn_cloud.activity.CourseActivity;
import com.ibm.gz.learn_cloud.activity.NoteActivity;
import com.ibm.gz.learn_cloud.entire.Course;
import com.ibm.gz.learn_cloud.entire.Note;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by host on 2015/8/18.
 */
public class NoteFgm extends Fragment{
    private View contextView;
    private AQuery aq;
    private Course course;
    private ListView listView;
    private NoteAdapter noteAdapter;
    private List<Note> noteList;
    private DbUtils db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contextView =inflater.inflate(R.layout.fragment_note, container, false);
        initData();
        initView();
        setListener();
        return contextView;
    }

    private void initData() {
        course=((CourseActivity)getActivity()).getCourse();
        db=DbUtils.create(getActivity());
        listView=(ListView)contextView.findViewById(R.id.note_list);
    }

    private void initView() {
    }
    private void setListener(){
        //查看编辑笔记
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putSerializable(Constant.DataKey.NOTE,noteList.get(position));
                Intent intent=new Intent(getActivity(), NoteActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //删除笔记
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                String[] items={"删除"};
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            db.delete(noteList.get(position));
                            checkDBForNote();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }
    private void checkDBForNote(){
        try {
            if(noteList==null) {
                noteList= db.findAll(Selector.from(Note.class)
                        .where("courseName", "=", course.getCourse_name()).orderBy("id",true));
                if(noteList==null)//数据库查询为空时，list也为空，所以……
                    noteList=new ArrayList<>();
            }else {
                noteList.clear();
                List<Note> temp= db.findAll(Selector.from(Note.class)
                        .where("courseName", "=", course.getCourse_name()).orderBy("id",true));
                if(temp!=null)
                    noteList.addAll(temp);
            }
            if(noteAdapter==null) {//首次查询数据库
                noteAdapter = new NoteAdapter(getActivity(), noteList);
                listView.setAdapter(noteAdapter);
            }else {//查询数据库后更新
                noteAdapter.notifyDataSetChanged();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        checkDBForNote();
    }
}
