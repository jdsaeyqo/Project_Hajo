package com.ssafy.hibernate.dto;

import java.util.List;

import lombok.Data;

@Data
public class HomeDto {

	// 대플랜 시퀀스
	private Long grandplanSeq;

	// 대플랜 이름
	private String grandplanTitle;

	// 대플랜 매칭여부
	private Boolean isMatch;

	// 중플랜 시퀀스
	private Long midplanSeq;

	// 중플랜 이름
	private String midplanTitle;

	// 소플랜 시퀀스
	private Long smallplanSeq;

	// 서브디티오 리스트
	private List<HomeSubDto> subDto;


}
