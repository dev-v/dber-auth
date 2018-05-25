package com.dber.auth.api.entity;

import lombok.Data;

/**
 * <li>文件名称: UserThirdBind.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月20日
 * @author dev-v
 */
@Data
public class UserThirdBind {
	
	/**
	 * 
	 */
	private Long id;

	/**
	 * 三方系统openid
	 */
	private String openId;

	/**
	 * 
	 */
	private Long userDetailId;

	/**
	 * 三方账号平台类型：1-微信，2-QQ，3-支付宝，4-新浪微薄
	 */
	private Integer type;

	/**
	 * 
	 */
	private java.sql.Date createTime;


}
