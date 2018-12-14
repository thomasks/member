package cn.freeexchange.member.service.impl;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.freeexchange.common.base.did.SnowflakeIdWorker;
import cn.freeexchange.common.base.exception.BusinessException;
import cn.freeexchange.member.domain.Identity;
import cn.freeexchange.member.domain.Meeting;
import cn.freeexchange.member.domain.User;
import cn.freeexchange.member.domain.rpt.IdentityRpt;
import cn.freeexchange.member.domain.rpt.MeetingRpt;
import cn.freeexchange.member.domain.rpt.UserRpt;
import cn.freeexchange.member.dto.MemberDto;
import cn.freeexchange.member.service.MemberService;
import cn.freeexchange.member.service.SignupService;

@Service
public class SignupServiceImpl implements SignupService {
	
	
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	
	@Autowired
	private UserRpt userRpt;
	
	@Autowired
	private IdentityRpt identityRpt;
	
	@Autowired
	private MeetingRpt meetingRpt;
	
	@Autowired
	private MemberService memberService;
	
	@Override
	@Transactional
	public MemberDto signup(Long partner, String identityType, String identityValue,Map<String,String> extraParam) {
		Identity existsIdentity = identityRpt.findIdentity(partner, identityType, identityValue);
		if(null != existsIdentity) {
			throw new BusinessException("mbr00");
		}
		long nextId = snowflakeIdWorker.nextId();
		User user = new User(nextId);
		userRpt.save(user);
		Identity identity = new Identity(partner, nextId, identityType, identityValue);
		identityRpt.save(identity);
		String meetingType = extraParam.get("meetingType");
		String rendezvousType =  extraParam.get("rendezvousType");
		String rendezvousValue = extraParam.get("rendezvousValue");
		Meeting meeting = new Meeting(nextId, partner, meetingType, rendezvousType, rendezvousValue);
		meetingRpt.save(meeting);
		MemberDto memberDto = memberService.createMemeber(partner, nextId, identityType, identityValue);
		
		//send user sign up event
		/*Map<String,String> content = new HashMap<>();
		Event event = new Event(UserEventTypeEnum.USER_SIGNUP.getCode(), content);
		rabbitTemplate.convertAndSend(EventSourcingEnum.USER_EVENT_SOURCING.getCode() ,JSON.toJSONString(event));*/
		return memberDto;
	}
	

}
