package com.zmc.article.security.controller;

import com.zmc.article.security.captcha.ImageCodeCaptcha;
import com.zmc.article.security.dto.output.CaptchaOutpuDTO;
import com.zmc.article.security.redis.ImageCodeCaptchaRedis;
import com.zmc.article.security.redis.PhoneCodeCaptchaRedis;
import com.zmc.article.security.util.web.response.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author: Demo
 * @date: 2021-10-28 15:42
 */
@RestController
@RequestMapping(value = "/common")
public class CommonController {

    @Resource
    private ImageCodeCaptchaRedis imageCodeCaptchaRedis;

    @Resource
    private PhoneCodeCaptchaRedis phoneCodeCaptchaRedis;

    @GetMapping(value = "/getImageCode")
    public CaptchaOutpuDTO getImageCode() {
        String id = UUID.randomUUID().toString();
        ImageCodeCaptcha captcha = new ImageCodeCaptcha();
        imageCodeCaptchaRedis.setCaptcha(id, captcha.getCode());
        return new CaptchaOutpuDTO().setId(id).setData(captcha.getBase64Data());
    }

    @GetMapping(value = "/getPhoneSmsCode")
    public void getPhoneSmsCode(String phone) {
        //这里只是示例，一些安全校验、风控限制请自行解决
        int rand = (int) (Math.random() * 1000000);
        phoneCodeCaptchaRedis.setPhoneCode(phone, String.valueOf(rand));
        JsonResult.success(rand);
    }
}
