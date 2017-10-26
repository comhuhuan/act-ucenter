package com.act.ucenter.common.entity;

import javax.persistence.*;

/**
 * TabRoleEntity
 * Description:
 * author: Administrator
 * 2017/10/26 0026
 */
@Entity
@Table(name = "tab_role", schema = "testdb", catalog = "")
public class TabRoleEntity {
    private Integer roleId;
    private String roleName;
    private String roleDesc;
    private String programId;
    private String createuser;

    @Id
    @Column(name = "role_id")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "role_desc")
    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
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
    @Column(name = "createuser")
    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TabRoleEntity that = (TabRoleEntity) o;

        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;
        if (roleName != null ? !roleName.equals(that.roleName) : that.roleName != null) return false;
        if (roleDesc != null ? !roleDesc.equals(that.roleDesc) : that.roleDesc != null) return false;
        if (programId != null ? !programId.equals(that.programId) : that.programId != null) return false;
        if (createuser != null ? !createuser.equals(that.createuser) : that.createuser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId != null ? roleId.hashCode() : 0;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (roleDesc != null ? roleDesc.hashCode() : 0);
        result = 31 * result + (programId != null ? programId.hashCode() : 0);
        result = 31 * result + (createuser != null ? createuser.hashCode() : 0);
        return result;
    }
}
