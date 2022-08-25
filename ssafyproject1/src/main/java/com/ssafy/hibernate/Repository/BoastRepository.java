package com.ssafy.hibernate.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.Boast;

@Repository
@Transactional
public interface BoastRepository extends JpaRepository<Boast, Long>{
	// 인기글 탑파이브
	List<Boast> findTop5ByUser_UserUidNotInOrderByBoastLikecountDesc(List<String> blockUsers);
	List<Boast> findTop5ByOrderByBoastLikecountDesc();

	// 최신글 탑파이브
	List<Boast> findTop5ByUser_UserUidNotInOrderByBoastWrttimeDesc(List<String> blockUsers);
	List<Boast> findTop5ByOrderByBoastWrttimeDesc();

	// 내가 쓴 글 최신순 탑파이브
	List<Boast> findTop5ByUser_UserUidOrderByBoastWrttimeDesc(String uid);

	// 인기글 전체조회
	List<Boast> findAllByUser_UserUidNotInOrderByBoastLikecountDesc(List<String> blockUsers);
	List<Boast> findAllByOrderByBoastLikecountDesc();

	// 최신글 탑파이브
	List<Boast> findAllByUser_UserUidNotInOrderByBoastWrttimeDesc(List<String> blockUsers);
	List<Boast> findAllByOrderByBoastWrttimeDesc();

	// 내가 쓴 글 최신순 전체조회
	List<Boast> findAllByUser_UserUidOrderByBoastWrttimeDesc(String uid);

	// uid로 boast 찾기
	Boast findByUser_UserUidAndUser_UserUidNotIn(String uid, List<String> blockUsers);

}
