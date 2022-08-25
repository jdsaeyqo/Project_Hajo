package com.ssafy.hibernate.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.hibernate.dto.PlanUpdateDto;
import com.ssafy.hibernate.entity.PlanMid;

public interface PlanMidService {
	public List<PlanMid> findAll();

	public Optional<PlanMid> findbyId(Long midplanSeq);

	public int deleteById(Long midplanSeq);

	public PlanMid save(PlanMid planmid);

	public void updateById(Long midplanSeq, PlanMid planmid);

	public int updateByDto(PlanUpdateDto updatedto);

	List<Long> findbyPlangrand_GrandplanSeq(Long seq);

	// 여기까지 기본적인 CRUD
}
