package com.ssafy.hibernate.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name="MATCH_NOW_TB")
@Data
@NamedEntityGraph(name="MatchNowWithUserAndPlanGrandAndUserAndPlanGrand", attributeNodes = {@NamedAttributeNode("user"), @NamedAttributeNode("planGrand"), @NamedAttributeNode("matchUser"), @NamedAttributeNode("matchPlanGrand")})
public class MatchNow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="NOW_SEQ")
	private Long nowSeq;

	@ManyToOne
	@JoinColumn(name="USER_UID", referencedColumnName="USER_UID")
	private User user;

	@ManyToOne
	@JoinColumn(name="GRANDPLAN_SEQ", referencedColumnName="GRANDPLAN_SEQ")
	private PlanGrand planGrand;

	@Column(name="PLAN_STATUS")
	private String planStatus;

	@ManyToOne
	@JoinColumn(name="MATCH_USER_UID", referencedColumnName="USER_UID")
	private User matchUser;

	@ManyToOne
	@JoinColumn(name="MATCH_GRANDPLAN_SEQ", referencedColumnName="GRANDPLAN_SEQ")
	private PlanGrand matchPlanGrand;

	@Column(name="MATCH_PLAN_STATUS")
	private String matchPlanStatus;

	@Column(name="MATCH_STARTDATE")
	@Temporal(TemporalType.DATE)
	private Calendar matchStartdate;

	@Column(name="MATCH_ENDDATE")
	@Temporal(TemporalType.DATE)
	private Calendar matchEnddate;

	@Column(name="MATCH_RESULT")
	private String matchResult;

	@Column(name="USER_PROGRESS")
	private String userProgress;

	@Column(name="MATCH_USER_PROGRESS")
	private String matchUserProgress;
}
