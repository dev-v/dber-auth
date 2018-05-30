package com.dber.auth.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dber.tool.config.ServerConfig;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter implements Filter {
  private ServerConfig.Session.Cookie cookieConfig;

  public TokenFilter(ServerConfig.Session.Cookie cookieConfig) {
    this.cookieConfig = cookieConfig;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    String path = ((HttpServletRequest) request).getServletPath();
    ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse) response);
//    ((HttpServletResponse) response);
    chain.doFilter(request, wrapper);
    String content = new String(wrapper.getContent(), "utf-8");
    JSONObject json = JSON.parseObject(content);
    TokenBody tokenBody = new TokenBody();
    tokenBody.setCode(200);
    tokenBody.setUserId(json.getLongValue("user_id"));
    tokenBody.setUserName(json.getString("user_name"));

    ((HttpServletResponse) response).addCookie(getCookie("Authorization", json.getString("access_token")));

    Cookie refreshCookie = getCookie("refresh_token",
            json.getString("refresh_token"));
    refreshCookie.setMaxAge(60 * 60 * 24);
    ((HttpServletResponse) response).addCookie(refreshCookie);

    response.getWriter().write(JSON.toJSONString(tokenBody));
  }

  private Cookie getCookie(String name, String value) {
    Cookie cookie = new Cookie(name, value);
    cookie.setMaxAge(cookieConfig.getMaxAge());
    cookie.setPath(cookieConfig.getPath());
    cookie.setHttpOnly(cookieConfig.isHttpOnly());
    cookie.setDomain(cookieConfig.getDomain());
    return cookie;
  }

  @Override
  public void destroy() {

  }
}
