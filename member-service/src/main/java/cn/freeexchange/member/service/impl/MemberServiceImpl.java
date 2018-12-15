package cn.freeexchange.member.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.freeexchange.account.api.AccountEventTypeEnum;
import cn.freeexchange.common.base.Constants;
import cn.freeexchange.common.base.event.Event;
import cn.freeexchange.common.base.event.EventSourcingEnum;
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
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Override
	@Transactional
	public MemberDto createMemeber(Long partner, Long openId, String identityType, String identityValue) {
		Member member = Member.makeMember(partner, openId, identityType, identityValue);
		memberRpt.save(member);
		//send user sign up event
		Map<String,Object> content = new HashMap<>();
		content.put(Constants.PARTNER, partner);
		content.put(Constants.OPEN_ID, openId);
		Event event = new Event(AccountEventTypeEnum.OPEN_ACCOUNT.getCode(), content);
		rabbitTemplate.convertAndSend(EventSourcingEnum.ACCOUNT_EVENT_SOURCING.getCode() ,JSON.toJSONString(event));
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
