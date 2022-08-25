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
@Table(name = "BOAST_LIKE_TB")
@Data
public class BoastLike {
	// pk, 자랑글 좋아요 시퀀스
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="BOASTLIKE_SEQ")
	private Long boastlikeSeq;

	// user
	@ManyToOne
	@JoinColumn(name="USER_UID",referencedColumnName="USER_UID")
	private User user;

	// 자랑글
	@ManyToOne
	@JoinColumn(name="BOAST_SEQ",referencedColumnName="BOAST_SEQ")
	private Boast boast;

	// 자랑글 좋아요 누른 시간
	@Column(name="BOASTLIKE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar boastlikeTime;
}
