package com.xgxfd.moocback.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.util.Random;

@Slf4j
public class CommonUtil {

    public static String MD5(String key){
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try{
            byte[] btInput = key.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);

            byte[] md = mdInst.digest();

            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for(int i = 0;i < j;i++){
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return  new String(str);
        }catch (Exception e){
            log.error("生成MD5失败,错误信息:"+e.getMessage());
            return null;

        }
    }

    public StringBuffer registValid(StringBuffer valid){
        String sources = "0123456789abcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();
        valid = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            valid.append(sources.charAt(rand.nextInt(36)));
        }
        log.info("验证码为：" + valid);
        return  valid;
    }
}
