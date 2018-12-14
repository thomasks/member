package cn.freeexchange.member.domain.rpt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.freeexchange.member.domain.Meeting;

public interface MeetingRpt extends JpaRepository<Meeting, Long>, JpaSpecificationExecutor<Meeting>{
	
}
