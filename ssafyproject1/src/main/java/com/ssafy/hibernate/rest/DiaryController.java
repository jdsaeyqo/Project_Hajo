package com.ssafy.hibernate.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hibernate.dto.DiaryDto;
import com.ssafy.hibernate.dto.DiaryHomeDto;
import com.ssafy.hibernate.dto.DiaryObjDto;
import com.ssafy.hibernate.dto.DiaryObjSaveDto;
import com.ssafy.hibernate.dto.DiaryTextSaveDto;
import com.ssafy.hibernate.service.DiaryService;

@RestController
@RequestMapping("/diary")
public class DiaryController {
	
	@Autowired
	private DiaryService dS;
	
	// 다이어리 목록 조회 (get)
	// http://아이피/diary/home/{uid}
	// 해당 유저의 다이어리들을 조회해줌 (리스트 - DiaryHomeDto)
	@GetMapping("/home/{uid}")
	public List<DiaryHomeDto> diaryHome(@PathVariable String uid){
		return dS.diaryHome(uid);
	}
	
	// 다이어리 조회 (get)
	// http://아이피/diary/{seq}
	// 해당 다이어리의 각 페이지에 대한 정보를 조회해줌 (DiaryDto, 리스트 - DiaryPageDto, 리스트 - DiaryObjDto, DiaryTextDto)
	// 자동연결 안된 것들 확인필
	@GetMapping("/{seq}")
	public DiaryDto getDiray(@PathVariable Long seq) {
		return dS.getDiary(seq);
	}
	
	
	
	
//오브젝트 사진 저장
//오브젝트 글 저장
//이 안에 다 넣을 수 있을 것 같아서 일단 생략
	
	// 오브젝트 추가 (post)
	// http://아이피/diary/obj
	// 200, 400 반환
	@PostMapping("/obj")
	public int addObj(@RequestBody DiaryObjSaveDto objdto) {
		System.out.println(objdto);
		return dS.addObj(objdto);
	}
	
	// 오브젝트 삭제 (delete)
	// http://아이피/diary/obj/{seq}
	// 200, 400 반환
	@DeleteMapping("/obj/{seq}")
	public int deleteObj(@PathVariable Long seq) {
		return dS.deleteObj(seq);
	}
	
	// 오브젝트 위치 옮기기 (put)
	// http://아이피/diary/obj/loc
	// Long 맵으로 시퀀스, x, y 좌표 입력받기
	// 200, 400 반환
	@PutMapping("/obj/loc")
	public int moveObj(@RequestBody List<Map<String, Long>> list) {
		return dS.moveObj(list);
	}
	
	// 이미지 교체하기 (put)
	// http://아이피/diary/obj/pic
	// String 맵으로 시퀀스, 이미지 받아가지고 이미지 교체해줌
	// 200, 400 반환
	@PutMapping("/obj/pic")
	public int modifyPic(@RequestBody Map<String, String> vo) {
		return dS.modifyPic(vo);
	}
	
	// 글 내용 교체하기 (put)
	// http://아이피/diary/obj/text
	// TextSaveDto 받아서 처리해주기
	// 200, 400 반환
	@PutMapping("/obj/text")
	public int modifyText(@RequestBody DiaryTextSaveDto textdto) {
		return dS.modifyText(textdto);
	}
	
	
	
	
	// 다이어리 추가 (post)
	// http://아이피/diary
	// String맵으로 uid랑 타입만 입력받고, 나머지는 알아서 채워서 다이어리 만들어주기
	// 200, 400 반환
	@PostMapping("")
	public int addDiary(@RequestBody Map<String, String> vo) {
		return dS.addDiary(vo);
	}
	
	// 다이어리 삭제 (delete)
	// http://아이피/diary/{seq}
	// 다이어리 시퀀스 입력받고 삭제 (패스)
	// 200, 400 반환
	@DeleteMapping("/{seq}")
	public int deleteDiary(@PathVariable Long seq) {
		return dS.deleteDiary(seq);
	}


}
