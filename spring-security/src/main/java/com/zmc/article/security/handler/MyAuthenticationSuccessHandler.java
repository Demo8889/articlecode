package com.zmc.article.security.handler;

import com.zmc.article.security.userdetails.UserCommonDetail;
import com.zmc.article.security.util.JwtUtils;
import com.zmc.article.security.util.web.response.JsonResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Demo
 * @date: 2021-05-17 13:05
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        UserCommonDetail userCommonDetail = (UserCommonDetail) authentication.getPrincipal();
        JsonResult.success(jwtUtils.createToken(userCommonDetail.getId()));
    }

}
