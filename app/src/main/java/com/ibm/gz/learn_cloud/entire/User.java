package com.ibm.gz.learn_cloud.entire;

import java.io.Serializable;
import java.util.List;

/**
 * Created by host on 2015/8/13.
 */
public class User implements Serializable{
    private int id;
    private String username;
    private String pwd;
    private String mail;
    private String phone;
    private String avater;
    private String detail;
    private List<Interest> interests;

    public int getId() {
        return id;
    }

    public void setId(int u_id) {
        this.id = u_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }
}
