package org.ogcs.okra.example.game.persistence.domain;

import java.io.Serializable;

/**
 * 账号信息
 */
public class MemRole implements Serializable {

    private Long uid;                //  玩家唯一ID
    private String account;            //  登陆账号
    private String name;               //  昵称
    private Integer figure;             //  头像
    private String psw;                //  登陆密码
    private Long timeCreate;         //  创建账号时间  时间戳(毫秒)
    private Long timeLastLogin;      //  最后一次登录时间 时间戳(毫秒)
    private String ipCreate;           //  创建IP
    private String ipLastLogin;        //  最后一次登录ip

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFigure() {
        return figure;
    }

    public void setFigure(Integer figure) {
        this.figure = figure;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public Long getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Long timeCreate) {
        this.timeCreate = timeCreate;
    }

    public Long getTimeLastLogin() {
        return timeLastLogin;
    }

    public void setTimeLastLogin(Long timeLastLogin) {
        this.timeLastLogin = timeLastLogin;
    }

    public String getIpCreate() {
        return ipCreate;
    }

    public void setIpCreate(String ipCreate) {
        this.ipCreate = ipCreate;
    }

    public String getIpLastLogin() {
        return ipLastLogin;
    }

    public void setIpLastLogin(String ipLastLogin) {
        this.ipLastLogin = ipLastLogin;
    }
}
