package com.ssafy.hibernate.dto;

import com.ssafy.hibernate.entity.ListArchive;

import lombok.Data;

@Data
public class ListArchiveDto {
	private Long archiveSeq;
	private String archiveName;
	private String archiveCondparam;
	private Integer cond;
	private String archiveRewardtype;
	private Long archiveRewardseq;
	private String rewardName;
	
	public ListArchiveDto(ListArchive l) {
		this.archiveSeq = l.getArchiveSeq();
		this.archiveName = l.getArchiveName();
		this.archiveCondparam = l.getArchiveCondparam();
		this.cond = l.getCond();
		this.archiveRewardtype = l.getArchiveRewardtype();
		this.archiveRewardseq = l.getArchiveRewardseq();
		this.rewardName = "-";
		
	}
}
