package com.swb.springcloud.comment_vote.pojo;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Like 实体
 */
@Entity // 实体
public class Vote implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long id; // 用户的唯一标识

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "flowerId", nullable = false)
    private Long flowerId;

    @Column(nullable = false) // 映射为字段，值不能为空
    @CreationTimestamp  // 由数据库自动创建时间
    private Timestamp createTime;

    protected Vote() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser() {
        return userId;
    }

    public void setUser(Long userId) {
        this.userId = userId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Long getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(Long flowerId) {
        this.flowerId = flowerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Vote(Long flowerId, Long userId) {
        this.userId = userId;
        this.flowerId = flowerId;
    }
}
