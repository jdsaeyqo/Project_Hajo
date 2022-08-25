package com.ssafy.hibernate.dto;

import com.ssafy.hibernate.entity.Diary;

import lombok.Data;

@Data
public class DiaryHomeDto {
	
	// 다이어리 시퀀스
	private Long diarySeq;
	// 다이어리 제목
	private String diaryTitle;
	// 다이어리 타입
	private String diaryType;
	
	public DiaryHomeDto(Diary diary) {
		this.diarySeq=diary.getDiarySeq();
		this.diaryTitle=diary.getDiaryTitle();
		this.diaryType=diary.getDiaryType();
	}
	

}
