package com.ssafy.hibernate.dto;



import lombok.Data;

@Data
public class UserHistoryDTO {

	private Long historySeq;

	private String userUid;

	private String matchUserNickname;

	private Integer matchUserExp;

	private String matchUserTitleName;

	private String matchResult;
}
