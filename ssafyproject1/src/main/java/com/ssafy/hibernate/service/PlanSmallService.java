package com.ssafy.hibernate.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ssafy.hibernate.dto.PlanSmallBydate;
import com.ssafy.hibernate.dto.PlanSmallDto;
import com.ssafy.hibernate.entity.PlanMid;
import com.ssafy.hibernate.entity.PlanSmall;

public interface PlanSmallService {
	public List<PlanSmall> findAll();

	public Optional<PlanSmall> findbyId(Long smallplanSeq);

	public void deleteById(Long smallplanSeq);

	public PlanSmall save(PlanSmall plansmall);

	// 여기까지 기본적인 CR(U)D

	public void updateById(Long smallplanSeq, PlanSmall plansmall);

	public void saveManySmallplan(PlanMid planmid, int howlong);

	public List<PlanSmall> saveAll(List<PlanSmall> plansmalls);

	public PlanSmallDto getPlanSmall(Long seq);

	public char Isdone(Long seq, Calendar date);
	
	public List<PlanSmallBydate> getPlanBydate(Map<String, String> vo);
}
