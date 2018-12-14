package cn.freeexchange.member.domain.rpt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.freeexchange.member.domain.User;

public interface UserRpt extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{
	
}
