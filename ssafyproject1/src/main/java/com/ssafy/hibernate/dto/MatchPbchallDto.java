package com.ssafy.hibernate.dto;

import com.ssafy.hibernate.entity.PlanGrand;
import com.ssafy.hibernate.entity.User;

import lombok.Data;

@Data
public class MatchPbchallDto {
	private Long pbchallSeq;
	private String matchUserUid;
	private String matchUserImg;
	private String matchUserNickname;
	private Integer matchUserExp;
	private String matchUserTitle;
	private Integer matchUserWin;
	private Integer matchUserLose;
	private Integer matchUserDraw;
	private Integer matchUserWinwin;
	private Long matchGrandPlanSeq;
	private String matchGrandPlanName;
	
	public MatchPbchallDto(User u, PlanGrand p) {
		this.matchUserUid = u.getUserUid();
		this.matchUserImg = u.getUserImg();
		this.matchUserNickname = u.getUserNickname();
		this.matchUserExp = u.getUserExp();
		//this.matchUserTitle = u.getTitleSeq().toString();
		this.matchUserWin = u.getUserWin();
		this.matchUserLose = u.getUserLose();
		this.matchUserDraw = u.getUserDraw();
		this.matchUserWinwin = u.getUserWinwin();
		this.matchGrandPlanSeq = p.getGrandplanSeq();
		this.matchGrandPlanName = p.getGrandplanTitle();
	}
}
