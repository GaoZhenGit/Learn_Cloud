package com.ibm.gz.learn_cloud.entire;

import java.io.Serializable;

/**
 * Created by gz on 15/9/7.
 */
public class Video implements Serializable{
    private String Uri;
    private String name;

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
