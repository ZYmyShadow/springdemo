package com.hzhazha.demo.admin.service;


import com.hzhazha.demo.admin.bean.SysToken;
import com.hzhazha.demo.admin.bean.User;

import java.util.Map;

public interface LoginService {
    /**
     * Find user by username
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * create token by userId
     * @param userId
     * @return
     */
    Map<String,Object> createToken(Integer userId);

    /**
     * logout
     * @param token
     */
    void logout(String token);

    /**
     * find token by token
     * @param accessToken
     * @return
     */
    SysToken findByToken(String accessToken);

    /**
     * find user by userId
     * @param userId
     * @return
     */
    User findByUserId(Integer userId);
}
