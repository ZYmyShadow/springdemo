package com.hzhazha.demo.admin.repository;

import com.hzhazha.demo.admin.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface RoleRepository extends JpaRepository<Role,Integer> {
}
