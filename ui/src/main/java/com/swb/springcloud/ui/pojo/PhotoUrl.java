package com.swb.springcloud.ui.pojo;

import java.sql.Timestamp;

public class PhotoUrl {

    private Long id;

    private String url;

    private Timestamp createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PhotoUrl() {

    }

    public PhotoUrl(Long id, String url, Timestamp createTime) {
        this.id = id;
        this.url = url;
        this.createTime = createTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PhotoUrl{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
