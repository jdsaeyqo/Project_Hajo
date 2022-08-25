package com.ssafy.hibernate.dto;

import com.ssafy.hibernate.entity.UserArchive;

import lombok.Data;

@Data
public class UserArchiveDto {
	private Long userArchiveSeq;
	private String archiveName;
	private String archiveCondparam;
	private Integer cond;
	private Integer usercond;
	private String archiveRewardtype;
	private Long archiveRewardseq;
	private String rewardName;
	private Boolean userArchiveIsdone;
	private Boolean userArchiveIsreceived;
	
	public UserArchiveDto(UserArchive u) {
		this.userArchiveSeq=u.getUserArchiveSeq();
		this.archiveName=u.getListArchive().getArchiveName();
		this.archiveCondparam=u.getListArchive().getArchiveCondparam();
		this.cond=u.getListArchive().getCond();
		this.archiveRewardtype=u.getListArchive().getArchiveRewardtype();
		this.archiveRewardseq=u.getListArchive().getArchiveRewardseq();
		this.rewardName="-";
		this.userArchiveIsdone=u.getUserArchiveIsdone();
		this.userArchiveIsreceived=u.getUserArchiveIsreceived();
	}
}
