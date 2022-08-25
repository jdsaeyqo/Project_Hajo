package com.ssafy.hibernate.dto;

import lombok.Data;

@Data
public class ShrplanListSubDto {
	
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
	
	// 대플랜 시퀀스
	private Long grandplanSeq;
	
	// 대플랜 제목
	private String grandplanTitle;

	// 공유플랜 제목
	private String shrplanTitle;

	// 공유플랜 카테고리
	private String shrplanCategory;
	
	// 공유플랜 좋아요 수
	private int shrplanLikecount;

	// 공유플랜 북마크 수
	private int shrplanBmkcount;

}
