package cn.freeexchange.member.api.enums;

import lombok.Getter;

@Getter
public enum IdentityTypeEnum {
	
	
	MOBILE(1,"MOBILE","手机号"),
	EMAIL(2,"EMAIL","邮箱");
	
	
	private Integer id;

	private String code;
    private String name;
    

	private IdentityTypeEnum(Integer id,String code,String name) {
		this.id = id;
		this.name = name;
		this.code = code;
	}
    
	
	
    
    
    
}
