package cn.freeexchange.member.domain.rpt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.freeexchange.member.domain.Identity;

public interface IdentityRpt extends JpaRepository<Identity, Long>, JpaSpecificationExecutor<Identity>{
	
	@Query("from Identity where partner=?1 and identityType=?2 and identityValue=?3")
	Identity findIdentity(Long partner, String identityType, String identityValue);
}
