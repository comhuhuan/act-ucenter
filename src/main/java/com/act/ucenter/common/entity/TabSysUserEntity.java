package com.act.ucenter.common.entity;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * TabSysUserEntity
 * Description:
 * author: Administrator
 * 2017/10/26 0026
 */
@Entity
@Table(name = "tab_sys_user", schema = "testdb", catalog = "")
public class TabSysUserEntity {
    private String userId;
    private String password;
    private String username;
    private String serviceCode;
    private Byte sex;
    private String tel;
    private String mobile;
    private String email;
    private String descrip;
    private Byte state;
    private Timestamp lastTime;
    private Integer loginAmount;
    private Byte isHint;
    private String question;
    private String answer;
    private String createuser;
    private String sysmanage;

    @Id
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "password")
    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "service_code")
    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    @Basic
    @Column(name = "sex")
    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "email")
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "descrip")
    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    @Basic
    @Column(name = "state")
    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    @Basic
    @Column(name = "last_time")
    public Timestamp getLastTime() {
        return lastTime;
    }

    public void setLastTime(Timestamp lastTime) {
        this.lastTime = lastTime;
    }

    @Basic
    @Column(name = "login_amount")
    public Integer getLoginAmount() {
        return loginAmount;
    }

    public void setLoginAmount(Integer loginAmount) {
        this.loginAmount = loginAmount;
    }

    @Basic
    @Column(name = "is_hint")
    public Byte getIsHint() {
        return isHint;
    }

    public void setIsHint(Byte isHint) {
        this.isHint = isHint;
    }

    @Basic
    @Column(name = "question")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Basic
    @Column(name = "answer")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Basic
    @Column(name = "createuser")
    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    @Basic
    @Column(name = "sysmanage")
    public String getSysmanage() {
        return sysmanage;
    }

    public void setSysmanage(String sysmanage) {
        this.sysmanage = sysmanage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TabSysUserEntity that = (TabSysUserEntity) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (serviceCode != null ? !serviceCode.equals(that.serviceCode) : that.serviceCode != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (tel != null ? !tel.equals(that.tel) : that.tel != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (descrip != null ? !descrip.equals(that.descrip) : that.descrip != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (lastTime != null ? !lastTime.equals(that.lastTime) : that.lastTime != null) return false;
        if (loginAmount != null ? !loginAmount.equals(that.loginAmount) : that.loginAmount != null) return false;
        if (isHint != null ? !isHint.equals(that.isHint) : that.isHint != null) return false;
        if (question != null ? !question.equals(that.question) : that.question != null) return false;
        if (answer != null ? !answer.equals(that.answer) : that.answer != null) return false;
        if (createuser != null ? !createuser.equals(that.createuser) : that.createuser != null) return false;
        if (sysmanage != null ? !sysmanage.equals(that.sysmanage) : that.sysmanage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (serviceCode != null ? serviceCode.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (descrip != null ? descrip.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (lastTime != null ? lastTime.hashCode() : 0);
        result = 31 * result + (loginAmount != null ? loginAmount.hashCode() : 0);
        result = 31 * result + (isHint != null ? isHint.hashCode() : 0);
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (createuser != null ? createuser.hashCode() : 0);
        result = 31 * result + (sysmanage != null ? sysmanage.hashCode() : 0);
        return result;
    }
}
