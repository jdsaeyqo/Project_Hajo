package com.ssafy.hibernate.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hibernate.dto.HomeDto;
import com.ssafy.hibernate.service.HomeService;

@RestController
@RequestMapping("/home")
public class HomeController {
	@Autowired
	private HomeService hS;

	// http://아이피/home/{uid}
	// 홈화면에 나와야 하는 태스크 목록 조회 (post)
	@PostMapping("")
	public List<HomeDto> getHome(@RequestBody Map<String, String> vo){
		String uid = vo.get("uid");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		Calendar date = Calendar.getInstance();
		Date tempdate;
		try {
			tempdate = sdf.parse(vo.get("todayDate"));
			date.setTime(tempdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return hS.getHome(uid, date);

	}




}
