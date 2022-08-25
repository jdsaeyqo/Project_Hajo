package com.ssafy.hibernate.dto;

import java.util.Calendar;

import lombok.Data;

@Data
public class MatchNowDto {
	private Long nowSeq;
	private String userUid;
	private String userNickname;
	private Long grandplanSeq;
	private String grandplanTitle;
	private String planStatus;
	private String matchUserUid;
	private String matchUserNickname;
	private Long matchGrandplanSeq;
	private String matchPlanStatus;
	private Calendar matchStartdate;
	private Calendar matchEnddate;
	private String matchResult;
	private String userProgress;
	private String matchUserProgress;

}
