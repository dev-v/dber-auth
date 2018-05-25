package com.dber.auth.service;

import com.dber.auth.server.AuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dber.platform.mapper.IMapper;
import com.dber.platform.service.AbstractService;
import com.dber.auth.api.entity.UserDetail;
import com.dber.auth.mapper.UserDetailMapper;

/**
 * <li>文件名称: UserDetailService.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月20日
 */
@Service
public class UserDetailServiceImpl extends AbstractService<UserDetail> implements UserDetailService {

  @Autowired
  private UserDetailMapper mapper;

  @Override
  protected IMapper<UserDetail> getMapper() {
    return this.mapper;
  }

  @Override
  public AuthUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDetail userDetail = new UserDetail();
    userDetail.setUsername(username);
    userDetail = mapper.getByCondition(userDetail);
    if (userDetail == null) {
      throw new UsernameNotFoundException("用户：" + username + "未找到！");
    }
    return new AuthUserDetails(userDetail);
  }

  @Override
  public AuthUserDetails loadUserByMobile(String mobile) {
    UserDetail userDetail = new UserDetail();
    userDetail.setMobile(mobile);
    userDetail.setUsername("aaaa");
    userDetail.setId(111L);
    userDetail.setSystem(1);
    return new AuthUserDetails(userDetail);
  }
}
