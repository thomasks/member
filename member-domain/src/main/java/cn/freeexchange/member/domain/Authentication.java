package cn.freeexchange.member.domain;

import lombok.Getter;
import lombok.Setter;

//认证
@Getter
@Setter
public class Authentication {
	
	//opneId
	private String openId;
	
	
	//MOBILE,EMAIL
	private String identityType;
			
	//18XXXX,wangwee163@163.com
	private String identityValue;
	
	//认证方式
	private String authType;
	
	//认证值
	private String authValue;
	
	
	
}
