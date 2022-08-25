package com.ssafy.hibernate.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.PlanGrand;

@Repository
public interface PlanGrandRepository extends JpaRepository<PlanGrand, Long>{

	List<PlanGrand> findByUser_UserUid(String uid);

	List<PlanGrand> findByUser_UserUidAndGrandplanIsmatchFalseAndGrandplanIsdoneFalse(String uid);

}
