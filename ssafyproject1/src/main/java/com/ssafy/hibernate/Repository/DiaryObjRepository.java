package com.ssafy.hibernate.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.DiaryObj;

@Repository
@Transactional
public interface DiaryObjRepository extends JpaRepository<DiaryObj, Long>{
	
	public List<DiaryObj> findByDiarypage_DiarypageSeq(Long seq);

}
