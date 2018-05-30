package com.dber.auth.server;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class DberTokenEnhancer implements TokenEnhancer {
  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    AuthUserDetails authUserDetails = (AuthUserDetails) authentication.getPrincipal();
    Map<String, Object> additionalInfo = new HashMap<>();
    additionalInfo.put("user_id", authUserDetails.getUserId());
    additionalInfo.put("user_name", authUserDetails.getUsername());
    additionalInfo.put("system", authUserDetails.getSystem());
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
    return accessToken;
  }
}
