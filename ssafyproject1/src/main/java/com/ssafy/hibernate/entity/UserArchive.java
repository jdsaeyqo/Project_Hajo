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
@Table(name="USER_ARCHIVE_TB")
@Data
@NamedEntityGraph(name="UserArchiveWithUserAndListArchive", attributeNodes = {@NamedAttributeNode("user"), @NamedAttributeNode("listArchive")})
public class UserArchive {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USER_ARCHIVE_SEQ")
	private Long userArchiveSeq;
	
	@ManyToOne
	@JoinColumn(name="ARCHIVE_SEQ", referencedColumnName="ARCHIVE_SEQ")
	private ListArchive listArchive;
	
	@ManyToOne
	@JoinColumn(name="USER_UID", referencedColumnName="USER_UID")
	private User user;
	
	@Column(name="USER_ARCHIVE_ISDONE")
	private Boolean userArchiveIsdone;
	
	@Column(name="USER_ARCHIVE_ISRECEIVED")
	private Boolean userArchiveIsreceived;
}
