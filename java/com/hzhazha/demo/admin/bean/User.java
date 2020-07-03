package com.hzhazha.demo.admin.bean;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table( name = "shiro_user")
public class User {
    @Id
    @GeneratedValue
    private Integer userId;
    private String username;
    private String password;
    private Integer age;
    private String email;
    private Boolean isDeleted;
    private Date createTime;
    private Date updateTime;
    private String district;
    private String contactNumber;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "shiro_user_role",
            joinColumns = { @JoinColumn(name = "user_id",referencedColumnName = "userId") },
            inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "roleId") })
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
