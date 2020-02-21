package com.swb.springcloud.ui.pojo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Flower {

    private Long id;

    private Long userId;

    private String content;

    private Date createTime;

    private Integer commentSize = 0;  // 评论量

    private Integer voteSize = 0;  // 点赞量

    private String tags;  // 标签

    private List<PhotoUrl> photoUrls;

    private String userName;

    private String userAvatar;

    private boolean isVote;

    public Flower() {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = new Date(createTime);
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

    public void setPhotoUrls(List<PhotoUrl> photoUrls) {
        this.photoUrls = photoUrls;
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

    public boolean isVote() {
        return isVote;
    }

    public void setVote(boolean vote) {
        isVote = vote;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Flower(Long id, Long userId, String content, Date createTime, Integer commentSize,
                  Integer voteSize, String tags, List<PhotoUrl> photoUrls, String userName, String userAvatar, boolean isVote) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.createTime = createTime;
        this.commentSize = commentSize;
        this.voteSize = voteSize;
        this.tags = tags;
        this.photoUrls = photoUrls;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.isVote = isVote;
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
