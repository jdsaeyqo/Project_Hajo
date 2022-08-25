package com.ssafy.hibernate.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@DynamicUpdate
@Table(name = "BOAST_TB")
@Data
public class Boast {

	// pk, 자랑글 seq
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="BOAST_SEQ")
	private Long boastSeq;

	// user
	@ManyToOne
	@JoinColumn(name="USER_UID",referencedColumnName="USER_UID")
	private User user;

	// 자랑글 제목
	@Column(name="BOAST_TITLE")
	private String boastTitle;

	// 자랑글 본문
	@Column(name="BOAST_CONTENT")
	private String boastContent;

	// 자랑글 이미지
	@Column(name="BOAST_IMG")
	private String boastImg;

	// 자랑글 좋아요 수
	@Column(name="BOAST_LIKECOUNT")
	private int boastLikecount;

	// 자랑글 인정 수
	@Column(name="BOAST_AGRCOUNT")
	private int boastAgrcount;

	// 자랑글 신고 수
	@Column(name="BOAST_RPTCOUNT")
	private int boastRptcount;

	// 자랑글 작성시각
	@Column(name="BOAST_WRTTIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar boastWrttime;
}
