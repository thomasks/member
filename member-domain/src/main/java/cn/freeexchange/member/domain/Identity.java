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

import org.apache.commons.lang3.time.DateUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_identity", schema = "member")
//标识
public class Identity {
	
	//主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "partner")
	private Long partner;
	
	//统一会员标识
	@Column(name = "open_id")
	private Long openId;
	
	//MOBILE,EMAIL
	@Column(name = "identity_type")
	private String identityType;
	
	//18XXXX,wangwee163@163.com
	@Column(name = "identity_value")
	private String identityValue;
	
	//有效期起始时间
	@Column(name = "validate_begin_date")
	private Date validateBeginDate;
	
	//有效期结束时间
	@Column(name = "validate_end_date")
	private Date validateEndDate;
	
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
	
	protected Identity() {
		
	}
	
	public Identity(Long partner,Long openId,String identityType,String identityValue) {
		this.partner = partner;
		this.openId = openId;
		this.identityType = identityType;
		this.identityValue = identityValue;
		this.validateBeginDate = new Date();
		this.validateEndDate = DateUtils.addYears(this.validateBeginDate, 20);
	}
	
		
	public boolean isInvalidate() {
		Date now = new Date();
		return this.validateBeginDate.after(now) || this.validateEndDate.before(now);
	}
	
}
