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
@Table(name = "DIARY_PAGE_TB")
@Data
public class DiaryPage {
	
	// pk, 다이어리 페이지 시퀀스
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="DIARYPAGE_SEQ")
	private Long diarypageSeq;
	
	// 다이어리
	@ManyToOne
	@JoinColumn(name="DIARY_SEQ",referencedColumnName="DIARY_SEQ")
	private Diary diary;
	
	// 다이어리 페이지 넘버
	@Column(name="DIARYPAGE_NUM")
	private int diarypageNum;

}
