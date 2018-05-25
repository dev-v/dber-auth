package com.dber.auth.verify.mobile;

import com.dber.auth.verify.Constant;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

public class MobileTokenGranter extends AbstractTokenGranter {
  private AuthenticationManager authenticationManager;

  public MobileTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
    super(tokenServices, clientDetailsService, requestFactory, "mobile");
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
    Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());

    String mobile = parameters.get(Constant.GrantType.Mobile.mobileField);
    String validCodeKey = parameters.get(Constant.GrantType.Mobile.validCodeKeyField);
    String validCode = parameters.get(Constant.GrantType.Mobile.validCodeField);

    Authentication mobileAuth = new MobileAuthenticationToken(new MobileVo(mobile, validCode, validCodeKey));
    ((AbstractAuthenticationToken) mobileAuth).setDetails(parameters);

    try {
      mobileAuth = authenticationManager.authenticate(mobileAuth);
    } catch (AccountStatusException ase) {
      throw new InvalidGrantException(ase.getMessage());
    } catch (BadCredentialsException e) {
      throw new InvalidGrantException(e.getMessage());
    }

    if (mobileAuth == null || !mobileAuth.isAuthenticated()) {
      throw new InvalidGrantException("Could not authenticate mobile: " + mobile);
    }

    OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
    return new OAuth2Authentication(storedOAuth2Request, mobileAuth);
  }
}
