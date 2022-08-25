package com.ssafy.hibernate.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.BoastLike;

@Repository
@Transactional
public interface BoastLikeRepository extends JpaRepository<BoastLike, Long> {
	// 좋아요 누른 글 최신순 탑파이브
	List<BoastLike> findTop5ByUser_UserUidAndBoast_User_UserUidNotInOrderByBoastlikeTimeDesc(String uid, List<String> blocklist);
	List<BoastLike> findTop5ByUser_UserUidOrderByBoastlikeTimeDesc(String uid);
	
	// 좋아요 누른 글 최신순 전체조회
	List<BoastLike> findAllByUser_UserUidAndBoast_User_UserUidNotInOrderByBoastlikeTimeDesc(String uid, List<String> blocklist);
	List<BoastLike> findAllByUser_UserUidOrderByBoastlikeTimeDesc(String uid);

	// uid랑 seq 입력하면 있는지 없는지 찾아내기
	Boolean existsByUser_UserUidAndBoast_BoastSeq(String uid, Long seq);
	
	// uid랑 seq 입력하면 찾아내기
	Optional<BoastLike> findByUser_UserUidAndBoast_BoastSeq(String uid, Long seq);
}
