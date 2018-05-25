package com.dber.auth.api.entity;

import lombok.Data;

/**
 * <li>文件名称: UserDetail.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月20日
 * @author dev-v
 */
@Data
public class UserDetail{
	
	/**
	 * 
	 */
	private Long id;

	/**
	 * 本平台昵称
	 */
	private String username;

	/**
	 * 账号或手机登录密码
	 */
	private String password;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 
	 */
	private String email;

	/**
	 * 本账户被绑定的账户id，被绑定后，本账户数据将迁移到被绑定账户上，并且实现本账户
	 */
	private Long bindId;

	/**
	 * 账户状态：
1-启用
2-绑定失效（被认证的其他账户绑定，数据已被迁移到其他账号上）
3-登录锁定
	 */
	private Integer status;

	/**
	 * 所属平台，与数据字典保持一致
	 */
	private Integer system;

	/**
	 * 
	 */
	private java.sql.Date createTime;

	/**
	 * 
	 */
	private java.sql.Timestamp modifyTime;


}
