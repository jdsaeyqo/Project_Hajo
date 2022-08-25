package com.ssafy.hibernate.dto;

import lombok.Data;

@Data
public class DiaryTextSaveDto {
		// 오브젝트 시퀀스
		private Long objSeq;
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
		
}
