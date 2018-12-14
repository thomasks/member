package cn.freeexchange.member.domain.rpt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.freeexchange.member.domain.Member;


public interface MemberRpt extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member>{
	
	@Query("from Member where partner=?1 and openId=?2")
	Member findMemeber(Long partner,Long openId);
}
