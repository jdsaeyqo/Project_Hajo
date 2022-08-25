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
import com.ssafy.hibernate.dto.BoastListDto;
import com.ssafy.hibernate.service.BoastService;

@RestController
@RequestMapping("/boast")
public class BoastController {

	@Autowired
	private BoastService bS;

	// http://아이피/boast/{uid}
	// 홈 화면에 나와야 하는 게시판 타입별 리스트 조회 (get)
	@GetMapping("/{uid}")
	public BoastListDto boastHome(@PathVariable String uid) {
		return bS.getHomeBoast(uid);
	}

	// http://아이피/boast/type
	// 게시판 타입(인기글, 최신글, 나의 어쩌구... 등)에 해당하는 글 전체 조회 (post)
	@PostMapping("/type")
	public List<BoastDto> boastType(@RequestBody Map<String, String> vo){
		return bS.getTypeBoast(vo);
	}

	// http://아이피/boast/detail
	// 게시판 시퀀스에 해당하는 글 보여주기 (post)
	@PostMapping("/detail")
	public BoastDto boastDetail(@RequestBody Map<String, String> vo) {
		return bS.getDetailBoast(vo);
	}

	// http://아이피/boast
	// 게시글 작성 (post)
	@PostMapping("")
	public Map<String, Boolean> boastAdd(@RequestBody BoastDto boastdto){
		return bS.saveBoast(boastdto);
	}

	// http://아이피/boast
	// 게시글 수정 (put)
	@PutMapping("")
	public Map<String, Boolean> boastUpdate(@RequestBody BoastDto boastdto){
		return bS.updateBoast(boastdto);
	}

	// http://아이피/boast
	// 게시글 삭제 (delete)
	@DeleteMapping("/{seq}")
	public Map<String, Boolean> boastDelete(@PathVariable Long seq){
		return bS.deleteBoast(seq);
	}

	// http://아이피/boast/like
	// 게시글에 좋아요 (put)
	@PutMapping("/like")
	public Map<String, Boolean> boastLikeUpdate(@RequestBody Map<String, String> vo){
		return bS.updateBoastLike(vo);
	}

	// http://아이피/boast/like
	// 좋아요 취소 (post)
	@PostMapping("/like")
	public Map<String, Boolean> boastLikeDelete(@RequestBody Map<String, String> vo){
		return bS.deleteBoastLike(vo);
	}
	
	// http://아이피/boast/rpt
	// 게시글 신고 (put)
	@PutMapping("/rpt")
	public Map<String, String> boastReport(@RequestBody Map<String, String> vo){
		return bS.reportBoast(vo);
	}

}
