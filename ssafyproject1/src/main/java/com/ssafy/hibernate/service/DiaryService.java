package com.ssafy.hibernate.service;

import java.util.List;
import java.util.Map;

import com.ssafy.hibernate.dto.DiaryDto;
import com.ssafy.hibernate.dto.DiaryHomeDto;
import com.ssafy.hibernate.dto.DiaryObjSaveDto;
import com.ssafy.hibernate.dto.DiaryTextSaveDto;

public interface DiaryService {
	
	// 해당 유저의 다이어리 조회하기
	public List<DiaryHomeDto> diaryHome(String uid);
	
	// 해당 다이어리의 각 페이지에 대한 정보 조회하기
	public DiaryDto getDiary(Long seq);
	
	
	
	// 오브젝트 추가
	public int addObj(DiaryObjSaveDto objdto);
	
	// 오브젝트 삭제
	public int deleteObj(Long seq);
	
	// 오브젝트 이동
	public int moveObj(List<Map<String, Long>> list);
	
	// 이미지 교체
	public int modifyPic(Map<String, String> vo);
	
	// 글 옵션 수정
	public int modifyText(DiaryTextSaveDto textdto);
	
	
	
	// 다이어리 추가
	public int addDiary(Map<String, String> vo);
	
	// 다이어리 삭제
	public int deleteDiary(Long seq);

}
