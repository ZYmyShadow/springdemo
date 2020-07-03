package com.hzhazha.demo.admin.bean;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "shiro_role")
public class Role {
    @Id
    @GeneratedValue
    private Integer roleId;
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "shiro_role_permission",
            joinColumns = { @JoinColumn(name = "role_id",referencedColumnName = "roleId") },
            inverseJoinColumns = {@JoinColumn(name = "permission_id",referencedColumnName = "permissionId") })
    private List<Permission> permissions;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
