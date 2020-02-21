package com.swb.springcloud.flower.pojo;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
public class Article {

    @Id                                                     //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 自增长策略
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Lob  // 大对象，映射 MySQL 的 Long Text 类型
    @Basic(fetch=FetchType.LAZY) // 懒加载
    @NotEmpty(message = "内容不能为空")
    @Size(min=2)
    @Column(nullable = false) // 映射为字段，值不能为空
    private String content;

    @NotEmpty(message = "标题不能为空")
    @Size(min=2, max=50)
    @Column(nullable = false, length = 50) // 映射为字段，值不能为空
    private String title;

    @Column(nullable = false) // 映射为字段，值不能为空
    @org.hibernate.annotations.CreationTimestamp  // 由数据库自动创建时间
    private Timestamp createTime;

    @Column(name="tags",length=100)
    private String tags ;  // 标签

    protected Article() {
    }

    public Article(User user, String content,String title) {
        this.user = user;
        this.content = content;
        this.title=title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime.getTime();
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
