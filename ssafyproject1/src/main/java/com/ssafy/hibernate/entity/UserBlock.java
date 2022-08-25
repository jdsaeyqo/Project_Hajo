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
@Table(name="USER_BLOCK_TB")
@Data
@NamedEntityGraph(name="UserBlockWithUserAndUser", attributeNodes = {@NamedAttributeNode("user"), @NamedAttributeNode("blockUser")})
public class UserBlock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="BLOCK_SEQ")
	private Long blockSeq;
	
	@ManyToOne
	@JoinColumn(name="USER_UID", referencedColumnName="USER_UID")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="BLOCK_UID", referencedColumnName="USER_UID")
	private User blockUser;
}
