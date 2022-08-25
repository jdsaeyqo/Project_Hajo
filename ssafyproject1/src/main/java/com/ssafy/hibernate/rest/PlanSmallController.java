package com.ssafy.hibernate.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hibernate.dto.PlanSmallBydate;
import com.ssafy.hibernate.dto.PlanSmallDto;
import com.ssafy.hibernate.service.PlanSmallService;

@RestController
@RequestMapping("/plansmalls")
public class PlanSmallController {

	@Autowired
	PlanSmallService pSS;

	// http://아이피/plansmalls/{소플랜시퀀스}
	// 해당 시퀀스에 해당하는 소플랜의 중플랜, 태스크 정보를 조회
	@GetMapping("/{seq}")
	public PlanSmallDto getPlanSmall(@PathVariable("seq") Long seq) {
		return pSS.getPlanSmall(seq);
	}
	
	// http://아이피/plansmalls/bydate
	// 특정 기간과 대플랜 시퀀스를 가지고 모든 날짜에 대한 완료된 태스크 수 반환
	@PostMapping("/bydate")
	public List<PlanSmallBydate> getPlanBydate(@RequestBody Map<String, String> vo) {
		return pSS.getPlanBydate(vo);
	}

//	@PostMapping("/check/{seq}")
//	public char Isdone(@RequestBody Map<String, String> vo) {
//
//		Long seq = Long.valueOf(vo.get("grandplanSeq"));
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
//
//		Calendar date = Calendar.getInstance();
//		Date tempdate;
//		try {
//			tempdate = sdf.parse(vo.get("todayDate"));
//			date.setTime(tempdate);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//		return pSS.Isdone(seq, date);
//	}

}
