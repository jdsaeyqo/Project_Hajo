package com.ssafy.hibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserTitleDTO {
	private Long userTitleSeq;
	private String userUid;
	private Long titleSeq;
	private String titleName;
	private Boolean equipped;
	public UserTitleDTO(Long userTitleSeq, String userUid, Long titleSeq, String titleName) {
		this.userTitleSeq = userTitleSeq;
		this.userUid = userUid;
		this.titleSeq = titleSeq;
		this.titleName = titleName;
		this.equipped = false;
	}
	
	

}
