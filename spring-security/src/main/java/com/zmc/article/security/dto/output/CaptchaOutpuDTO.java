package com.zmc.article.security.dto.output;

/**
 * @author: Demo
 * @date: 2021-03-22 11:56
 */
public class CaptchaOutpuDTO {

    /**
     * 验证码ID
     */
    private String id;

    /**
     * 验证码图片base64
     */
    private String data;

    public String getId() {
        return id;
    }

    public CaptchaOutpuDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getData() {
        return data;
    }

    public CaptchaOutpuDTO setData(String data) {
        this.data = data;
        return this;
    }
}
