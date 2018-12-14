package cn.freeexchange.member.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VerifyCodeRequest {
	
	
	private String mobileNo;
	private String bizCode;
	

}
