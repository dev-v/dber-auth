package com.dber.auth.service;

import com.dber.auth.server.AuthUserDetails;
import com.dber.platform.service.IService;
import com.dber.auth.api.entity.UserDetail;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <li>文件名称: IUserDetailService.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月20日
 */
public interface UserDetailService extends IService<UserDetail>, UserDetailsService {
  AuthUserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

  AuthUserDetails loadUserByMobile(String mobile);
}
