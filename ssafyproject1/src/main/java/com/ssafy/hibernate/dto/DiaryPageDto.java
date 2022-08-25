package com.ssafy.hibernate.dto;

import java.util.List;

import com.ssafy.hibernate.entity.DiaryPage;

import lombok.Data;

@Data
public class DiaryPageDto {
	
	// 페이지 시퀀스
	private Long diarypageSeq;
	// 몇번째 페이지인지
	private int diarypageNum;
	// 오브젝트 리스트
	private List<DiaryObjDto> dirayObjs;
	
	public DiaryPageDto(DiaryPage page) {
		this.diarypageSeq = page.getDiarypageSeq();
		this.diarypageNum = page.getDiarypageNum();
		// 리스트도 수동
	}

}
