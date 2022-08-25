package com.ssafy.hibernate.dto;

import com.ssafy.hibernate.entity.DiaryObj;

import lombok.Data;

@Data
public class DiaryObjDto {
	
	// 오브젝트 시퀀스
	private Long diaryobjSeq;
	// 오브젝트 타입
	private String diaryobjType;
	// x 좌표
	private int diaryobjXloc;
	// y 좌표
	private int diaryobjYloc;
	// 사진 이미지 주소
	private String objpicImg;
	// 글 정보
	private DiaryTextDto objtext;
	
	public DiaryObjDto(DiaryObj obj) {
		this.diaryobjSeq = obj.getDiaryobjSeq();
		this.diaryobjType = obj.getDiaryobjType();
		this.diaryobjXloc = obj.getDiaryobjXloc();
		this.diaryobjYloc = obj.getDiaryobjYloc();
		this.objpicImg = obj.getObjpicImg();
		// 글자 정보는 수동;
	}

}
