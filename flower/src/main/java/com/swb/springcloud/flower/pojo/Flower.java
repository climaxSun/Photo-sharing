package com.swb.springcloud.flower.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Flower {

    @Id                                                     //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 自增长策略
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Lob  // 大对象，映射 MySQL 的 Long Text 类型
    @Basic(fetch = FetchType.LAZY) // 懒加载
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false) // 映射为字段，值不能为空
    private String content;

    @Column(nullable = false) // 映射为字段，值不能为空
    @CreationTimestamp  // 由数据库自动创建时间
    private Timestamp createTime;

    @Column(name = "commentSize")
    private Integer commentSize = 0;  // 评论量

    @Column(name = "voteSize")
    private Integer voteSize = 0;  // 点赞量

    @Column(name = "tags", length = 100)
    private String tags;  // 标签

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "flower_photoUrl", joinColumns = @JoinColumn(name = "flower_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "photoUrl_id", referencedColumnName = "id"))
    @OrderBy("createTime ASC")//按创建时间排序  越早越前
    private List<PhotoUrl> photoUrls;

    @Transient
    private String userName;

    @Transient
    private String userAvatar;

    @Transient
    private boolean isVote;

    public Flower() {
        this.isVote=false;
    }

    public Flower(String content,String[] photoUrls) {
        this.content = content;
        this.setPhotoUrls(photoUrls);
        this.isVote=false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return (createTime.getTime());
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(Integer commentSize) {
        this.commentSize = commentSize;
    }

    public Integer getVoteSize() {
        return voteSize;
    }

    public void setVoteSize(Integer voteSize) {
        this.voteSize = voteSize;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<PhotoUrl> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(String[] photoUrls) {
        List<PhotoUrl> pus=new ArrayList<>();
        for(String pu:photoUrls){
            pus.add(new PhotoUrl(pu));
        }
        this.photoUrls = pus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public boolean getIsVote() {
        return isVote;
    }

    public void setIsVote(boolean isVote) {
        this.isVote = isVote;
    }

    public void serUser(UserReturn user){
        this.userName=user.getName();
        this.userAvatar=user.getAvatar();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userid) {
        this.userId = userid;
    }

    @Override
    public String toString() {
        return "Flower{" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", commentSize=" + commentSize +
                ", voteSize=" + voteSize +
                ", tags='" + tags + '\'' +
                ", photoUrls=" + photoUrls +
                ", userName='" + userName + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", isVote=" + isVote +
                '}';
    }
}
