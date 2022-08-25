package com.ssafy.hibernate.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class PlanDetailDto {

	// 대플랜 시퀀스를 받아서
	private Long grandplanSeq;

	// 대플랜 이름
	private String grandplanTitle;

	// 대플랜 설명
	private String grandplanDesc;
//
//	// 대플랜 시작일
//	private Calendar grandplanStart;
//
//	// 대플랜 종료일
//	private Calendar grandplanEnd;

	// 대플랜 시작일
	private String grandplanStart;

	// 대플랜 종료일
	private String grandplanEnd;

	// 대플랜 컬러
	private String grandplanColor;

	// 오늘 날짜
	private LocalDate today;

	// 대플랜에 속해있는 소플랜 리스트 List<소플랜Dto> 이렇게
	private List<PlanDetailSubDto> subDto;


}
