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
@Table(name = "DIARY_TB")
@Data
public class Diary {
	
	// pk, 다이어리 시퀀스
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="DIARY_SEQ")
	private Long diarySeq;
	
	// 유저
	@ManyToOne
	@JoinColumn(name="USER_UID",referencedColumnName="USER_UID")
	private User user;
	
	// 다이어리 제목
	@Column(name="DIARY_TITLE")
	private String diaryTitle;
	
	// 다이어리 타입
	@Column(name="DIARY_TYPE")
	private String diaryType;

}
