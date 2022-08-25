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
@Table(name = "SHRPLAN_LIKE_TB")
@Data
public class ShrplanLike {
	// pk, 공유플랜 좋아요 시퀀스
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SHRPLANLIKE_SEQ")
	private Long shrplanlikeSeq;

	// user
	@ManyToOne
	@JoinColumn(name="USER_UID",referencedColumnName="USER_UID")
	private User user;

	// 공유플랜
	@ManyToOne
	@JoinColumn(name="SHRPLAN_SEQ",referencedColumnName="SHRPLAN_SEQ")
	private Shrplan shrplan;

	// 공유플랜 좋아요 누른 시간
	@Column(name="SHRPLANLIKE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar shrplanlikeTime;
}
