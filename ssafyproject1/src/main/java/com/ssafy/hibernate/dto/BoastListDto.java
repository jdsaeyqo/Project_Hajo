package com.ssafy.hibernate.dto;

import java.util.List;

import lombok.Data;

@Data
public class BoastListDto {
	// 인기글 상위 5개
	List<BoastListSubDto> popularList;

	// 최신글 상위 5개
	List<BoastListSubDto> recentList;

	// 내가 쓴 글 최신순 상위 5개
	List<BoastListSubDto> myList;

	// 내가 좋아요 누른 글 최신순 상위 5개
	List<BoastListSubDto> likeList;

}
