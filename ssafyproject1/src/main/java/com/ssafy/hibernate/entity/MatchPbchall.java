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

//다른사람 보라고 올려놓는 도전장

@Entity
@Table(name="MATCH_PBCHALL_TB")
@Data
@NamedEntityGraph(name="MatchPbchallWithUserAndPlanGrand", attributeNodes = {@NamedAttributeNode("user"), @NamedAttributeNode("planGrand")})
public class MatchPbchall {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PBCHALL_SEQ")
	private Long pbchallSeq;

	@ManyToOne
	@JoinColumn(name="USER_UID", referencedColumnName="USER_UID")
	private User user;

	@ManyToOne
	@JoinColumn(name="GRANDPLAN_SEQ", referencedColumnName="GRANDPLAN_SEQ")
	private PlanGrand planGrand;

	@Column(name="MATCH_PERIOD")
	private Integer matchPeriod;
}
