package com.ssafy.hibernate.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.Shrplan;

@Repository
@Transactional
public interface ShrplanRepository extends JpaRepository<Shrplan, Long>{
	// 인기글 탑파이브
	List<Shrplan> findTop5ByUser_UserUidNotInOrderByShrplanLikecountDesc(List<String> blocklist);
	List<Shrplan> findTop5ByOrderByShrplanLikecountDesc();
	
	// 최신글 탑파이브
	List<Shrplan> findTop5ByUser_UserUidNotInOrderByShrplanWrttimeDesc(List<String> blocklist);
	List<Shrplan> findTop5ByOrderByShrplanWrttimeDesc();

	// 내가 쓴 글 최신순 탑파이브
	List<Shrplan> findTop5ByUser_UserUidOrderByShrplanWrttimeDesc(String uid);

	// 인기글 전체조회
	List<Shrplan> findAllByUser_UserUidNotInOrderByShrplanLikecountDesc(List<String> blocklist);
	List<Shrplan> findAllByOrderByShrplanLikecountDesc();

	// 최신글 탑파이브
	List<Shrplan> findAllByUser_UserUidNotInOrderByShrplanWrttimeDesc(List<String> blocklsit);
	List<Shrplan> findAllByOrderByShrplanWrttimeDesc();

	// 내가 쓴 글 최신순 전체조회
	List<Shrplan> findAllByUser_UserUidOrderByShrplanWrttimeDesc(String uid);

	// uid로 Shrplan 찾기
	Shrplan findByUser_UserUid(String uid);
}
