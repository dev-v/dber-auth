package com.dber.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dber.platform.mapper.IMapper;
import com.dber.platform.service.AbstractService;
import com.dber.auth.api.entity.UserThirdBind;
import com.dber.auth.mapper.UserThirdBindMapper;

/**
 * <li>文件名称: UserThirdBindService.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月20日
 * @author dev-v
 */
@Service
public class UserThirdBindServiceImpl extends AbstractService<UserThirdBind> implements UserThirdBindService {

	@Autowired
	private UserThirdBindMapper mapper;

	@Override
	protected IMapper<UserThirdBind> getMapper() {
		return this.mapper;
	}

}
