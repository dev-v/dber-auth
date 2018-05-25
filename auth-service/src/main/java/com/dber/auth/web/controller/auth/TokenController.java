package com.dber.auth.web.controller.auth;

import org.apache.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

public class TokenController {
  public void postAccessToken(HttpServletRequest principal, HttpServletResponse response) throws HttpRequestMethodNotSupportedException {
  }
}
