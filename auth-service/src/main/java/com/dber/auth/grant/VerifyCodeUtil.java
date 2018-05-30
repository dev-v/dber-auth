package com.dber.auth.grant;

import com.dber.tool.util.ExpireMap;

import java.util.Random;

public class VerifyCodeUtil {
  private static ExpireMap<String, String> verifyCodeMap = new ExpireMap<>(32768, 600);

  private static Random random = new Random();

  public static final void validCode(String verifyCodeKey, String verifyCode) {
    String cacheKey = verifyCodeMap.get(verifyCodeKey);
    if (cacheKey == null || !cacheKey.equals(verifyCode)) {
//      throw new VerifyCodeException();
    }
  }

  public static final String generateVerifyCodeKey(String verifyCode) {
    String key;
    do {
      key = String.valueOf(random.nextInt());
    } while (verifyCodeMap.get(key) != null);
    verifyCodeMap.put(key, verifyCode);
    return key;
  }
}
