package com.ssafy.hibernate.Repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.DiaryObjText;

@Repository
@Transactional
public interface DiaryObjTextRepository extends JpaRepository<DiaryObjText, Long> {
	
	Optional<DiaryObjText> findByDiaryobj_DiaryobjSeq(Long seq);

}
