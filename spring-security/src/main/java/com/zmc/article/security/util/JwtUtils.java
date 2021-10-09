package com.zmc.article.security.util;

import com.zmc.article.security.userdetails.UserCommonDetail;
import com.zmc.article.security.util.web.RequestUtil;
import com.zmc.article.security.util.web.response.JsonResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author: Demo
 * @date: 2021-09-22 16:50
 */
@Component
public class JwtUtils {

    private static final String SECRET_KEY = "5fa463fab7fa0af0742c31627950ef64f544a462";

    public static final long ONE_WEEK = 1000 * 60 * 60 * 24 * 7;

    /**
     * 生成jwt token
     *
     * @param id
     * @return java.lang.String
     * @throws
     * @author Demo
     * @date 2021-09-23 9:50
     */
    public String createToken(long id) {
        return Jwts.builder()
                .claim("id", id)
                .claim("token", UUID.randomUUID().toString())
                .claim("expire", System.currentTimeMillis() + ONE_WEEK)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * 客户端请求API时，验证token是否有效
     *
     * @param token
     * @return boolean
     * @throws
     * @author Demo
     * @date 2021-09-23 9:50
     */
    public boolean validateToken(String token) {
        Long id = null;
        try {
            id = parseSession(token);
        } catch (Exception e) {
            JsonResult.error(e.getMessage());
            return false;
        }
        Set<GrantedAuthority> authority = new HashSet();
        UserCommonDetail detail = new UserCommonDetail()
                .setUsername(null)
                .setId(id)
                .setAuthorities(authority);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(detail, null, authority);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }

    public Long parseSession(String token) {
        if (StringUtils.isBlank(token)) {
            throw new RuntimeException("请登录");
        }
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        long expire = Long.parseLong(String.valueOf(claims.get("expire")));
        if (System.currentTimeMillis() > expire) {
            throw new RuntimeException("token过期");
        }
        return Long.parseLong(String.valueOf(claims.get("id")));
    }

    /**
     * 获取当前用户ID
     *
     * @param
     * @return long
     * @throws
     * @author Demo
     * @date 2021-09-23 9:51
     */
    public static long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserCommonDetail backendManagerDetail = (UserCommonDetail) authentication.getPrincipal();
            return backendManagerDetail.getId();
        }
        String token = RequestUtil.getParameter("token");
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(String.valueOf(claims.get("id")));
    }
}

