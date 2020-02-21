package com.swb.springcloud.comment_vote.pojo;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Report {

    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long id;

    @Column(nullable = false,name = "type") // 映射为字段，值不能为空
    private String type;

    @Column(nullable = false,name = "reportedId")
    private Long reportedId;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private boolean handle;

    @Column(nullable = false)
    private boolean result;

    @Column(nullable = false) // 映射为字段，值不能为空
    @CreationTimestamp  // 由数据库自动创建时间
    private Timestamp createTime;

    public Report() {
    }

    public Report(String type, Long reportedId, String reason) {
        this.type = type;
        this.reportedId = reportedId;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getReportedId() {
        return reportedId;
    }

    public void setReportedId(Long reportedId) {
        this.reportedId = reportedId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isHandle() {
        return handle;
    }

    public void setHandle(boolean handle) {
        this.handle = handle;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", reportedId=" + reportedId +
                ", reason='" + reason + '\'' +
                ", handle=" + handle +
                ", result=" + result +
                ", createTime=" + createTime +
                '}';
    }
}
