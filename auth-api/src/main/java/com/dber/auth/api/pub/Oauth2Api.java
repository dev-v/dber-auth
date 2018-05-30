package com.dber.auth.api.pub;

import com.dber.auth.api.AuthApiConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@FeignClient(AuthApiConfig.PLACE_HOLD_SERVICE_NAME)
@RequestMapping("oauth")
public interface Oauth2Api {

  @PostMapping("token")
  String refreshToken(@RequestParam("grant_type") String grant_type, @RequestParam("refresh_token") String refreshToken);
}
