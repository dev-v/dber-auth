package com.dber.auth.resource.jwt;

import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieBearerTokenExtractor extends BearerTokenExtractor {

  @Override
  protected String extractToken(HttpServletRequest request) {
    for (Cookie cookie : request.getCookies()) {
      if (cookie.getName().equals("Authorization")) {
        return cookie.getValue();
      }
    }
    return null;
  }
}
