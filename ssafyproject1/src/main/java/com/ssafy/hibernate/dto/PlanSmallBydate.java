package com.ssafy.hibernate.dto;

import lombok.Data;

@Data
public class PlanSmallBydate {
	
	// 해당일
	private String date;
	
	// 완료된 소플랜 수
	private int counts;

}
