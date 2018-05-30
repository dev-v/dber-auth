package com.dber.auth.config;

import com.dber.auth.response.TokenFilter;
import com.dber.auth.resource.config.JwtAuthenticationResourceConfig;
import com.dber.auth.server.DberTokenEnhancer;
import com.dber.auth.service.UserDetailService;
import com.dber.auth.grant.mobile.MobileTokenGranter;
import com.dber.tool.config.ServerConfig;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@Import({JwtTokenConfig.class, AuthServiceConfig.class, JwtAuthenticationResourceConfig.class})
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private ServerConfig serverConfig;

  @Autowired
  private DataSource dataSource;

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailService userDetailService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtAccessTokenConverter accessTokenConverter;

  @Bean
  public TokenEnhancer tokenEnhancer() {
    return new DberTokenEnhancer();
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients)
          throws Exception {
    clients.jdbc(dataSource);
  }

  @Override
  public void configure(
          AuthorizationServerEndpointsConfigurer endpoints) {
    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter));
    endpoints
            .tokenStore(tokenStore)
            .tokenEnhancer(tokenEnhancerChain)
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailService).tokenServices(null)
            .tokenGranter(buildTokenGranter(
                    endpoints.getTokenServices(),
                    endpoints.getClientDetailsService(),
                    endpoints.getOAuth2RequestFactory()));
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security.passwordEncoder(passwordEncoder);
  }


  private TokenGranter buildTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
    List<TokenGranter> tokenGranters = new ArrayList<>();
    tokenGranters.add(new MobileTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
    tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory));
    CompositeTokenGranter granter = new CompositeTokenGranter(tokenGranters);
    return granter;
  }

  @Bean
  public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new TokenFilter(serverConfig.getSession().getCookie()));
    filterRegistrationBean.addUrlPatterns("/oauth/token");
    return filterRegistrationBean;
  }

  @Bean
  public EmbeddedServletContainerCustomizer customizer() {
    return container -> {
      if (container instanceof TomcatEmbeddedServletContainerFactory) {
        TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
        tomcat.addContextCustomizers(context -> context.setCookieProcessor(new LegacyCookieProcessor()));
      }
    };
  }
}
