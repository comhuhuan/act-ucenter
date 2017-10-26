package com.act.ucenter.common.entity;

import javax.persistence.*;

/**
 * TabMenuRoleEntity
 * Description:
 * author: Administrator
 * 2017/10/26 0026
 */
@Entity
@Table(name = "tab_menu_role", schema = "testdb", catalog = "")
public class TabMenuRoleEntity {
    private Integer menuId;
    private Integer parentId;
    private String menuNameCh;
    private String menuUrl;
    private Byte showSeq;
    private String isActive;

    @Id
    @Column(name = "menu_id")
    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    @Basic
    @Column(name = "parent_id")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "menu_name_ch")
    public String getMenuNameCh() {
        return menuNameCh;
    }

    public void setMenuNameCh(String menuNameCh) {
        this.menuNameCh = menuNameCh;
    }

    @Basic
    @Column(name = "menu_url")
    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    @Basic
    @Column(name = "show_seq")
    public Byte getShowSeq() {
        return showSeq;
    }

    public void setShowSeq(Byte showSeq) {
        this.showSeq = showSeq;
    }

    @Basic
    @Column(name = "is_active")
    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TabMenuRoleEntity that = (TabMenuRoleEntity) o;

        if (menuId != null ? !menuId.equals(that.menuId) : that.menuId != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (menuNameCh != null ? !menuNameCh.equals(that.menuNameCh) : that.menuNameCh != null) return false;
        if (menuUrl != null ? !menuUrl.equals(that.menuUrl) : that.menuUrl != null) return false;
        if (showSeq != null ? !showSeq.equals(that.showSeq) : that.showSeq != null) return false;
        if (isActive != null ? !isActive.equals(that.isActive) : that.isActive != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = menuId != null ? menuId.hashCode() : 0;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (menuNameCh != null ? menuNameCh.hashCode() : 0);
        result = 31 * result + (menuUrl != null ? menuUrl.hashCode() : 0);
        result = 31 * result + (showSeq != null ? showSeq.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        return result;
    }
}
