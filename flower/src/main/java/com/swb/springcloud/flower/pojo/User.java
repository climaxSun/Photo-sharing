package com.swb.springcloud.flower.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class User {

    @Id    //主键
    @GeneratedValue(strategy= GenerationType.IDENTITY)//自增策略
    private Long id;

    @NotEmpty(message="姓名不能为空")
    @Size(min=2, max=20)
    @Column(nullable = false, length = 20) // 映射为字段，值不能为空
    private String name;

    @NotEmpty(message = "邮箱不能为空")
    @Size(max=50)
    @Email(message= "邮箱格式不对" )
    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @NotEmpty(message = "账号不能为空")
    @Size(min=3, max=20)
    @Column(nullable = false, length = 20, unique = true)
    private String username; // 用户账号，用户登录时的唯一标识

    @NotEmpty(message = "密码不能为空")
    @Size(max=100,min=6)
    @Column(length = 100)
    private String password; // 登录时密码

    @Column(length = 200)
    private String avatar; // 头像图片地址

    @Column(nullable = false)
    private Integer lv;

    @Column(length = 200)
    private String introduction;

    @Column(insertable = false,columnDefinition = "int default 0")
    private Integer sharesNumber;

    @Transient
    private String token;

    @Transient
    private String yzm;

    protected User() {//防止直接使用
        super();
    }

    public User(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Integer getLv() {
        return lv;
    }

    public void setLv(Integer lv) {
        this.lv = lv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getYzm() {
        return yzm;
    }

    public void setYzm(String yzm) {
        this.yzm = yzm;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getSharesNumber() {
        return sharesNumber;
    }

    public void setSharesNumber(Integer sharesNumber) {
        this.sharesNumber = sharesNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", lv=" + lv +
                ", introduction='" + introduction + '\'' +
                ", sharesNumber=" + sharesNumber +
                ", token='" + token + '\'' +
                ", yzm='" + yzm + '\'' +
                '}';
    }
}
