package com.ssafy.hibernate.dto;

import lombok.Data;

@Data
public class PlanTaskDto {

	//태스크 시퀀스
	private Long taskSeq;

	//소플랜 시퀀스
	private Long plansmallSeq;

	//태스크 이름
	private String title;

	//달성여부
	private Boolean isDone;

	//메모내용
	private String memo;

	//이미지정보
	private String img;

	//알람시간
	private String alramtime;
}
