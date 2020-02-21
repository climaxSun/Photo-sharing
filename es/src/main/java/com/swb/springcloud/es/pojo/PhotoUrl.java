package com.swb.springcloud.es.pojo;


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

    public PhotoUrl(String url) {
        this.url = url;
    }

    public PhotoUrl() {
        // TODO Auto-generated constructor stub
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
