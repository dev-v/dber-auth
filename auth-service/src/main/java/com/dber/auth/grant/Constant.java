package com.dber.auth.grant;

public interface Constant {
  interface GrantType {
    interface Mobile extends VerifyCode {
      String mobileField = "mobile";
      String path = "/oauth/mobile";
      String validCodePath = path + "/code";
    }

    interface VerifyCode {
      String validCodeField = "validCode";
      String validCodeKeyField = "validCodeKey";
    }
  }
}
