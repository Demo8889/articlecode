package com.zmc.article.security.provider;

import com.zmc.article.security.authentication.PhoneLoginAuthenticationToken;
import com.zmc.article.security.service.PhoneLoginService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * @author: Demo
 * @date: 2021-10-29 10:55
 */
public class PhoneLoginAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {

    protected final Log logger = LogFactory.getLog(getClass());

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    private PhoneLoginService phoneLoginService;

    public PhoneLoginAuthenticationProvider(PhoneLoginService phoneLoginService) {
        this.phoneLoginService = phoneLoginService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //校验验证码并从数据库中查询用户
        UserDetails userDetails = phoneLoginService.loadUserByPhone(authentication.getPrincipal().toString(), authentication.getCredentials().toString());
        if (!userDetails.isEnabled()) {
            throw new DisabledException(
                    this.messages.getMessage("AccountStatusUserDetailsChecker.disabled", "用户已冻结"));
        }
        return new PhoneLoginAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //这里记得换成我们的Token类
        return PhoneLoginAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public final void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messages, "A message source must be set");
        doAfterPropertiesSet();
    }

    protected void doAfterPropertiesSet() throws Exception {
    }

}
