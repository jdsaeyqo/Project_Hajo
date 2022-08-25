package com.ssafy.hibernate.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.ShrplanLike;

@Repository
@Transactional
public interface ShrplanLikeRepository extends JpaRepository<ShrplanLike, Long>{
	// 좋아요 누른 글 최신순 탑파이브
	List<ShrplanLike> findTop5ByUser_UserUidAndShrplan_User_UserUidNotInOrderByShrplanlikeTimeDesc(String uid, List<String> blocklist);
	List<ShrplanLike> findTop5ByUser_UserUidOrderByShrplanlikeTimeDesc(String uid);
	
	// 좋아요 누른 글 최신순 전체조회
	List<ShrplanLike> findAllByUser_UserUidAndShrplan_User_UserUidNotInOrderByShrplanlikeTimeDesc(String uid, List<String> blocklist);
	List<ShrplanLike> findAllByUser_UserUidOrderByShrplanlikeTimeDesc(String uid);

	// uid랑 seq 입력하면 있는지 없는지 찾아내기
	Boolean existsByUser_UserUidAndShrplan_ShrplanSeq(String uid, Long seq);
	
	// uid랑 seq 입력하면 찾아내기
	Optional<ShrplanLike> findByUser_UserUidAndShrplan_ShrplanSeq(String uid, Long seq);
}
