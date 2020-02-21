package com.swb.springcloud.ui.pojo;

public class UserReturn {

    private  Long id;

    private String name;

    private String username;

    private String avatar; // 头像图片地址

    private String email;

    private String introduction;

    private Integer lv;

    private String token;

    private Integer sharesNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getLv() {
        return lv;
    }

    public void setLv(Integer lv) {
        this.lv = lv;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public UserReturn() {
    }

    public UserReturn(String name, String avatar, Integer lv, String token, String introduction, String email, String username, Integer sharesNumber) {
        this.name = name;
        this.avatar = avatar;
        this.lv = lv;
        this.token = token;
        this.introduction = introduction;
        this.email = email;
        this.username = username;
        this.sharesNumber = sharesNumber;
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

    public Integer getSharesNumber() {
        return sharesNumber;
    }

    public void setSharesNumber(Integer sharesNumber) {
        this.sharesNumber = sharesNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserReturn{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", introduction='" + introduction + '\'' +
                ", lv=" + lv +
                ", token='" + token + '\'' +
                ", sharesNumber=" + sharesNumber +
                '}';
    }

}
