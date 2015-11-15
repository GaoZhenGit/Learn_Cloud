package com.ibm.gz.learn_cloud.activity;

import android.widget.ListView;

import com.androidquery.AQuery;
import com.ibm.gz.learn_cloud.Adapter.DiscussAdapter;
import com.ibm.gz.learn_cloud.Adapter.QuestionAdapter;
import com.ibm.gz.learn_cloud.R;
import com.ibm.gz.learn_cloud.entire.Discuss;
import com.ibm.gz.learn_cloud.entire.Question;
import com.ibm.gz.learn_cloud.entire.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ask question to teacher and discuss
 * Created by host on 2015/11/14.
 */
public class QuestionActivity extends BasePageActivity {
    private AQuery aQuery;
    private boolean isTeacher = true;

    private ListView listView;
    private QuestionAdapter questionAdapter;
    private DiscussAdapter discussAdapter;

    private List<Question> questionList;
    private List<Discuss> discussList;


    @Override
    protected void initData() {

    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_question);
        aQuery = new AQuery(this);
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.listview);

        questionList = new ArrayList<>();
        discussList = new ArrayList<>();

        setStaticData();//静态数据

        questionAdapter = new QuestionAdapter(questionList);
        discussAdapter = new DiscussAdapter(discussList);
        listView.setAdapter(questionAdapter);
        listView.setDivider(null);
        listView.setClickable(false);
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.title_left_btn).clicked(this, "onBackPressed");
        aQuery.id(R.id.title_left).clicked(this, "changeToTeacher");
        aQuery.id(R.id.title_right).clicked(this, "changeToStudent");
    }

    public void changeToTeacher() {
        if (isTeacher) {
            return;
        }
        aQuery.id(R.id.title_left).backgroundColorId(R.color.white);
        aQuery.id(R.id.title_right).background(R.drawable.title_stock);
        isTeacher = true;

        listView.setAdapter(questionAdapter);
    }

    public void changeToStudent() {
        if (!isTeacher) {
            return;
        }
        aQuery.id(R.id.title_right).backgroundColorId(R.color.white);
        aQuery.id(R.id.title_left).background(R.drawable.title_stock);
        isTeacher = false;

        listView.setAdapter(discussAdapter);
    }

    private void setStaticData() {
        for (int i = 0; i < 10; i++) {
            Discuss discuss = new Discuss();
            discuss.setContent("这个是" + i + "号评论");
            User user = new User();
            user.setAvater("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2728905284,2325510692&fm=116&gp=0.jpg");
            user.setUsername(i + "tom");
            discuss.setUser(user);
            discussList.add(discuss);
        }

        for (int i = 0; i < 20; i++) {
            Question question = new Question();
            question.setIsFromTeacher(i < 5);
            int radomTime = (int) (Math.random() * 10);
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < radomTime; j++) {
                stringBuilder.append(j + "hello world ");
            }
            question.setContent(stringBuilder.toString());
//            question.setContent("你好你好你好你好你好你好你好你好你好你好你好你好");
            questionList.add(question);
        }
    }
}
