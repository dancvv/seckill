package com.xxxxx.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wang
 * @since 2022-04-25
 */
@TableName("t_user")
//@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

//    @ApiModelProperty("用户id，手机号码")
    private Long id;

    private String nickname;

//    @ApiModelProperty("MD5(MD5(PASSWORD))")
    private String password;

    private String slat;

    private String head;

//    @ApiModelProperty("注册时间")
    private Date registerDate;

//    @ApiModelProperty("注册时间")
    private Date lastLoginDate;

//    @ApiModelProperty("登录次数")
    private Integer loginCount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSlat() {
        return slat;
    }

    public void setSlat(String slat) {
        this.slat = slat;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", nickname=" + nickname +
        ", password=" + password +
        ", slat=" + slat +
        ", head=" + head +
        ", registerDate=" + registerDate +
        ", lastLoginDate=" + lastLoginDate +
        ", loginCount=" + loginCount +
        "}";
    }
}
