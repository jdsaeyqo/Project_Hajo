package com.ssafy.hibernate.dto;

import lombok.Data;

@Data
public class BoastListSubDto {
	// 글 시퀀스
	private Long boastSeq;

	// 글 제목
	private String boastTitle;

	// 글 이미지
	private String boastImg;

}
