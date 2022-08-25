package com.ssafy.hibernate.service;

import java.util.List;
import java.util.Map;

import com.ssafy.hibernate.dto.BoastDto;
import com.ssafy.hibernate.dto.BoastListDto;
import com.ssafy.hibernate.dto.BoastListSubDto;
import com.ssafy.hibernate.entity.Boast;
import com.ssafy.hibernate.entity.BoastLike;

public interface BoastService {

	// 탑파이브 4개 묶음으로 정리하기
	public BoastListDto getHomeBoast(String uid);

	// 탑파이브 (인기글, 최신순, 내가 쓴 글) 썸네일 dto로 변환하기
	// 탑파이브 (좋아요 누른 글) dto로 변환하기
	List<BoastListSubDto> getSubDto(List<Boast> list);
	List<BoastListSubDto> getLikeSubDto(List<BoastLike> list);

	// 전체보기 리스트 정리하기
	public List<BoastDto> getTypeBoast(Map<String, String> vo);

	// 전체글 상세보기 dto로 변환하기
	// 좋아요 누른 글 상세보기 dto로 변환하기
	List<BoastDto> getBoastDto(List<Boast> list, String uid);
	List<BoastDto> getLikeBoastDto(List<BoastLike> list);

	// 하나의 글 상세보기 dto로 변환하기
	public BoastDto getDetailBoast(Map<String, String> vo);

	// 자랑글 작성
	// 자랑글 수정
	// 자랑글 삭제
	public Map<String, Boolean> saveBoast(BoastDto boastdto);
	public Map<String, Boolean> updateBoast(BoastDto boastdto);
	public Map<String, Boolean> deleteBoast(Long seq);

	// 좋아요 누르기
	// 좋아요 빼기
	public Map<String, Boolean> updateBoastLike(Map<String, String> vo);
	public Map<String, Boolean> deleteBoastLike(Map<String, String> vo);

	// 신고하기
	public Map<String, String> reportBoast(Map<String, String> vo);
	
//	// 차단 추가하기
//	// 차단 삭제하기
//	public Map<String, String> updateBoastBlock(Long seq);
//	public Map<String, String> deleteBoastBlock(Long seq);



}
