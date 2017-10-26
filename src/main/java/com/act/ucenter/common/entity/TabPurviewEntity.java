package com.act.ucenter.common.entity;

import javax.persistence.*;

/**
 * TabPurviewEntity
 * Description:
 * author: Administrator
 * 2017/10/26 0026
 */
@Entity
@Table(name = "tab_purview", schema = "testdb", catalog = "")
public class TabPurviewEntity {
    private Integer id;
    private Integer roleId;
    private String programId;
    private Integer secMenuId;
    private String purviewList;
    private String purviewStr;
    private String isUsbVerify;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "role_id")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "program_id")
    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    @Basic
    @Column(name = "sec_menu_id")
    public Integer getSecMenuId() {
        return secMenuId;
    }

    public void setSecMenuId(Integer secMenuId) {
        this.secMenuId = secMenuId;
    }

    @Basic
    @Column(name = "purview_list")
    public String getPurviewList() {
        return purviewList;
    }

    public void setPurviewList(String purviewList) {
        this.purviewList = purviewList;
    }

    @Basic
    @Column(name = "purview_str")
    public String getPurviewStr() {
        return purviewStr;
    }

    public void setPurviewStr(String purviewStr) {
        this.purviewStr = purviewStr;
    }

    @Basic
    @Column(name = "is_usb_verify")
    public String getIsUsbVerify() {
        return isUsbVerify;
    }

    public void setIsUsbVerify(String isUsbVerify) {
        this.isUsbVerify = isUsbVerify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TabPurviewEntity that = (TabPurviewEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;
        if (programId != null ? !programId.equals(that.programId) : that.programId != null) return false;
        if (secMenuId != null ? !secMenuId.equals(that.secMenuId) : that.secMenuId != null) return false;
        if (purviewList != null ? !purviewList.equals(that.purviewList) : that.purviewList != null) return false;
        if (purviewStr != null ? !purviewStr.equals(that.purviewStr) : that.purviewStr != null) return false;
        if (isUsbVerify != null ? !isUsbVerify.equals(that.isUsbVerify) : that.isUsbVerify != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        result = 31 * result + (programId != null ? programId.hashCode() : 0);
        result = 31 * result + (secMenuId != null ? secMenuId.hashCode() : 0);
        result = 31 * result + (purviewList != null ? purviewList.hashCode() : 0);
        result = 31 * result + (purviewStr != null ? purviewStr.hashCode() : 0);
        result = 31 * result + (isUsbVerify != null ? isUsbVerify.hashCode() : 0);
        return result;
    }
}
