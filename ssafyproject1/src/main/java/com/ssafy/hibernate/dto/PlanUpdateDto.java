package com.ssafy.hibernate.dto;

import lombok.Data;

@Data
public class PlanUpdateDto {

	// 바꾸고자 하는 플랜의 시퀀스
	private Long planSeq;

	// 바꾸고자 하는 것
	private String updateType;

	// 바꿀 내용
	private String updateContent;

}
