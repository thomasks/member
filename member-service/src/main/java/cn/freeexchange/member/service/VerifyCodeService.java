package cn.freeexchange.member.service;

public interface VerifyCodeService {
	
	//mobileNo: 139xxxxxxxx
	//bizCode:REGISTER,PAYMENT
	//partner:合作商户
	public String sendVerifyCode(String mobileNo,String bizCode,Long partner);
	
	
	public boolean validateVerifyCode(String verifyCodeId,String verfiyCode);
	
	
	
	
	

}
