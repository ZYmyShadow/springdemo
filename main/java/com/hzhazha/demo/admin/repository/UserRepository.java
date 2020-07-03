package com.hzhazha.demo.admin.repository;

import com.hzhazha.demo.admin.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername( String username);
    User findByUserId(Integer userId);
}
