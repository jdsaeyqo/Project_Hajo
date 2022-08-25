package com.ssafy.hibernate.dto;

import lombok.Data;

@Data
public class HomeSubDto {

	// 태스크 시퀀스
	private Long taskSeq;

	// 태스크 이름
	private String taskTitle;

	// 태스크 달성여부
	private Boolean isDone;
}
