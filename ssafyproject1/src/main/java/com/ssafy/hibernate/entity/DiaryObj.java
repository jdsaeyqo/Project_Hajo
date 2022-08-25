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
@Table(name = "DIARY_OBJ_TB")
@Data
public class DiaryObj {
	
	//pk, 오브젝트 시퀀스
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="DIARYOBJ_SEQ")
	private Long diaryobjSeq;
	
	// 다이어리 페이지
	@ManyToOne
	@JoinColumn(name="DIARYPAGE_SEQ",referencedColumnName="DIARYPAGE_SEQ")
	private DiaryPage diarypage;
	
	// 오브젝트 타입
	@Column(name="DIARYOBJ_TYPE")
	private String diaryobjType;
	
	// 오브젝트 X 좌표
	@Column(name="DIARYOBJ_XLOC")
	private int diaryobjXloc;
	
	// 오브젝트 Y 좌표
	@Column(name="DIARYOBJ_YLOC")
	private int diaryobjYloc;
	
	// 사진일 경우 참조할 이미지 주소
	@Column(name="OBJPIC_IMG")
	private String objpicImg;
	
	// 텍스트일 경우 참조할 텍스트 시퀀스... 여차하면 원투매니로 연결한다
	@Column(name="OBJTEXT_SEQ")
	private Long objtextSeq;

}
