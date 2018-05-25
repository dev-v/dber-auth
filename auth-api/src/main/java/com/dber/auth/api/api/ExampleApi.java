package com.dber.auth.api.api;

import com.dber.auth.api.AuthApiConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@FeignClient(AuthApiConfig.PLACE_HOLD_SERVICE_NAME)
@RequestMapping("api/example")
public interface ExampleApi {

  @RequestMapping("{path}")
  String path(@PathVariable("path") String path);

  @RequestMapping("test")
  String test(Authentication authentication, HttpServletResponse response);
}
