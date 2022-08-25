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

import com.ssafy.hibernate.dto.BoastDto;
import com.ssafy.hibernate.dto.ShrplanDto;
import com.ssafy.hibernate.dto.ShrplanListDto;
import com.ssafy.hibernate.dto.ShrplanListSubDto;
import com.ssafy.hibernate.service.ShrplanService;

@RestController
@RequestMapping("/shrplan")
public class ShrplanController {

	@Autowired
	private ShrplanService sS;

	// http://아이피/shrplan/{uid}
	// 홈 화면에 나와야 하는 게시판 타입별 리스트 조회 (get)
	@GetMapping("/{uid}")
	public ShrplanListDto shrplanHome(@PathVariable String uid) {
		return sS.getHomeShrplan(uid);
	}

	// http://아이피/shrplan/type
	// 게시판 타입(인기글, 최신글, 나의 어쩌구... 등)에 해당하는 글 전체 조회 (post)
	@PostMapping("/type")
	public List<ShrplanListSubDto> shrplanType(@RequestBody Map<String, String> vo){
		return sS.getTypeShrplan(vo);
	}

	// http://아이피/shrplan/detail
	// 게시판 시퀀스에 해당하는 글 보여주기 (post)
	@PostMapping("/detail")
	public ShrplanDto shrplanDetail(@RequestBody Map<String, String> vo) {
		return sS.getDetailShrplan(vo);
	}

	// http://아이피/shrplan
	// 게시글 작성 (post)
	@PostMapping("")
	public Map<String, Boolean> shrplanAdd(@RequestBody ShrplanDto shrplandto){
		return sS.saveShrplan(shrplandto);
	}
	

	// http://아이피/shrplan
	// 게시글 수정 (put)
	@PutMapping("")
	public Map<String, Boolean> shrplanUpdate(@RequestBody ShrplanDto shrplandto){
		return sS.updateShrplan(shrplandto);
	}

	// http://아이피/shrplan/{seq}
	// 게시글 삭제 (delete)
	@DeleteMapping("/{seq}")
	public Map<String, Boolean> shrplanDelete(@PathVariable Long seq){
		return sS.deleteShrplan(seq);
	}

	// http://아이피/shrplan/like
	// 게시글에 좋아요 (put)
	@PutMapping("/like")
	public Map<String, Boolean> shrplanLikeUpdate(@RequestBody Map<String, String> vo){
		return sS.updateShrplanLike(vo);
	}

	// http://아이피/shrplan/like
	// 좋아요 취소 (post)
	@PostMapping("/like")
	public Map<String, Boolean> shrplanLikeDelete(@RequestBody Map<String, String> vo){
		return sS.deleteShrplanLike(vo);
	}
	
	
	
	// http://아이피/shrplan/bmk
	// 게시글에 북마크 (put)
	@PutMapping("/bmk")
	public Map<String, Boolean> shrplanBmkUpdate(@RequestBody Map<String, String> vo){
		return sS.updateShrplanBmk(vo);
	}

	// http://아이피/shrplan/bmk
	// 북마크 취소 (post)
	@PostMapping("/bmk")
	public Map<String, Boolean> shrplanBmkDelete(@RequestBody Map<String, String> vo){
		return sS.deleteShrplanBmk(vo);
	}

	
	
	
	// http://아이피/shrplan/rpt
	// 게시글 신고 (put)
	@PutMapping("/rpt")
	public Map<String, String> shrplanReport(@RequestBody Map<String, String> vo){
		return sS.reportShrplan(vo);
	}

}
