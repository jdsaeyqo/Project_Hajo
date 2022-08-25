package com.ssafy.hibernate.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.ShrplanBmk;

@Repository
@Transactional
public interface ShrplanBmkRepository extends JpaRepository<ShrplanBmk, Long>{
	// 북마크 누른 글 최신순 탑파이브
	List<ShrplanBmk> findTop5ByUser_UserUidAndShrplan_User_UserUidNotInOrderByShrplanbmkTimeDesc(String uid, List<String> blocklist);
	List<ShrplanBmk> findTop5ByUser_UserUidOrderByShrplanbmkTimeDesc(String uid);

	// 북마크 누른 글 최신순 전체조회
	List<ShrplanBmk> findAllByUser_UserUidAndShrplan_User_UserUidNotInOrderByShrplanbmkTimeDesc(String uid, List<String> blocklist);
	List<ShrplanBmk> findAllByUser_UserUidOrderByShrplanbmkTimeDesc(String uid);

	// uid랑 seq 입력하면 있는지 없는지 찾아내기
	Boolean existsByUser_UserUidAndShrplan_ShrplanSeq(String uid, Long seq);
	
	// uid랑 seq 입력하면 찾아내기
	Optional<ShrplanBmk> findByUser_UserUidAndShrplan_ShrplanSeq(String uid, Long seq);
}
