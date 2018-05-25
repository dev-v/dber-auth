package com.dber.auth.verify.mobile;

import com.dber.auth.api.entity.UserDetail;
import com.dber.auth.server.AuthUserDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MobileAuthenticationToken extends AbstractAuthenticationToken {
  private Object principal;

  public MobileAuthenticationToken(MobileVo mobile) {
    super(null);
    this.principal = mobile;
  }

  public MobileAuthenticationToken(AuthUserDetails userDetail, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = userDetail;
    this.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return this.principal;
  }
}
