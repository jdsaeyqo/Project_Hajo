package com.ssafy.hibernate.dto;

import lombok.Data;

@Data
public class ShrplanDto {

	// 공유플랜 seq
	private Long shrplanSeq;

	// 작성자 uid
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

	// 유저 북마크 여부
	private Boolean userIsbmk;
	
	// 대플랜 시퀀스
	private Long grandplanSeq;
	
	// 대플랜 제목
	private String grandplanTitle;

	// 공유플랜 제목
	private String shrplanTitle;

	// 공유플랜 본문
	private String shrplanContent;

	// 공유플랜 카테고리
	private String shrplanCategory;

	// 공유플랜 기간
	private int shrplanPeriod;

	// 공유플랜 요약
	private String shrplanSummary;

	// 공유플랜 좋아요 수
	private int shrplanLikecount;

	// 공유플랜 북마크 수
	private int shrplanBmkcount;

	// 공유플랜 작성시간
	private String shrplanWrttime;

}
