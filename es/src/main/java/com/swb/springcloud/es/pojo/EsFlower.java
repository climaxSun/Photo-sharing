package com.swb.springcloud.es.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Document(indexName = "flower", type = "flower")
@XmlRootElement
public class EsFlower implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id  // 主键
    private String id;

    private Long flowerId;

    @Field(analyzer = "stop",type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Text,analyzer = "stop",fielddata = true)
    private String tags;  // 标签

    private Long userId;  // 标签

    private String username;  // 标签

    private String userAvatar;  // 标签

    private Integer commentSize = 0;  // 评论量

    private Integer voteSize = 0;  // 点赞量

    private List<PhotoUrl> photoUrls;

    private Timestamp createTime;

    protected EsFlower() {  // JPA 的规范要求无参构造函数；设为 protected 防止直接使用
    }

    private EsFlower(String content){
        this.content=content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public EsFlower(Flower flower){
        this.content=flower.getContent();
        this.tags=flower.getTags();
        this.commentSize=flower.getCommentSize();
        this.voteSize=flower.getVoteSize();
        this.flowerId=flower.getId();
        this.createTime=new Timestamp(flower.getCreateTime());
        this.userId=flower.getUserId();
        this.photoUrls=flower.getPhotoUrls();
        this.username=flower.getUserName();
        this.userAvatar=flower.getUserAvatar();
    }

    public void update(Flower flower){
        this.content=flower.getContent();
        this.tags=flower.getTags();
        this.commentSize=flower.getCommentSize();
        this.voteSize=flower.getVoteSize();
        this.flowerId=flower.getId();
        this.createTime=new Timestamp(flower.getCreateTime());
        this.userId=flower.getUserId();
        this.photoUrls=flower.getPhotoUrls();
        this.username=flower.getUserName();
        this.userAvatar=flower.getUserAvatar();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(Long flowerId) {
        this.flowerId = flowerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public Long getCreateTime() {
        return createTime.getTime();
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public List<PhotoUrl> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<PhotoUrl> photoUrls) {
        this.photoUrls = photoUrls;
    }

    @Override
    public String toString() {
        return "EsFlower{" +
                "id='" + id + '\'' +
                ", flowerId=" + flowerId +
                ", content='" + content + '\'' +
                ", tags='" + tags + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", commentSize=" + commentSize +
                ", voteSize=" + voteSize +
                ", photoUrls=" + photoUrls +
                ", createTime=" + createTime +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public void addCommentSize(){
        commentSize++;
    }

    public void subCommentSize(){
        commentSize--;
    }

    public void addVoteSize(){
        voteSize++;
    }

    public void subVoteSize(){
        voteSize--;
    }
}
