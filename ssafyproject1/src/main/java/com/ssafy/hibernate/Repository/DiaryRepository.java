package com.ssafy.hibernate.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.Diary;

@Repository
@Transactional
public interface DiaryRepository extends JpaRepository<Diary, Long>{
	
	// uid로 조회하기
	List<Diary> findByUser_UserUid(String uid);
	
}
