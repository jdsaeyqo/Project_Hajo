package com.ssafy.hibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRankDto {
	private String userUid;
	private String userNickname;
	private Integer userCpoint;
	private Integer ranking;
}
