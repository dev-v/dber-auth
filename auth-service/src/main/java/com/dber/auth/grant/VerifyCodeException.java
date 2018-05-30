package com.dber.auth.grant;

import org.springframework.security.core.AuthenticationException;

public class VerifyCodeException extends AuthenticationException {
  public VerifyCodeException() {
    super("验证码输入错误！");
  }
}
