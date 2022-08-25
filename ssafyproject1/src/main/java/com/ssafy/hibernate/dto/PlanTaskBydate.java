package com.ssafy.hibernate.dto;

import java.util.List;

import lombok.Data;

@Data
public class PlanTaskBydate {
	// 대플랜 이름
	private String grandPlanName;
	
	// 중플랜 이름
	private String midPlanName;
	
	// 태스크 리스트 스트링
	List<String> tasks;

}
