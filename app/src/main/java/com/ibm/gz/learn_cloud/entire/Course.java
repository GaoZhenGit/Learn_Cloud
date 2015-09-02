package com.ibm.gz.learn_cloud.entire;

import java.io.Serializable;

/**
 * Created by host on 2015/8/9.
 */
public class Course implements Serializable{
    private int course_id;
    private String course_name;
    private String course_video;
    private String course_img;
    private String detail;
    private User teacher;

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_video() {
        return course_video;
    }

    public void setCourse_video(String course_video) {
        this.course_video = course_video;
    }

    public String getCourse_img() {
        return course_img;
    }

    public void setCourse_img(String course_img) {
        this.course_img = course_img;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }
}
