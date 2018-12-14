package cn.freeexchange.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

//意图:标识用户实体,并且说明其状态
//状态主要包含两类:
//用户是否是重复注册用户
//用户是否已激活
@Entity
@Table(name = "tb_user", schema = "member")
@Setter
@Getter
public class User {
	
	//业务用户统一标识
	@Id
	@GenericGenerator(name = "id",strategy = "assigned")
	@GeneratedValue(generator = "id")
	@Column(name = "open_id", nullable = false)
	private Long openId;
	
	
	//关联主账户
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "master_open_id")
	private User master;
	
	//创建时间
	@Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date createTime = new Date();
	
	//更新时间
	@Column(name = "update_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date updateTime = new Date();
	
	//是否激活用户
	@Column(name = "activation")
	private boolean activation;
	
	@Column(name = "logic_delete")
    private Boolean logicDelete = false;
	
	//框架使用的构造函数
	protected User() {
		
	}
	
	public User(Long openId) {
		this.openId = openId;
		this.master = null;
		this.activation = true;
	}
	
	
	public boolean bind(User master) {
		this.master = master;
		return true;
	}

	public User getMasterUser() {
		return master == null ? master : this;
	}



	public Date getCreateTime() {
		return createTime;
	}


	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}



	public boolean isActivation() {
		return activation;
	}
	
}
