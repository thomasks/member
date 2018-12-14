package cn.freeexchange.member.domain.rpt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.freeexchange.member.domain.VerifyCode;

public interface VerifyCodeRpt extends JpaRepository<VerifyCode, Long>, JpaSpecificationExecutor<VerifyCode>{
	
	
	@Query("from VerifyCode where verifyCodeId=?1 ")
	VerifyCode getByVerifyCodeId(String verifyCodeId);
}
