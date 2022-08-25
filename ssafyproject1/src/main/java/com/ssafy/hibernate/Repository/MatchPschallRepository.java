package com.ssafy.hibernate.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.MatchPschall;

@Repository
public interface MatchPschallRepository extends JpaRepository<MatchPschall, Long>{

	@EntityGraph("MatchPschallWithUserAndPlanGrandAndUserAndPlanGrand")
	Optional<MatchPschall> findByPschallSeq(Long seq);

	@EntityGraph("MatchPschallWithUserAndPlanGrandAndUserAndPlanGrand")
	List<MatchPschall> findByUser_UserUid(String uid);

	@EntityGraph("MatchPschallWithUserAndPlanGrandAndUserAndPlanGrand")
	List<MatchPschall> findByMatchUser_UserUid(String uid);

	@Override
	@EntityGraph("MatchPschallWithUserAndPlanGrandAndUserAndPlanGrand")
	List<MatchPschall> findAll();

	@EntityGraph("MatchPschallWithUserAndPlanGrandAndUserAndPlanGrand")
	@Transactional
	void deleteByUser_UserUidAndMatchUser_UserUid(String userUid, String matchUserUid);

	@EntityGraph("MatchPschallWithUserAndPlanGrandAndUserAndPlanGrand")
	Optional<MatchPschall> findByUser_UserUidAndMatchUser_UserUid(String userUid, String matchUserUid);

	@EntityGraph("MatchPschallWithUserAndPlanGrandAndUserAndPlanGrand")
	@Transactional
	void deleteByPschallSeq(Long seq);

}
