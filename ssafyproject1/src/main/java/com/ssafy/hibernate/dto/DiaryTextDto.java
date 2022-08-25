package com.ssafy.hibernate.dto;

import com.ssafy.hibernate.entity.DiaryObjText;

import lombok.Data;

@Data
public class DiaryTextDto {
	
	// 글 시퀀스
	private Long objtextSeq;
	// 글 내용
	private String objtextContent;
	// 글 폰트
	private String objtextFont;
	// 글 폰트 크기
	private int objtextSize;
	// 글 색
	private String objtextColor;
	// 볼드인지
	private Boolean objtextIsbold;
	// 이탤릭인지
	private Boolean objtextIsitalic;
	
	public DiaryTextDto(DiaryObjText text) {
		this.setObjtextSeq(text.getObjtextSeq());
		this.setObjtextContent(text.getObjtextContent());
		this.setObjtextFont(text.getObjtextFont());
		this.setObjtextSize(text.getObjtextSize());
		this.setObjtextColor(text.getObjtextColor());
		this.setObjtextIsbold(text.getObjtextIsbold());
		this.setObjtextIsitalic(text.getObjtextIsitalic());
	}

}
