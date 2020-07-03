package com.hzhazha.demo.shiro.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.DigestUtils;

public class Algorithm {
    public static String MD5encrypt(String str) {
        try {
            return DigestUtils.md5DigestAsHex(str.getBytes("UTF-8"));
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public static String BASE64encrypt(String str){
        try {
            return Base64.encodeBase64String(str.getBytes("UTF-8"));
        }catch (Exception e){
            System.out.println(e);
            return null;
        }    }

    public static String BASE64decrypt(String str){
        try {
            return new String(Base64.decodeBase64(str),"UTF-8");
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
