package com.zmc.article.security.redis;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Demo
 * @date: 2021-09-07 9:36
 */
@Component
public class PhoneCodeCaptchaRedis {

    /**
     * 换成您自己的缓存，因为是演示代码，所以用map暂时替代一下.
     */
/*    @Resource
    private RedisService redisService;*/

    public static final String PHONE_KEY = "captcha:phonecode:";

    private Map<String, String> tempMap = new HashMap<>();

    public void setPhoneCode(String key, String code) {
        //redisService.setValue(PHONE_KEY + key, code, TimeSecondValue.FIVE_MINUTE);
        tempMap.put(PHONE_KEY + key, code);
    }

    public String getPhoneCode(String key) {
        //return redisService.getValue(PHONE_KEY + key);
        return tempMap.get(PHONE_KEY + key);
    }

    public void deletePhoneCode(String key) {
        //redisService.delete(PHONE_KEY + key);
        tempMap.remove(PHONE_KEY + key);
    }
}
