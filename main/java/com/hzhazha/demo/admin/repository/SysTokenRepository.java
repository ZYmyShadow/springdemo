package com.hzhazha.demo.admin.repository;

import com.hzhazha.demo.admin.bean.SysToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface SysTokenRepository extends JpaRepository<SysToken,Integer> {
    /**
     * 通过token查找
     * @param token
     * @return
     */
    SysToken findByToken(String token);

    /**
     * 通过userID查找
     * @param userId
     * @return
     */
    SysToken findByUserId(Integer userId);

}
