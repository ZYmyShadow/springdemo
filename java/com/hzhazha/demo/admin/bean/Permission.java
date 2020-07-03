package com.hzhazha.demo.admin.bean;

import javax.persistence.*;

@Entity
@Table( name = "shiro_permission")
public class Permission {
    @Id
    @GeneratedValue
    private Integer permissionId;
    private String permissionName;
    private String permission;

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
