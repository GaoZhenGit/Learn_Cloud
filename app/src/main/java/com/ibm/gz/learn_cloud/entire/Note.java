package com.ibm.gz.learn_cloud.entire;

import com.lidroid.xutils.db.annotation.Id;

import java.io.Serializable;

/**
 * Created by host on 2015/9/9.
 */
public class Note implements Serializable{
    @Id(column = "id")
    private int id;
    private String time;
    private String courseName;
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
