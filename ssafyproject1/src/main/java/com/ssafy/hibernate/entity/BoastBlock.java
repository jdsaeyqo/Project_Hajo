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
@NamedEntityGraph(name="BoastBlockWithBoastAndBoast", attributeNodes = {@NamedAttributeNode("user"), @NamedAttributeNode("blockBoast")})
@Table(name = "BOAST_BLOCK_TB")
@Data
public class BoastBlock {

	// pk, 블락 시퀀스
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOASTBLOCK_SEQ")
	private Long boastblockSeq;

	// 자랑글
	@ManyToOne
	@JoinColumn(name = "BOAST_SEQ", referencedColumnName = "BOAST_SEQ")
	private Boast blockBoast;

	// 유저
	@ManyToOne
	@JoinColumn(name = "USER_UID", referencedColumnName = "USER_UID")
	private User user;
}
