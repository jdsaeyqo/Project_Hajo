package com.ssafy.hibernate.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hibernate.dto.PlanUpdateDto;
import com.ssafy.hibernate.entity.PlanGrand;
import com.ssafy.hibernate.entity.PlanMid;
import com.ssafy.hibernate.service.PlanGrandService;
import com.ssafy.hibernate.service.PlanMidService;
import com.ssafy.hibernate.service.PlanSmallService;

@RestController
@RequestMapping("/planmids")
public class PlanMidController {
	@Autowired
	PlanGrandService pGS;

	@Autowired
	PlanMidService pMS;

	@Autowired
	PlanSmallService pSS;

//	@GetMapping("")
//	public List<PlanMid> getPlanMid(){
//		return pMS.findAll();
//	}

	// http://아이피/planmids
	// 중플랜 추가 (post)
	@PostMapping("")
	public PlanMid addPlanMid(@RequestBody Map<String, String> vo) {

		PlanMid planmid = new PlanMid();

		// 채워넣어야 할 것
		// 대플랜seq --> 대플랜
		// 중플랜 제목
		// 중플랜 설명
		// 중플랜 색
		Long tempSeq = Long.valueOf(vo.get("grandplanSeq"));
		planmid.setPlangrand(pGS.findbyId(tempSeq).get());
		planmid.setMidplanTitle(vo.get("midplanTitle"));
		planmid.setMidplanDesc(vo.get("midplanDesc"));
		planmid.setMidplanColor(vo.get("midplanColor"));

		// 중플랜 시작일
		// 중플랜 종료일
		Calendar startcalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA);
		Calendar endcalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		try {
			Long startdate = sdf.parse(vo.get("midplanStartdate")).getTime();
			Long enddate = sdf.parse(vo.get("midplanEnddate")).getTime();
			
			startcalendar.setTimeInMillis(startdate);
			endcalendar.setTimeInMillis(enddate);

			planmid.setMidplanStartdate(startcalendar);
			planmid.setMidplanEnddate(endcalendar);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 중플랜의 시작종료일 받아옴
		Calendar startdate = planmid.getMidplanStartdate();
		Calendar enddate = planmid.getMidplanEnddate();

		// 받아온 시작종료일을 날수로 표현
		long start = startdate.getTimeInMillis()/(24*60*60*1000);
		long end = enddate.getTimeInMillis()/(24*60*60*1000);

		// 그 차이를 계산해둠, 이후 소플랜 반복문의 조건이 됨
		int howlong = (int) (end-start)+1;

		// 중플랜 세팅하고 저장
		// 달성여부
		// 그 외의 것들 초기화
		planmid.setMidplanIsdone(false);
		planmid.setMidplanTtsplan(howlong);
		planmid.setMidplanTdsplan(0);
		pMS.save(planmid);

		// 소플랜도 추가하러 간당
		pSS.saveManySmallplan(planmid, howlong);

		// 대플랜 안의 중플랜 수를 갱신해준다
		PlanGrand plangrandTemp = planmid.getPlangrand();
		plangrandTemp.setGrandplanTtmplan(plangrandTemp.getGrandplanTtmplan()+1);
		pGS.updateById(planmid.getPlangrand().getGrandplanSeq(), plangrandTemp);

		return planmid;
	}

	// http://아이피/planmids
	// 중플랜 수정 (put)
	@PutMapping("")
	public int updatePlanMid(@RequestBody PlanUpdateDto updatedto) {
		//바꾸고자 하는 데이터를 불러온다
		return pMS.updateByDto(updatedto);
	}

	// http://아이피/planmids/{중플랜시퀀스}
	// 중플랜 삭제 (delete)
	@DeleteMapping("/{seq}")
	public int deletePlanMid(@PathVariable("seq") Long seq) {
		return pMS.deleteById(seq);
	}
}
