package com.swb.springcloud.userservice.pojo;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Apply {

    @Id    //主键
    @GeneratedValue(strategy= GenerationType.IDENTITY)//自增策略
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false)
    private boolean handle;

    @Column(nullable = false)
    private boolean result;

    protected Apply() {
    }

    public Apply(User user) {
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(o==null)
           return false;
        Apply apply = (Apply) o;
        if(user==null || apply.getUser()==null)
            return false;
        if(user.getId().equals(apply.getUser().getId())){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "Apply{" +
                "id=" + id +
                ", user=" + user +
                ", handle=" + handle +
                ", result=" + result +
                '}';
    }
}
