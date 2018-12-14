package cn.freeexchange.member.listener;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.freeexchange.common.base.event.Event;
import cn.freeexchange.member.api.constants.MemeberConstants;
import cn.freeexchange.member.api.enums.UserEventTypeEnum;
import cn.freeexchange.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Component
@RabbitListener(queues = "USER_EVENT_SOURCING")
@Slf4j
public class UserEventListener {
	
	@Autowired
	private MemberService memberService;
	
	@RabbitHandler
	@Transactional
	@SuppressWarnings("rawtypes")
	public void process(String dataStr) throws Exception {
		log.info("@@UserEventListener capture msg:{}",dataStr);
		Event event = JSON.parseObject(dataStr, Event.class);
		String eventType = event.getEventType();
		if(eventType.equalsIgnoreCase(UserEventTypeEnum.USER_SIGNUP.getCode())) {
			Map content = event.getContent();
			Long partner = (Long) content.get(MemeberConstants.PARTNER);
			Long openId = (Long) content.get(MemeberConstants.OPEN_ID);
			String identityType = (String) content.get(MemeberConstants.IDENTITY_TYPE);
			String identityValue = (String) content.get(MemeberConstants.IDENTITY_VALUE);
			memberService.createMemeber(partner, openId, identityType, identityValue);
		}
	}
}
