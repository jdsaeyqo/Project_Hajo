package com.ssafy.hibernate.service;

import java.util.List;
import java.util.Map;

import com.ssafy.hibernate.dto.HomeSubDto;
import com.ssafy.hibernate.dto.PlanTaskBydate;
import com.ssafy.hibernate.dto.PlanTaskDto;
import com.ssafy.hibernate.entity.PlanSmall;
import com.ssafy.hibernate.entity.PlanTask;

public interface PlanTaskService {
	public List<PlanTask> findAll();

	public PlanTaskDto findbyId(Long taskSeq);

	public int deleteById(Long taskSeq);

	public int save(PlanTaskDto plantaskdto, PlanSmall plansmall);

//	public void updateById(Long taskSeq, PlanTask plantask);

	// 여기까지 기본적인 CRUD

	public int updateById(Long taskSeq, PlanTask plantask);

	public List<PlanTaskDto> findBySmallplan(Long seq);

	public List<HomeSubDto> findForHome(Long seq);

	public String checkPlanTask(Long seq);
	
	public PlanTaskBydate getPlanBydate(Map<String, String> vo);

}
