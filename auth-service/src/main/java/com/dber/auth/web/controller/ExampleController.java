package com.dber.auth.web.controller;

import com.alibaba.fastjson.JSON;
import com.dber.auth.api.api.ExampleApi;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
public class ExampleController implements ExampleApi {

  @Override
  public String path(String path) {
    return "例子：" + path + JSON.toJSONString(SecurityContextHolder.getContext().getAuthentication().getCredentials());
  }

  @Override
  public String test(Authentication authentication, HttpServletResponse response) {
    Cookie cookie = new Cookie("aaa", "aaa");
    response.addCookie(cookie);

    return JSON.toJSONString(authentication.getPrincipal());
  }
}
