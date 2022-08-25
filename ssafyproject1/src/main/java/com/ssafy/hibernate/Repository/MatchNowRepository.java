package com.ssafy.hibernate.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.hibernate.entity.MatchNow;

public interface MatchNowRepository extends JpaRepository<MatchNow, Long>{

	@Override
	@EntityGraph("MatchNowWithUserAndPlanGrandAndUserAndPlanGrand")
	public List<MatchNow> findAll();

	@EntityGraph("MatchNowWithUserAndPlanGrandAndUserAndPlanGrand")
	public List<MatchNow> findByUser_UserUid(String uid);
	
	public Optional<MatchNow> findByUser_UserUidAndPlanGrand_GrandplanSeq(String uid, Long seq);
	
	public Optional<MatchNow> findByMatchUser_UserUidAndMatchPlanGrand_GrandplanSeq(String uid, Long seq);
	
	public Boolean existsByPlanGrand_GrandplanSeq(Long seq);
	
}
