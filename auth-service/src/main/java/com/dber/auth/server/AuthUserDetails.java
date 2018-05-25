package com.dber.auth.server;

import com.dber.auth.api.entity.UserDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AuthUserDetails implements UserDetails {

  private UserDetail userDetail;

  public AuthUserDetails(UserDetail userDetail) {
    this.userDetail = userDetail;
  }

  public long getUserId() {
    return userDetail.getId();
  }

  public int getSystem() {
    return userDetail.getSystem();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return userDetail.getPassword();
  }

  @Override
  public String getUsername() {
    return userDetail.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return userDetail.getStatus() != 3;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return userDetail.getStatus() == 1;
  }
}
