package com.ssafy.hibernate.dto;

import lombok.Data;

@Data
public class PlanHomeDto {
	// 찾고자 하는 플랜의 시퀀스
	private Long planSeq;

	// 찾고자 하는 플랜의 제목
	private String planTitle;

	// 찾고자 하는 플랜의 달성률
	private int planRate;
}
