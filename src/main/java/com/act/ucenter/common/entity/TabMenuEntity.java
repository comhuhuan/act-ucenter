package com.act.ucenter.common.entity;

import javax.persistence.*;

/**
 * TabMenuEntity
 * Description:
 * author: Administrator
 * 2017/10/26 0026
 */
@Entity
@Table(name = "tab_menu", schema = "testdb", catalog = "")
public class TabMenuEntity {
    private Integer menuId;
    private Integer parentId;
    private String menuNameCh;
    private String menuNameEn;
    private String menuNameBig;
    private String menuTitleCh;
    private String menuTitleEn;
    private String menuTitleBig;
    private String menuImage;
    private String menuUrl;
    private String menuParameter;
    private Integer showSeq;
    private String opType;
    private String verflag;

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
    @Column(name = "menu_name_en")
    public String getMenuNameEn() {
        return menuNameEn;
    }

    public void setMenuNameEn(String menuNameEn) {
        this.menuNameEn = menuNameEn;
    }

    @Basic
    @Column(name = "menu_name_big")
    public String getMenuNameBig() {
        return menuNameBig;
    }

    public void setMenuNameBig(String menuNameBig) {
        this.menuNameBig = menuNameBig;
    }

    @Basic
    @Column(name = "menu_title_ch")
    public String getMenuTitleCh() {
        return menuTitleCh;
    }

    public void setMenuTitleCh(String menuTitleCh) {
        this.menuTitleCh = menuTitleCh;
    }

    @Basic
    @Column(name = "menu_title_en")
    public String getMenuTitleEn() {
        return menuTitleEn;
    }

    public void setMenuTitleEn(String menuTitleEn) {
        this.menuTitleEn = menuTitleEn;
    }

    @Basic
    @Column(name = "menu_title_big")
    public String getMenuTitleBig() {
        return menuTitleBig;
    }

    public void setMenuTitleBig(String menuTitleBig) {
        this.menuTitleBig = menuTitleBig;
    }

    @Basic
    @Column(name = "menu_image")
    public String getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
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
    @Column(name = "menu_parameter")
    public String getMenuParameter() {
        return menuParameter;
    }

    public void setMenuParameter(String menuParameter) {
        this.menuParameter = menuParameter;
    }

    @Basic
    @Column(name = "show_seq")
    public Integer getShowSeq() {
        return showSeq;
    }

    public void setShowSeq(Integer showSeq) {
        this.showSeq = showSeq;
    }

    @Basic
    @Column(name = "op_type")
    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    @Basic
    @Column(name = "verflag")
    public String getVerflag() {
        return verflag;
    }

    public void setVerflag(String verflag) {
        this.verflag = verflag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TabMenuEntity that = (TabMenuEntity) o;

        if (menuId != null ? !menuId.equals(that.menuId) : that.menuId != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (menuNameCh != null ? !menuNameCh.equals(that.menuNameCh) : that.menuNameCh != null) return false;
        if (menuNameEn != null ? !menuNameEn.equals(that.menuNameEn) : that.menuNameEn != null) return false;
        if (menuNameBig != null ? !menuNameBig.equals(that.menuNameBig) : that.menuNameBig != null) return false;
        if (menuTitleCh != null ? !menuTitleCh.equals(that.menuTitleCh) : that.menuTitleCh != null) return false;
        if (menuTitleEn != null ? !menuTitleEn.equals(that.menuTitleEn) : that.menuTitleEn != null) return false;
        if (menuTitleBig != null ? !menuTitleBig.equals(that.menuTitleBig) : that.menuTitleBig != null) return false;
        if (menuImage != null ? !menuImage.equals(that.menuImage) : that.menuImage != null) return false;
        if (menuUrl != null ? !menuUrl.equals(that.menuUrl) : that.menuUrl != null) return false;
        if (menuParameter != null ? !menuParameter.equals(that.menuParameter) : that.menuParameter != null)
            return false;
        if (showSeq != null ? !showSeq.equals(that.showSeq) : that.showSeq != null) return false;
        if (opType != null ? !opType.equals(that.opType) : that.opType != null) return false;
        if (verflag != null ? !verflag.equals(that.verflag) : that.verflag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = menuId != null ? menuId.hashCode() : 0;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (menuNameCh != null ? menuNameCh.hashCode() : 0);
        result = 31 * result + (menuNameEn != null ? menuNameEn.hashCode() : 0);
        result = 31 * result + (menuNameBig != null ? menuNameBig.hashCode() : 0);
        result = 31 * result + (menuTitleCh != null ? menuTitleCh.hashCode() : 0);
        result = 31 * result + (menuTitleEn != null ? menuTitleEn.hashCode() : 0);
        result = 31 * result + (menuTitleBig != null ? menuTitleBig.hashCode() : 0);
        result = 31 * result + (menuImage != null ? menuImage.hashCode() : 0);
        result = 31 * result + (menuUrl != null ? menuUrl.hashCode() : 0);
        result = 31 * result + (menuParameter != null ? menuParameter.hashCode() : 0);
        result = 31 * result + (showSeq != null ? showSeq.hashCode() : 0);
        result = 31 * result + (opType != null ? opType.hashCode() : 0);
        result = 31 * result + (verflag != null ? verflag.hashCode() : 0);
        return result;
    }
}
