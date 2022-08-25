package com.ssafy.hibernate.dto;

import lombok.Data;

@Data
public class PlanDetailSubDto {

	// 날짜
	private String smallplanDate;

	// 소플랜 아이디
	private Long smallplanSeq;

	// 태스크 존재여부
	private Boolean hasTask;

	// 중플랜 아이디
	private Long midplanSeq;

	// 중플랜 이름
	private String midplanTitle;

	// 중플랜 색
	private String midplanColor;

	// 중플랜 시작일
	private String midplanStart;

}
