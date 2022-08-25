package com.ssafy.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@DynamicUpdate
@Table(name = "DIARY_OBJTEXT_TB")
@Data
public class DiaryObjText {
	
	// pk, 텍스트 시퀀스
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="OBJTEXT_SEQ")
	private Long objtextSeq;
	
	// 다이어리 오브젝트
	@ManyToOne
	@JoinColumn(name="DIARYOBJ_SEQ",referencedColumnName="DIARYOBJ_SEQ")
	private DiaryObj diaryobj;
	
	// 텍스트 내용
	@Column(name="OBJTEXT_CONTENT")
	private String objtextContent;
	
	// 텍스트 폰트
	@Column(name="OBJTEXT_FONT")
	private String objtextFont;
	
	// 텍스트 폰트크기
	@Column(name="OBJTEXT_SIZE")
	private int objtextSize;
	
	// 텍스트 색
	@Column(name="OBJTEXT_COLOR")
	private String objtextColor;
	
	// 이거 볼드인지
	@Column(name="OBJTEXT_ISBOLD")
	private Boolean objtextIsbold;
	
	// 이거 이탤릭인지
	@Column(name="OBJTEXT_ISITALIC")
	private Boolean objtextIsitalic;

}
