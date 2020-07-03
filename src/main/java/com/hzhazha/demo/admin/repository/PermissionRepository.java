package com.hzhazha.demo.admin.repository;

import com.hzhazha.demo.admin.bean.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PermissionRepository extends JpaRepository<Permission,Integer> {
}
