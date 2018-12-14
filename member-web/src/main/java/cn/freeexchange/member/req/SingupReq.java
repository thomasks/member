package cn.freeexchange.member.req;

import cn.freeexchange.member.api.enums.IdentityTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//校验手机号是否合法
//校验验证码是否正确
public class SingupReq {
	
	
	private String identityType = IdentityTypeEnum.MOBILE.getCode();
	
	
	//手机号
	private String identityValue;
	
	
	//验证码
	private String verifyCodeValue;
	
	//验证码编号
	private String verifyCodeId;
	
	
	
}
