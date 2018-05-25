package com.dber.auth.api;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableFeignClients(basePackages = "com.dber.auth.api")
@ConditionalOnExpression("#{!environment['spring.application.name'].endsWith('" + AuthApiConfig.SERVICE_NAME + "')}")
public class AuthApiConfig {
  public static final String SERVICE_NAME = "auth-service";
  public static final String PLACE_HOLD_SERVICE_NAME = "${dber.service." + SERVICE_NAME + ".name:" + SERVICE_NAME + "}";
}
