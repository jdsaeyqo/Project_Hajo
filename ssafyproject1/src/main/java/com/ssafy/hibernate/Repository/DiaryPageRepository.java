package com.ssafy.hibernate.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.DiaryPage;

@Repository
@Transactional
public interface DiaryPageRepository extends JpaRepository<DiaryPage, Long> {
	
	public List<DiaryPage> findByDiary_DiarySeq(Long seq);

}
