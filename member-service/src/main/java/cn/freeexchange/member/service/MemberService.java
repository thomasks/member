package cn.freeexchange.member.service;

import cn.freeexchange.member.dto.MemberDto;

public interface MemberService {
	
	public MemberDto createMemeber(Long partner,Long openId,String identityType,String identityValue);
	
	public MemberDto getMember(Long partner,String identityType,String identityValue);
	
	public MemberDto getMember(Long partner,Long openId);
	
	
	

}
