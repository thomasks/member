package cn.freeexchange.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.freeexchange.common.base.exception.BusinessException;
import cn.freeexchange.member.domain.Identity;
import cn.freeexchange.member.domain.Member;
import cn.freeexchange.member.domain.rpt.IdentityRpt;
import cn.freeexchange.member.domain.rpt.MemberRpt;
import cn.freeexchange.member.dto.MemberDto;
import cn.freeexchange.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberRpt memberRpt;
	
	@Autowired
	private IdentityRpt identityRpt;
	
	@Override
	@Transactional
	public MemberDto createMemeber(Long partner, Long openId, String identityType, String identityValue) {
		Member member = Member.makeMember(partner, openId, identityType, identityValue);
		memberRpt.save(member);
		return member.makeDto();
	}


	@Override
	public MemberDto getMember(Long partner,String identityType, String identityValue) {
		Identity existsIdentity = identityRpt.findIdentity(partner, identityType, identityValue);
		if(null == existsIdentity) {
			throw new BusinessException("mbr01");
		}
		Long openId = existsIdentity.getOpenId();
		Member memeber = memberRpt.findMemeber(partner, openId);
		return memeber.makeDto();
	}


	@Override
	public MemberDto getMember(Long partner, Long openId) {
		Member memeber = memberRpt.findMemeber(partner, openId);
		if(null == memeber) {
			throw new BusinessException("mbr01");
		}
		return memeber.makeDto();
	}

}
