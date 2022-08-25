package com.ssafy.hibernate.dto;

import lombok.Data;

@Data
public class PlanListDto {

	// 찾고자 하는 플랜의 시퀀스
	private Long planSeq;

	// 찾고자 하는 플랜의 제목
	private String planTitle;

}
