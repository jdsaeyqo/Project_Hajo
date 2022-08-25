package com.ssafy.hibernate.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.PlanSmallRepository;
import com.ssafy.hibernate.dto.HomeDto;
import com.ssafy.hibernate.entity.PlanGrand;
import com.ssafy.hibernate.entity.PlanMid;

@Service
public class HomeServiceImpl implements HomeService{
	@Autowired
	private PlanSmallRepository pSR;

	@Autowired
	private PlanTaskService pTS;

	@Override
	public List<HomeDto> getHome(String uid, Calendar date) {
		List<HomeDto> homedto = pSR.findByPlanmid_Plangrand_User_UserUidAndSmallplanDate(uid, date).stream().map(
				m->{
					HomeDto dto = new HomeDto();
					if(!pTS.findForHome(m.getSmallplanSeq()).isEmpty()) {
						PlanGrand grand = m.getPlanmid().getPlangrand();
						PlanMid mid = m.getPlanmid();
						dto.setGrandplanSeq(grand.getGrandplanSeq());
						dto.setGrandplanTitle(grand.getGrandplanTitle());
						dto.setIsMatch(grand.getGrandplanIsmatch());
						dto.setMidplanSeq(mid.getMidplanSeq());
						dto.setMidplanTitle(mid.getMidplanTitle());
						dto.setSmallplanSeq(m.getSmallplanSeq());
						dto.setSubDto(pTS.findForHome(m.getSmallplanSeq()));
						return dto;
					}
					return null;
				}
				).collect(Collectors.toList());

		homedto.removeAll(Collections.singletonList(null));

		return homedto;
	}

}
