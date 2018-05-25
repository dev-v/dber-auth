package com.dber.auth.resource;

import com.dber.auth.api.entity.UserDetail;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserDetailAuthenticationToken extends AbstractAuthenticationToken {

  private UserDetail userDetail;

  private Object credentials;

  public UserDetailAuthenticationToken(UserDetail userDetail) {
    super(null);
    this.userDetail = userDetail;
    this.setAuthenticated(true);
  }


  @Override
  public Object getCredentials() {
    return credentials;
  }

  @Override
  public Object getPrincipal() {
    return userDetail;
  }
}
