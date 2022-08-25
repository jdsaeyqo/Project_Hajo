package com.ssafy.hibernate.entity;

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

import lombok.Data;

//개인이 받은 도전장

@Entity
@Table(name="MATCH_PSCHALL_TB")
@Data
@NamedEntityGraph(name="MatchPschallWithUserAndPlanGrandAndUserAndPlanGrand", attributeNodes = {@NamedAttributeNode("user"), @NamedAttributeNode("planGrand"), @NamedAttributeNode("matchUser"), @NamedAttributeNode("matchPlanGrand")})
public class MatchPschall {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PSCHALL_SEQ")
	private Long pschallSeq;

	@ManyToOne
	@JoinColumn(name="USER_UID", referencedColumnName="USER_UID")
	private User user;

	@ManyToOne
	@JoinColumn(name="GRANDPLAN_SEQ", referencedColumnName="GRANDPLAN_SEQ")
	private PlanGrand planGrand;

	@ManyToOne
	@JoinColumn(name="MATCH_USER_UID", referencedColumnName="USER_UID")
	private User matchUser;

	@ManyToOne
	@JoinColumn(name="MATCH_GRANDPLAN_SEQ", referencedColumnName="GRANDPLAN_SEQ")
	private PlanGrand matchPlanGrand;

	@Column(name="MATCH_PERIOD")
	private Integer matchPeriod;
}
