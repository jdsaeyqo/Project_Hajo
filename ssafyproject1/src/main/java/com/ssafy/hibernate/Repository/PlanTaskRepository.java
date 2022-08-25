package com.ssafy.hibernate.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.PlanTask;

@Repository
@Transactional
public interface PlanTaskRepository extends JpaRepository<PlanTask, Long>{

	List<PlanTask> findByPlansmall_SmallplanSeq(Long seq);
	
	// 마이페이지 조회용
	List<PlanTask> findByPlansmall_SmallplanSeqAndTaskIsdoneTrue(Long seq);
}