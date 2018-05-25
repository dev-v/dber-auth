package com.dber.auth.verify.mobile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MobileVo {
  private String mobile;
  private String verifyCode;
  private String verifyCodeKey;
}
