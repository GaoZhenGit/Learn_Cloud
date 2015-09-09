package com.ibm.gz.learn_cloud.fragment.FirstPageChildFgm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.Adapter.NoteAdapter;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.Utils.LogUtil;
import com.ibm.gz.learn_cloud.activity.CourseActivity;
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
        return contextView;
    }

    private void initData() {
        course=((CourseActivity)getActivity()).getCourse();
        noteList=new ArrayList<>();
        Note note1=new Note();
        note1.setId(123456);
        note1.setCourseName("html");
        SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        note1.setTime(formats.format(Calendar.getInstance().getTime()));
        DbUtils db=DbUtils.create(getActivity());
        try {
            db.save(note1);
            LogUtil.i("----------db", "save success");
            noteList=db.findAll(Selector.from(Note.class));
        } catch (DbException e) {
            e.printStackTrace();
        }
        StringBuffer stringBuffer=new StringBuffer();
        for (Note n:noteList){
            stringBuffer.append(n.getTime()+"\n");
        }
        Toast.makeText(getActivity(),stringBuffer.toString(),Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        listView=(ListView)contextView.findViewById(R.id.note_list);

    }
}
