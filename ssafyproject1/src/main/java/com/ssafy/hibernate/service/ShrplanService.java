package com.ssafy.hibernate.service;

import java.util.List;
import java.util.Map;

import com.ssafy.hibernate.dto.ShrplanDto;
import com.ssafy.hibernate.dto.ShrplanListDto;
import com.ssafy.hibernate.dto.ShrplanListSubDto;
import com.ssafy.hibernate.entity.Shrplan;
import com.ssafy.hibernate.entity.ShrplanBmk;
import com.ssafy.hibernate.entity.ShrplanLike;

public interface ShrplanService {
	
	// 탑파이브 4개 묶음으로 정리하기
	public ShrplanListDto getHomeShrplan(String uid);
	
	// 전체보기 리스트 정리하기
	public List<ShrplanListSubDto> getTypeShrplan(Map<String, String> vo);

	// 인기글, 최신순, 내가 쓴 글 썸네일 dto로 변환하기
	// 좋아요 누른 글 썸네일 dto로 변환하기
	// 북마크 누른 글 썸네일 dto로 변환하기
	List<ShrplanListSubDto> getSubDto(List<Shrplan> list);
	List<ShrplanListSubDto> getLikeSubDto(List<ShrplanLike> list);
	List<ShrplanListSubDto> getBmkSubDto(List<ShrplanBmk> list);

	// 전체글 상세보기 dto로 변환하기
	// 좋아요 누른 글 상세보기 dto로 변환하기
	// 북마크 누른 글 상세보기 dto로 변환하기
//	List<ShrplanDto> getShrplanDto(List<Shrplan> list, String uid);
//	List<ShrplanDto> getLikeShrplanDto(List<ShrplanLike> list);
//	List<ShrplanDto> getBmkShrplanDto(List<ShrplanBmk> list);

	// 하나의 글 상세보기 dto로 변환하기
	public ShrplanDto getDetailShrplan(Map<String, String> vo);

	// 공유플랜 작성
	// 공유플랜 수정
	// 공유플랜 삭제
	public Map<String, Boolean> saveShrplan(ShrplanDto shrplandto);
	public Map<String, Boolean> updateShrplan(ShrplanDto shrplandto);
	public Map<String, Boolean> deleteShrplan(Long seq);

	// 좋아요 누르기
	// 좋아요 빼기
	public Map<String, Boolean> updateShrplanLike(Map<String, String> vo);
	public Map<String, Boolean> deleteShrplanLike(Map<String, String> vo);

	// 북마크 누르기
	// 북마크 빼기
	public Map<String, Boolean> updateShrplanBmk(Map<String, String> vo);
	public Map<String, Boolean> deleteShrplanBmk(Map<String, String> vo);

	// 신고하기
	public Map<String, String> reportShrplan(Map<String, String> vo);

}
