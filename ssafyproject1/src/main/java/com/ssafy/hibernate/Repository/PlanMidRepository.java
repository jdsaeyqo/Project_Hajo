package com.ssafy.hibernate.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.PlanMid;

@Repository
@Transactional
public interface PlanMidRepository extends JpaRepository<PlanMid, Long>{

	List<PlanMid> findByPlangrand_GrandplanSeq(Long seq);
}