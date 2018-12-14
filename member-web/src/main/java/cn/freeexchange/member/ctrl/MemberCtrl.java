package cn.freeexchange.member.ctrl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.freeexchange.common.base.ApiResponse;
import cn.freeexchange.common.base.exception.BusinessException;
import cn.freeexchange.common.base.identity.IdentityDto;
import cn.freeexchange.common.base.req.RequestDTO;
import cn.freeexchange.common.base.service.TokenService;
import cn.freeexchange.member.api.enums.IdentityTypeEnum;
import cn.freeexchange.member.dto.MemberDto;
import cn.freeexchange.member.req.SinginReq;
import cn.freeexchange.member.req.SingupReq;
import cn.freeexchange.member.req.VerifyCodeRequest;
import cn.freeexchange.member.rsp.IdentityRsp;
import cn.freeexchange.member.rsp.VerifyCodeRsp;
import cn.freeexchange.member.service.MemberService;
import cn.freeexchange.member.service.SignupService;
import cn.freeexchange.member.service.VerifyCodeService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/member/api")
@Slf4j
public class MemberCtrl {
	
	
	@Autowired
	private VerifyCodeService verifyCodeService;
	
	@Autowired
	private SignupService signupService;
	
	@Autowired
	private MemberService memberService;
	
	@Value("${user.token.exp}")
    private String tokenExpSeconds="604800";
	
