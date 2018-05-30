package com.dber.auth.grant.mobile;

import com.dber.auth.server.AuthUserDetails;
import com.dber.auth.service.UserDetailService;
import com.dber.auth.grant.VerifyCodeUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class MobileAuthenticationProvider implements AuthenticationProvider {

  private UserDetailService userDetailService;

  public MobileAuthenticationProvider(UserDetailService userDetailService) {
    this.userDetailService = userDetailService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;
    MobileVo mobileVo = (MobileVo) mobileAuthenticationToken.getPrincipal();

    VerifyCodeUtil.validCode(mobileVo.getVerifyCodeKey(), mobileVo.getVerifyCode());

    AuthUserDetails userDetail = userDetailService.loadUserByMobile(mobileVo.getMobile());
    if (userDetail == null) {
      // 进行手机号用户注册管理
    }
    MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetail, null);
    authenticationToken.setDetails(authentication.getDetails());
    return authenticationToken;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return true;
//    return MobileAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
