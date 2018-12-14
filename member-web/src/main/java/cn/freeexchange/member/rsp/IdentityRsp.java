package cn.freeexchange.member.rsp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IdentityRsp {
	
	//开放ID
	private String openId;
	
	private String token;
	
	protected IdentityRsp(String openId,String token) {
		this.openId = openId;
		this.token = token;
	}
	
	public static IdentityRsp makeRegistrationRsp(String openId,String token) {
		return new IdentityRsp(openId,token);
	}
	
	
}
