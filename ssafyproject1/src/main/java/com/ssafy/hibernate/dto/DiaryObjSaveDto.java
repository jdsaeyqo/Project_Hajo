package com.ssafy.hibernate.dto;

import lombok.Data;

@Data
public class DiaryObjSaveDto {
	
	// 다이어리 페이지 시퀀스
	private Long diarypageSeq;
	// 오브젝트 타입
	private String diaryobjType;
	// x 좌표
	private int diaryobjXloc;
	// y 좌표
	private int diaryobjYloc;
	// 사진 이미지 주소
	private String objpicImg;
	// 글 정보
	private DiaryTextSaveDto objtext;

}
