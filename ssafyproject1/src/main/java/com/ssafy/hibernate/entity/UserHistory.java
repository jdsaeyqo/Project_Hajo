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

@Entity
@Table(name = "USER_HISTORY_TB")
@Data
@NamedEntityGraph(name="UserHistoryWithUser", attributeNodes = @NamedAttributeNode("user"))
public class UserHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="HISTORY_SEQ")
	private Long historySeq;

	@ManyToOne
	@JoinColumn(name="USER_UID", referencedColumnName="USER_UID")
	private User user;

//	@Column(name="USER_UID")
//	private String userUid;

	@Column(name="MATCH_USER_NICKNAME")
	private String matchUserNickname;

	@Column(name="MATCH_USER_EXP")
	private Integer matchUserExp;

	@Column(name="MATCH_USER_TITLE")
	private Long matchUserTitle;

	@Column(name="MATCH_RESULT")
	private String matchResult;
}
