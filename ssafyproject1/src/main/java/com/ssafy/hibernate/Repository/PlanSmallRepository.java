package com.ssafy.hibernate.Repository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.PlanSmall;

@Repository
@Transactional
public interface PlanSmallRepository extends JpaRepository<PlanSmall, Long>{

	List<PlanSmall> findByPlanmid_Plangrand_GrandplanSeq(Long seq);

	// 이건 OX- 반환할때 쓰려고 만든건데
	// 마이페이지에서도 이거 쓴다
	Optional<PlanSmall> findByPlanmid_Plangrand_GrandplanSeqAndSmallplanDate(Long seq, Calendar date);

	// 홈화면에서 쓰던거야
	List<PlanSmall> findByPlanmid_Plangrand_User_UserUidAndSmallplanDate(String uid, Calendar date);
	
	// 하루에 태스크 몇개나 달성했는지 찾아주는 메소드를 위해서, 기간에 해당하는 소플랜 리스트 반환
	List<PlanSmall> findByPlanmid_Plangrand_GrandplanSeqAndSmallplanDateBetween(Long seq, Calendar start, Calendar end);

}