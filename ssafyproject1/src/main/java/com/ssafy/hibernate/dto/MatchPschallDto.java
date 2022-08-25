package com.ssafy.hibernate.dto;

import lombok.Data;

@Data
public class MatchPschallDto {
	private Long pschallSeq;
	private String userUid;
	private String matchUserUid;
	private String matchUserNickname;
	private Integer matchUserExp;
	private String matchUserTitle;
	private String grandplanTitle;
	private String matchGrandplanTitle;
	private Integer matchPeriod;
}
