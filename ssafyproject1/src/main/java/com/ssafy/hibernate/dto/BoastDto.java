package com.ssafy.hibernate.dto;

import lombok.Data;

@Data
public class BoastDto {

	// 자랑글 seq
	private Long boastSeq;

	// 작성자 유아이디
	private String wrtUid;

	// 작성자 닉네임
	private String wrtNickname;

	// 작성자 레벨
	private int wrtLevel;

	// 작성자 칭호
	private String wrtTitle;

	// 작성자 프로필 사진
	private String wrtImg;

	// 유저 좋아요 여부
	private Boolean userIslike;

	// 자랑글 제목
	private String boastTitle;

	// 자랑글 본문
	private String boastContent;

	// 자랑글 이미지
	private String boastImg;

	// 자랑글 좋아요 수
	private int boastLikecount;

	// 자랑글 작성시간
	private String boastWrttime;

}