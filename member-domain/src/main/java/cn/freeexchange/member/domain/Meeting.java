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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//约会
@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_meeting", schema = "member")
public class Meeting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name = "open_id")
	private Long openId;
	
	@Column(name = "partner")
	private Long partner;
	
	
	//地点类型
	@Column(name = "rendezvous_type")
	private String rendezvousType;
	
	//地点值
	@Column(name = "rendezvous_value")
	private String rendezvousValue;
	
	//约会时间
	@Column(name = "meeting_time")
	private Date meetingTime;
	
	//约会类型: 注册
	@Column(name = "meeting_type")
	private String meetingType;
	
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
	
	//Meeting
	protected Meeting() {
		
	}

	
	//meetingType DEMO
	//rendezvousType REGISTRATION
	//rendezvousValue DEMO
	public Meeting(Long openId,Long partner,String meetingType,String rendezvousType,String rendezvousValue) {
		this.meetingType = meetingType;
		this.rendezvousType = rendezvousType;
		this.rendezvousValue = rendezvousValue;
		this.openId = openId;
		this.partner = partner;
		this.meetingTime = new Date();
	}
	
}
