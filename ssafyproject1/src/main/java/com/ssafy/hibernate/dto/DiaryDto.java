package com.ssafy.hibernate.dto;

import java.util.List;

import com.ssafy.hibernate.entity.Diary;

import lombok.Data;

@Data
public class DiaryDto {
	
	// 다이어리 시퀀스
	private Long diarySeq;
	// 유저
	private String userUid;
	// 다이어리 이름
	private String diaryTitle;
	// 다이어리 타입
	private String diaryType;
	// 페이지 리스트
	private List<DiaryPageDto> diaryPages;
	
	public DiaryDto(Diary diary) {
		this.diarySeq=diary.getDiarySeq();
		this.userUid=diary.getUser().getUserUid();
		this.diaryTitle=diary.getDiaryTitle();
		this.diaryType=diary.getDiaryType();
		// 리스트는 수동으로 채우기
	}

}
