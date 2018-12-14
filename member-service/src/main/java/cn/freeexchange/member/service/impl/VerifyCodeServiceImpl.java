package cn.freeexchange.member.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import cn.freeexchange.member.api.enums.IdentityTypeEnum;
import cn.freeexchange.member.domain.VerifyCode;
import cn.freeexchange.member.domain.rpt.VerifyCodeRpt;
import cn.freeexchange.member.service.VerifyCodeService;

@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {
	
	@Autowired
	private VerifyCodeRpt verifyCodeRpt;
	
	
	@SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
	
	
	private static final String REDIS_PREFIX = "GW_PAR_";
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String sendVerifyCode(String mobileNo, String bizCode, Long partner) {
		
		//send sms
		Map<String,String> config = redisTemplate.opsForHash().entries(REDIS_PREFIX+partner);
		String channel = config.get(bizCode+"."+"verifycode.channel");
		String vcMode = config.get(bizCode+"."+"verifycode.mode");
		String vcLength = config.get(bizCode+"."+"verifycode.length");
		String vcTemplate = config.get(bizCode+"."+"verifycode.template");
		if(StringUtils.isBlank(channel) || StringUtils.isBlank(vcMode) 
				|| StringUtils.isBlank(vcLength) || StringUtils.isBlank(vcTemplate)) {
			throw new RuntimeException("商户["+bizCode+"]业务,短信验证码规则配置有误.");
		}
		if(!channel.equalsIgnoreCase("MOCK")) {
			throw new RuntimeException("信息通道未接入,仅支持模拟发送短信");
		}
		VerifyCode verifyCode = VerifyCode.makeVerifyCode(channel,vcMode, vcLength,vcTemplate,
				bizCode,mobileNo,IdentityTypeEnum.MOBILE.getCode());
		verifyCodeRpt.save(verifyCode);
		return verifyCode.getVerifyCodeId();
	}

	@Override
	public boolean validateVerifyCode(String verifyCodeId, String verfiyCodeValue) {
		if(StringUtils.isBlank(verifyCodeId) && StringUtils.isBlank(verfiyCodeValue)) {
			throw new RuntimeException("verifyCodeId,verfiyCodeValue均不能为空");
		}
		VerifyCode vc= verifyCodeRpt.getByVerifyCodeId(verifyCodeId);
		if(null == vc || null == vc.getVerifyCodeValue() || vc.isExpiry()) {
			return false;
		}
		return vc.getVerifyCodeValue().equalsIgnoreCase(verfiyCodeValue);
	}

}
