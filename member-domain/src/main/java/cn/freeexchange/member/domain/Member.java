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

import cn.freeexchange.member.dto.MemberDto;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "tb_member", schema = "member")
@Getter
@Setter
public class Member {
	
	//会员ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//合作商户标识
	@Column(name = "partner")
	private Long partner;
	
	//统一用户标识
	@Column(name = "open_id")
	private Long openId;
	
	@Column(name = "nick_name")
	private String nickName;
	
	@Column(name = "logic_delete")
    private Boolean logicDelete = false;
	
	//创建时间
	@Column(name = "create_time", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime = new Date();
		
	//更新时间
	@Column(name = "update_time", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime = new Date();
	
	@Column(name = "img_url")
	private String imgUrl;
	
	protected Member() {
		
	}
	
	public Member(Long partner,Long openId,String identityType,String identityValue) {
		this.partner = partner;
		this.openId = openId;
		this.nickName = identityValue;
		this.imgUrl = "https://www.freeexchange.cn/assets/avatar.jpeg";
	}
	
	public MemberDto makeDto() {
		return new MemberDto(partner,openId, nickName, imgUrl);
	}
	
	
	public static Member makeMember(Long partner,Long openId,String identityType,String identityValue) {
		return new Member(partner, openId, identityType, identityValue);
	}
	
	
	
}