	@Autowired
	private TokenService tokenService;

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sendVerifyCode", method = {RequestMethod.POST,RequestMethod.GET})
	public ApiResponse<VerifyCodeRsp> sendVerifyCode(HttpServletRequest request,@RequestBody VerifyCodeRequest vcr) {
		log.info("@@registration arrived.VerifyCodeRequest is {}.",vcr);
		String mobileNo = vcr.getMobileNo();
		String bizCode = vcr.getBizCode();
		String partner = request.getHeader(RequestDTO.HEADER_PARTNER_ID);
		if(StringUtils.isBlank(partner)) {
			return ApiResponse.failure("合作商户号不能为空");
		}
		if(StringUtils.isBlank(mobileNo)) {
			return ApiResponse.failure("手机号不能为空");
		}
		if(StringUtils.isBlank(bizCode)) {
			return ApiResponse.failure("业务码不能为空");
		}
		boolean validPhone = cn.freeexchange.common.base.utils.StringUtils.validPhone(mobileNo);
		if(!validPhone) {
			return ApiResponse.failure("请输入正确的手机号");
		}
		try {
			String verifyCodeId = verifyCodeService.sendVerifyCode(mobileNo, bizCode, Long.parseLong(partner));
			VerifyCodeRsp verifyCodeRsp = VerifyCodeRsp.makeVerifyCodeRsp(verifyCodeId);
			return ApiResponse.success(verifyCodeRsp);
		} catch (BusinessException e) {
			return ApiResponse.failure(e.getBusinessCode(), e.getMessage());
		} catch (Throwable t) {
			return ApiResponse.failure(ApiResponse.CODE_ERROR,ApiResponse.MSG_ERROR);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public @ResponseBody ApiResponse<IdentityRsp> signup(HttpServletRequest request,@RequestBody SingupReq singupReq) throws Exception {
		log.info("@@registration arrived.SingupReq is {}.",singupReq);
		String partner = request.getHeader(RequestDTO.HEADER_PARTNER_ID);
		if(StringUtils.isBlank(partner)) {
			return ApiResponse.failure("合作商户号不能为空");
		}
		
		String identityType = singupReq.getIdentityType();
		String identityValue = singupReq.getIdentityValue();
		String verifyCodeId = singupReq.getVerifyCodeId();
		String verifyCodeValue = singupReq.getVerifyCodeValue();
		
		if(StringUtils.isBlank(verifyCodeId) && StringUtils.isBlank(verifyCodeValue)) {
			return ApiResponse.failure("验证码不能为空");
		}
		
		boolean validateVerifyCode = verifyCodeService.validateVerifyCode(verifyCodeId, verifyCodeValue);
		if(!validateVerifyCode) {
			return ApiResponse.failure("验证码不正确.");
		}
		if(StringUtils.isBlank(identityValue)) {
			return ApiResponse.failure("用户标识不能为空");
		}
		if(StringUtils.isBlank(identityType)) {
			identityType = IdentityTypeEnum.MOBILE.getName();
		}
		Map<String,String> extraParams = new HashMap<>();
		extraParams.put("meetingType", "SIGNUP");
		extraParams.put("rendezvousType", "WEB");
		extraParams.put("rendezvousValue", "DEMO");
		try {
			MemberDto memberDto = signupService.signup(Long.parseLong(partner), identityType, identityValue,extraParams);
			
			Long expSeconds = Long.parseLong(this.tokenExpSeconds);
			
			String token = tokenService.makeToken(memberDto.getPartner(), memberDto.getOpenId(), expSeconds);
			
			IdentityRsp identityRsp = IdentityRsp.makeRegistrationRsp(memberDto.getOpenId()+"",token);
			return ApiResponse.success(identityRsp);
		} catch (BusinessException e) {
			return ApiResponse.failure(e.getBusinessCode(), e.getMessage());
		} catch (Throwable t) {
			return ApiResponse.failure(ApiResponse.CODE_ERROR,ApiResponse.MSG_ERROR);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody ApiResponse<IdentityRsp> login(HttpServletRequest request,@RequestBody SinginReq singinReq,@RequestHeader(value="token",defaultValue="") String token) throws Exception {
		log.info("@@registration arrived.SingupReq is {}.",singinReq);
		String partner = request.getHeader(RequestDTO.HEADER_PARTNER_ID);
		if(StringUtils.isBlank(partner)) {
			return ApiResponse.failure("合作商户号不能为空");
		}
		
		if(StringUtils.isNotBlank(token)) {
			IdentityDto identityDto = tokenService.getTokenIdentity(token);
			IdentityRsp identityRsp = IdentityRsp.makeRegistrationRsp(identityDto.getOpenId()+"",token);
			return ApiResponse.success(identityRsp);
		}
		
		String identityType = singinReq.getIdentityType();
		String identityValue = singinReq.getIdentityValue();
		String verifyCodeId = singinReq.getVerifyCodeId();
		String verifyCodeValue = singinReq.getVerifyCodeValue();
		
		if(StringUtils.isBlank(verifyCodeId) && StringUtils.isBlank(verifyCodeValue)) {
			return ApiResponse.failure("验证码不能为空");
		}
		
		boolean validateVerifyCode = verifyCodeService.validateVerifyCode(verifyCodeId, verifyCodeValue);
		if(!validateVerifyCode) {
			return ApiResponse.failure("验证码不正确.");
		}
		if(StringUtils.isBlank(identityValue)) {
			return ApiResponse.failure("用户标识不能为空");
		}
		if(StringUtils.isBlank(identityType)) {
			identityType = IdentityTypeEnum.MOBILE.getName();
		}
		try {
			MemberDto memberDto = memberService.getMember(Long.parseLong(partner), identityType, identityValue);
			Long expSeconds = Long.parseLong(this.tokenExpSeconds);
			String rtoken = tokenService.makeToken(Long.parseLong(partner), memberDto.getOpenId(), expSeconds);
			IdentityRsp identityRsp = IdentityRsp.makeRegistrationRsp(memberDto.getOpenId()+"",rtoken);
			return ApiResponse.success(identityRsp);
		} catch (BusinessException e) {
			return ApiResponse.failure(e.getBusinessCode(), e.getMessage());
		} catch (Throwable t) {
			return ApiResponse.failure(ApiResponse.CODE_ERROR,ApiResponse.MSG_ERROR);
		}
	}
	
	@RequestMapping(value = "/logout", method = {RequestMethod.POST,RequestMethod.GET})
	public ApiResponse logout(@RequestHeader(value="token",defaultValue="") String token) {
        if (StringUtils.isBlank(token)) return ApiResponse.success();
        tokenService.removeToken(token);
        return ApiResponse.success();
    }
	
	@RequestMapping(value = "/view", method = {RequestMethod.POST,RequestMethod.GET})
	public ApiResponse<MemberDto> view(@RequestHeader(value="token",defaultValue="") String token) {
		if(StringUtils.isBlank(token)) {
			return ApiResponse.failure("用户未登入");
		}
		try {
			IdentityDto identityDto = tokenService.getTokenIdentity(token);
			MemberDto memberDto = memberService.getMember(identityDto.getPartner(),
					identityDto.getOpenId());
			return ApiResponse.success(memberDto);
		} catch (BusinessException e) {
			return ApiResponse.failure(e.getBusinessCode(), e.getMessage());
		} catch (Throwable t) {
			return ApiResponse.failure(ApiResponse.CODE_ERROR,ApiResponse.MSG_ERROR);
		}
		
	}
	

	
	//---------------------------------------------------------------------
    

	

}
