package com.act.ucenter.common.entity;

import javax.persistence.*;

/**
 * TabSysconfigEntity
 * Description:
 * author: Administrator
 * 2017/10/26 0026
 */
@Entity
@Table(name = "tab_sysconfig", schema = "testdb", catalog = "")
public class TabSysconfigEntity {
    private String configid;
    private String defaultval;
    private String configval;
    private String note;

    @Id
    @Column(name = "configid")
    public String getConfigid() {
        return configid;
    }

    public void setConfigid(String configid) {
        this.configid = configid;
    }

    @Basic
    @Column(name = "defaultval")
    public String getDefaultval() {
        return defaultval;
    }

    public void setDefaultval(String defaultval) {
        this.defaultval = defaultval;
    }

    @Basic
    @Column(name = "configval")
    public String getConfigval() {
        return configval;
    }

    public void setConfigval(String configval) {
        this.configval = configval;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TabSysconfigEntity that = (TabSysconfigEntity) o;

        if (configid != null ? !configid.equals(that.configid) : that.configid != null) return false;
        if (defaultval != null ? !defaultval.equals(that.defaultval) : that.defaultval != null) return false;
        if (configval != null ? !configval.equals(that.configval) : that.configval != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = configid != null ? configid.hashCode() : 0;
        result = 31 * result + (defaultval != null ? defaultval.hashCode() : 0);
        result = 31 * result + (configval != null ? configval.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }
}
