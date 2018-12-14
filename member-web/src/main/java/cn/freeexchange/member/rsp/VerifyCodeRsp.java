package cn.freeexchange.member.rsp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VerifyCodeRsp {
	
	private String verifyCodeId;
	
	protected VerifyCodeRsp() {
		
	}
	
	public  VerifyCodeRsp(String verifyCodeId) {
		this.verifyCodeId = verifyCodeId;
	}
	
	
	public static VerifyCodeRsp makeVerifyCodeRsp(String verifyCodeId) {
		return new VerifyCodeRsp(verifyCodeId);
	}

}
