package com.dber.auth.config;

import com.dber.auth.service.UserDetailService;
import com.dber.auth.verify.mobile.MobileAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

@Import({AuthServiceConfig.class})
@Configuration
public class AuthenticationProviderManagerConfig extends GlobalAuthenticationConfigurerAdapter {
  @Autowired
  private UserDetailService userDetailService;

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(new MobileAuthenticationProvider(userDetailService));
    super.configure(auth);
  }
}
