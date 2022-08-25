package com.ssafy.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "LIST_ARCHIVE_TB")
@Data
public class ListArchive {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ARCHIVE_SEQ")
	private Long archiveSeq;

	@Column(name="ARCHIVE_NAME")
	private String archiveName;

	@Column(name="ARCHIVE_CONDPARAM")
	private String archiveCondparam;

	@Column(name="ARCHIVE_COND")
	private Integer cond;

	@Column(name="ARCHIVE_REWARDTYPE")
	private String archiveRewardtype;

	@Column(name="ARCHIVE_REWARDSEQ")
	private Long archiveRewardseq;

}
