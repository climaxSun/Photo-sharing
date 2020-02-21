package com.swb.springcloud.comment_vote.pojo;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * Comment 实体
 * 
 */
@Entity // 实体
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id // 主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	private Long id; // 用户的唯一标识

	@NotEmpty(message = "评论内容不能为空")
	@Size(min=2, max=500)
	@Column(nullable = false) // 映射为字段，值不能为空
	private String content;

	@Column(name="userId",nullable = false)
	private Long userId;

	@Column(name="flowerId",nullable = false)
	private Long flowerId;
	
	@Column(nullable = false) // 映射为字段，值不能为空
	@CreationTimestamp  // 由数据库自动创建时间
	private Timestamp createTime;

	@Transient
	private String username;

	@Transient
	private String userAvatar;

	protected Comment() {

	}

	public Comment(Long userId, String content, Long flowerId) {
		this.content = content;
		this.userId = userId;
		this.flowerId=flowerId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFlowerId() {
		return flowerId;
	}

	public void setFlowerId(Long flowerId) {
		this.flowerId = flowerId;
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

	@Override
	public String toString() {
		return "Comment{" +
				"id=" + id +
				", content='" + content + '\'' +
				", userId=" + userId +
				", flowerId=" + flowerId +
				", createTime=" + getCreateTime() +
				", username='" + username + '\'' +
				", userAvatar='" + userAvatar + '\'' +
				'}';
	}

	public Long getCreateTime() {
		return createTime.getTime();
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}


}
