package com.zmc.article.security.filter;

import com.zmc.article.security.redis.ImageCodeCaptchaRedis;
import com.zmc.article.security.util.web.response.JsonResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author: Demo
 * @date: 2021-05-18 15:25
 */
@Component
public class ImageCodeFilter extends OncePerRequestFilter {

    @Resource
    private ImageCodeCaptchaRedis imageCodeCaptchaRedis;

    private static final String[] PROCESS_URL = {"/login"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        for (String pattern : PROCESS_URL) {
            if (!Objects.equals(pattern, uri)) {
                continue;
            }
            String id = request.getParameter("id");
            String code = request.getParameter("code");
            if (StringUtils.isBlank(id)) {
                JsonResult.error("验证码标识不能为空");
                return;
            }
            if (StringUtils.isBlank(code)) {
                JsonResult.error("验证码不能为空");
                return;
            }
            String cacheCode = imageCodeCaptchaRedis.getCaptchaKey(id);
            if (StringUtils.isEmpty(cacheCode)) {
                JsonResult.error("验证码已过期，请重新输入！");
                return;
            }
            imageCodeCaptchaRedis.deleteCaptcha(id);
            if (!cacheCode.equalsIgnoreCase(code)) {
                JsonResult.error("验证码不正确！");
                return;
            }
            break;
        }
        filterChain.doFilter(request, response);
    }
}
