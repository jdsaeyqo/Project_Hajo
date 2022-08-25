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
@Table(name = "USER_TITLE_TB")
@Data
@NamedEntityGraph(name="UserTitleWithUserAndListTitle", attributeNodes = {@NamedAttributeNode("user"), @NamedAttributeNode("listTitle")})
public class UserTitle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USER_TITLE_SEQ")
	private Long userTitleSeq;

	@ManyToOne
	@JoinColumn(name="USER_UID", referencedColumnName="USER_UID")
	private User user;

	@ManyToOne
	@JoinColumn(name="TITLE_SEQ", referencedColumnName = "TITLE_SEQ")
	private ListTitle listTitle;


}
