package com.zmc.article.security.filter;

import com.zmc.article.security.authentication.PhoneLoginAuthenticationToken;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Demo
 * @date: 2021-10-29 10:07
 */
public class PhoneLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    //这里换成前端提交的 手机号 字段
    public static final String SPRING_SECURITY_FORM_PHONE_KEY = "phone";
    //这里换成前端提交的 验证码 字段
    public static final String SPRING_SECURITY_FORM_SMS_CODE_KEY = "smsCode";

    //这里换成我们手机号登录的网址
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/phoneLogin",
            "POST");

    private String phoneParameter = SPRING_SECURITY_FORM_PHONE_KEY;

    private String smsCodeParameter = SPRING_SECURITY_FORM_SMS_CODE_KEY;

    private boolean postOnly = true;

    public PhoneLoginAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public PhoneLoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = obtainPhone(request);
        username = (username != null) ? username : "";
        username = username.trim();
        String smsCode = obtainSmsCode(request);
        smsCode = (smsCode != null) ? smsCode : "";
        //我们创建的PhoneLoginAuthenticationToken
        PhoneLoginAuthenticationToken authRequest = new PhoneLoginAuthenticationToken(username, smsCode);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Nullable
    protected String obtainSmsCode(HttpServletRequest request) {
        return request.getParameter(this.smsCodeParameter);
    }

    @Nullable
    protected String obtainPhone(HttpServletRequest request) {
        return request.getParameter(this.phoneParameter);
    }

    protected void setDetails(HttpServletRequest request, PhoneLoginAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPhoneParameter(String phoneParameter) {
        Assert.hasText(phoneParameter, "phone parameter must not be empty or null");
        this.phoneParameter = phoneParameter;
    }

    public void setSmsCodeParameter(String smsCodeParameter) {
        Assert.hasText(smsCodeParameter, "smsCode parameter must not be empty or null");
        this.smsCodeParameter = smsCodeParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getPhoneParameter() {
        return this.phoneParameter;
    }

    public final String getSmsCodeParameter() {
        return this.smsCodeParameter;
    }

}
