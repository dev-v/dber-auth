package com.dber.auth.resource.config;

import com.dber.auth.resource.jwt.JwtUserAuthenticationConverter;
import com.dber.tool.config.SecurityConfig;
import com.dber.tool.encryp.RSAKeyPairUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties({SecurityConfig.class})
@EnableResourceServer
public class JwtAuthenticationResourceConfig extends ResourceServerConfigurerAdapter {

  @Autowired
  private SecurityConfig securityConfig;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()//默认允许访问
            .authorizeRequests()
            .antMatchers("/oauth/token").permitAll()
            .anyRequest().authenticated();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//    converter
    DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
    tokenConverter.setUserTokenConverter(new JwtUserAuthenticationConverter());

    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setVerifier(new RsaVerifier((RSAPublicKey) RSAKeyPairUtil.generatePublicKey(securityConfig.getOauth2().getResource().getJwt().getKeyValue())));
    converter.setAccessTokenConverter(tokenConverter);

//    tokenStore
    TokenStore tokenStore = new JwtTokenStore(converter);

    DefaultTokenServices tokenServices = new DefaultTokenServices();
    tokenServices.setTokenStore(tokenStore);

    resources.tokenServices(tokenServices);
  }
}
