package cn.freeexchange.member.service;

import java.util.Map;

import cn.freeexchange.member.dto.MemberDto;

public interface SignupService {
	
	//partner 商户号,identityType 注册类型,identityValue 注册账号
	public MemberDto signup(Long partner,String identityType,String identityValue,Map<String,String> extraParam);

}
