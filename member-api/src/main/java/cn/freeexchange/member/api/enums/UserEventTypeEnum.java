package cn.freeexchange.member.api.enums;

import lombok.Getter;

@Getter
public enum UserEventTypeEnum {
	
	
	USER_SIGNUP(1,"USER_SIGNUP","用户注册"),
	USER_LOGIN(1,"USER_LOGIN","用户登录");
	
	
	private Integer id;
	private String code;
    private String name;
    
    private UserEventTypeEnum(Integer id,String code,String name) {
		this.id = id;
		this.name = name;
		this.code = code;
	}

}
