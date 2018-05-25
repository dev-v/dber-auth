package com.dber.auth.resource.jwt;

import com.dber.auth.api.entity.UserDetail;
import com.dber.auth.resource.UserDetailAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.Map;

public class JwtUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

  @Override
  public Authentication extractAuthentication(Map<String, ?> map) {
    UserDetail userDetail = new UserDetail();
    userDetail.setId(Long.parseLong(map.get("user_id").toString()));
    userDetail.setUsername(map.get(USERNAME).toString());
    return new UserDetailAuthenticationToken(userDetail);
  }
}
