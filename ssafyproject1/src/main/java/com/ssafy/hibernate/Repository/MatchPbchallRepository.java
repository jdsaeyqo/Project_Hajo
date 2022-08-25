package com.ssafy.hibernate.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.MatchPbchall;

@Repository
public interface MatchPbchallRepository extends JpaRepository<MatchPbchall, Long>{

	@Override
	@EntityGraph("MatchPbchallWithUserAndPlanGrand")
	List<MatchPbchall> findAll();

	@EntityGraph("MatchPbchallWithUserAndPlanGrand")
	List<MatchPbchall> findByUser_UserUid(String uid);

	@EntityGraph("MatchPbchallWithUserAndPlanGrand")
	Optional<MatchPbchall> findByUser_UserUidAndPlanGrand_GrandplanSeq(String uid, Long grandplanSeq);

	@EntityGraph("MatchPbchallWithUserAndPlanGrand")
	@Transactional
	void deleteByPlanGrand_GrandplanSeq(Long grandplanSeq);

}
