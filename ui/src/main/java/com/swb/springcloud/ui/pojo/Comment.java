package com.swb.springcloud.ui.pojo;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * Comment 实体
 * 
 */
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id; // 用户的唯一标识

	private String content;

	private Long userId;

	private Long flowerId;

	private Long createTime;

	private String username;

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
		return createTime;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public void setUser(UserReturn user) {
		this.username = user.getName();
		this.userAvatar = user.getAvatar();
	}
}
