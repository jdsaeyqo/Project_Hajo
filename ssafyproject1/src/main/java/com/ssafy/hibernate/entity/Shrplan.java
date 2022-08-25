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
@Table(name = "SHRPLAN_TB")
@Data
public class Shrplan {
	// pk, 공유플랜 시퀀스
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SHRPLAN_SEQ")
	private Long shrplanSeq;

	// user
	@ManyToOne
	@JoinColumn(name="USER_UID",referencedColumnName="USER_UID")
	private User user;

	// 대플랜
	@ManyToOne
	@JoinColumn(name="GRANDPLAN_SEQ", referencedColumnName="GRANDPLAN_SEQ")
	private PlanGrand plangrand;

	// 공유플랜 제목
	@Column(name="SHRPLAN_TITLE")
	private String shrplanTitle;

	// 공유플랜 본문
	@Column(name="SHRPLAN_CONTENT")
	private String shrplanContent;

	// 공유플랜 카테고리
	@Column(name="SHRPLAN_CATEGORY")
	private String shrplanCategory;

	// 공유플랜 기간
	@Column(name="SHRPLAN_PERIOD")
	private int shrplanPeriod;

	// 공유플랜 요약
	@Column(name="SHRPLAN_SUMMARY")
	private String shrplanSummary;

	// 공유플랜 좋아요 수
	@Column(name="SHRPLAN_LIKECOUNT")
	private int shrplanLikecount;

	// 공유플랜 북마크 수
	@Column(name="SHRPLAN_BMKCOUNT")
	private int shrplanBmkcount;

	// 공유플랜 신고 수
	@Column(name="SHRPLAN_RPTCOUNT")
	private int shrplanRptcount;

	// 공유플랜 작성시각
	@Column(name="SHRPLAN_WRTTIME")
	@Temporal(TemporalType.DATE)
	private Calendar shrplanWrttime;


}
