package com.zmc.article.security.redis;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Demo
 * @date: 2021-09-07 9:36
 */
@Component
public class ImageCodeCaptchaRedis {

    /**
     * 换成您自己的缓存，因为是演示代码，所以用map暂时替代一下.
     */
/*    @Resource
    private RedisService redisService;*/

    public static final String CAPTCHA_KEY = "captcha:imagecode:";

    private Map<String, String> tempMap = new HashMap<>();

    public void setCaptcha(String key, String code) {
        //redisService.setValue(CAPTCHA_KEY + key, code, TimeSecondValue.FIVE_MINUTE);
        tempMap.put(CAPTCHA_KEY + key, code);
    }

    public String getCaptchaKey(String key) {
        //return redisService.getValue(CAPTCHA_KEY + key);
        return tempMap.get(CAPTCHA_KEY + key);
    }

    public void deleteCaptcha(String key) {
        //redisService.delete(CAPTCHA_KEY + key);
        tempMap.remove(CAPTCHA_KEY + key);
    }
}
