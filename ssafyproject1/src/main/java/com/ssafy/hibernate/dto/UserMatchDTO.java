package com.ssafy.hibernate.dto;

import com.ssafy.hibernate.entity.User;

import lombok.Data;

@Data
public class UserMatchDTO {
	// 유저 uid
	private String userUid;
	// 유저 닉네임
	private String userNickname;
	// 유저 프로필 사진
	private String userImg;
	// 유저 장착중인 칭호
	private String titleName;
	// 유저 경험치
	private Integer userExp;
	// 유저 승리수
	private Integer userWin;
	// 유저 패배수
	private Integer userLose;
	// 유저 무승부수
	private Integer userDraw;
	// 유저 윈윈수
	private Integer userWinwin;
	//경쟁전 참여여부
	private Boolean userOnmatch;
	//유저 코멘트
	private String userComment;

	public UserMatchDTO(User entity) {
		this.userUid=entity.getUserUid();
		this.userNickname=entity.getUserNickname();
		this.userImg=entity.getUserImg();
		this.userExp=entity.getUserExp();
		this.userWin=entity.getUserWin();
		this.userLose=entity.getUserLose();
		this.userDraw=entity.getUserDraw();
		this.userWinwin=entity.getUserWinwin();
		this.userOnmatch=entity.getUserOnmatch();
		this.userComment=entity.getUserComment();
	}

}
