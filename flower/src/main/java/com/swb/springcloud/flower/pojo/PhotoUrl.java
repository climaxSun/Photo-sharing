package com.swb.springcloud.flower.pojo;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class PhotoUrl {

    @Id                                                     //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 自增长策略
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false) // 映射为字段，值不能为空
    @CreationTimestamp  // 由数据库自动创建时间
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

    protected PhotoUrl() {
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
