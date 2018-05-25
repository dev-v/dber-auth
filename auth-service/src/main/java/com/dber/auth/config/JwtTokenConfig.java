package com.dber.auth.config;

import com.dber.auth.server.DberTokenEnhancer;
import com.dber.tool.config.SecurityConfig;
import com.dber.tool.encryp.RSAKeyPairUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;

@Configuration
@Import({AuthServiceConfig.class})
public class JwtTokenConfig {

  @Autowired
  private SecurityConfig securityConfig;

  @Bean
  public TokenStore tokenStore() throws InvalidKeySpecException {
    return new JwtTokenStore(accessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() throws InvalidKeySpecException {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    SecurityConfig.Oauth2.Resource.Jwt jwt = securityConfig.getOauth2().getResource().getJwt();
    RsaSigner signer = new RsaSigner((RSAPrivateKey) RSAKeyPairUtil.generatePrivateKey(jwt.getPrivateKey()));
    converter.setSigner(signer);
    return converter;
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices() throws InvalidKeySpecException {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    defaultTokenServices.setSupportRefreshToken(true);
    return defaultTokenServices;
  }

  @Bean
  public TokenEnhancer tokenEnhancer() {
    return new DberTokenEnhancer();
  }
}
