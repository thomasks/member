package cn.freeexchange.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.freeexchange.common.base.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_verify_code", schema = "member")
@Setter
@Getter
public class VerifyCode {
	
	
	//主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//验证码编号
	@Column(name = "verify_code_id")
	private String verifyCodeId;
	
	//验证码值
	@Column(name = "verify_code_value")
	private String verifyCodeValue;
	
	
	@Column(name = "verify_template_code")
	private String verifyTemplateCode;
	
	
	@Column(name = "biz_code")
	private String bizCode;
	
	@Column(name = "contact")
	private String contact;
	
	@Column(name = "send_type")
	private String sendType;
	
	//验证码有效期
	@Column(name = "duration")
	private Long duration;
	
	//创建时间
	@Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date createTime = new Date();
		
	//更新时间
	@Column(name = "update_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date updateTime = new Date();
	
	
	@Column(name = "logic_delete")
    private Boolean logicDelete = false;
	
	//VerifyCode
	protected VerifyCode() {
		
	}
	
	public static VerifyCode makeVerifyCode(String channel,String vcMode,String vcLength,String verifyTemplateCode,
			String bizCode,String contact,String sendType) {
		VerifyCode vc = new VerifyCode();
		String verifyCodeValue = generateVerifyCode(channel,vcMode,vcLength);
		vc.setVerifyCodeValue(verifyCodeValue);
		vc.setVerifyCodeId(""+System.currentTimeMillis());
		vc.setVerifyTemplateCode(verifyTemplateCode);
		vc.setSendType(sendType);
		vc.setDuration(180000L);
		vc.setContact(contact);
		vc.setBizCode(bizCode);
		return vc;
	}
	
	private static String generateVerifyCode(String channel,String vcMode,String vcLength) {
		if(channel.equalsIgnoreCase("MOCK")) {
			return "666666";
		}
		if(vcMode.equalsIgnoreCase("NUM")) {
			return StringUtils.getRandNum(Integer.parseInt(vcLength));
		}
		throw new RuntimeException("unsupported vc mode.");
	}

	
	public boolean isExpiry() {
		return System.currentTimeMillis() - duration > Long.parseLong(verifyCodeId);
	}
}
