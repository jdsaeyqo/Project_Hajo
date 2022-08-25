package com.ssafy.hibernate.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.PlanSmallRepository;
import com.ssafy.hibernate.dto.PlanSmallBydate;
import com.ssafy.hibernate.dto.PlanSmallDto;
import com.ssafy.hibernate.entity.PlanMid;
import com.ssafy.hibernate.entity.PlanSmall;

@Service
public class PlanSmallServiceImpl implements PlanSmallService{
	@Autowired
	private PlanSmallRepository pSR;

	@Autowired
	private PlanTaskService pTS;

	@Autowired
	EntityManager em;

	@Override
	public List<PlanSmall> findAll() {
		List<PlanSmall> smallplans = new ArrayList<>();
		pSR.findAll().forEach(e->smallplans.add(e));
		return smallplans;
	}

	@Override
	public Optional<PlanSmall> findbyId(Long smallplanSeq) {
		Optional<PlanSmall> smallplan = pSR.findById(smallplanSeq);
		return smallplan;
	}

	@Override
	public void deleteById(Long smallplanSeq) {
		pSR.deleteById(smallplanSeq);
	}

	@Override
	@Transactional
	public PlanSmall save(PlanSmall plansmall) {
		pSR.save(plansmall);
		return plansmall;
	}

	@Override
	@Transactional
	public List<PlanSmall> saveAll(List<PlanSmall> plansmalls) {
		pSR.saveAll(plansmalls);
		return plansmalls;
	}

	// 수정 필요함 바보야
	@Override
	public void updateById(Long smallplanSeq, PlanSmall plansmall) {
		Optional<PlanSmall> e = pSR.findById(smallplanSeq);

		if(e.isPresent()) {
			pSR.save(plansmall);
		}

	}

	// 일자별로 데이터를 조회
	@Override
	public PlanSmallDto getPlanSmall(Long seq) {
		PlanSmallDto dto = new PlanSmallDto();

		Optional<PlanSmall> plansmall = pSR.findById(seq);

		dto.setSmallplanSeq(seq);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

		dto.setSmallplanDate(sdf.format(plansmall.get().getSmallplanDate().getTimeInMillis()));

		dto.setMidplanSeq(plansmall.get().getPlanmid().getMidplanSeq());
		dto.setMidplanTitle(plansmall.get().getPlanmid().getMidplanTitle());
		dto.setMidplanDesc(plansmall.get().getPlanmid().getMidplanDesc());

		dto.setMidplanStart(sdf.format(plansmall.get().getPlanmid().getMidplanStartdate().getTimeInMillis()));
		dto.setMidplanEnd(sdf.format(plansmall.get().getPlanmid().getMidplanEnddate().getTimeInMillis()));

		dto.setMidplanColor(plansmall.get().getPlanmid().getMidplanColor());
		dto.setGrandplanSeq(plansmall.get().getPlanmid().getPlangrand().getGrandplanSeq());
		dto.setPlantaskList(pTS.findBySmallplan(seq));

		return dto;
	}

	@Override
	@Transactional
	public void saveManySmallplan(PlanMid planmid, int howlong) {

		for(int i=0;i<howlong;i++) {

			// 소플랜 객체 만들기
			PlanSmall small = new PlanSmall();
			Calendar tempdate = planmid.getMidplanStartdate();

			small.setPlanmid(planmid);

			small.setSmallplanIsdone(false);
			small.setSmallplanTdtask(0);
			small.setSmallplanTttask(0);
			small.setSmallplanDate(tempdate);
			
			em.persist(small);
			em.clear();
			
			tempdate.add(Calendar.DATE, 1);

//			smalls.add(small);
			
			// save를 대신해서 persist랑 clear를 사용해서 성공~
			// 추가적인 업데이트구문이 발생하지 않았다.

		}

	}

	@Override
	public char Isdone(Long seq, Calendar date) {

		Optional<PlanSmall> small = pSR.findByPlanmid_Plangrand_GrandplanSeqAndSmallplanDate(seq, date);

		if(small.isPresent()) {
			if(small.get().getSmallplanIsdone()) {
				// 이날 치를 달성 했다는 뜻!
				return 'O';
			} else if(small.get().getSmallplanTttask()==0) {
				// 이날 치가 달성되지 않았고, 속한 태스크의 수가 0
				// 즉, 이날은 태스크가 없던 날
				return '-';
			} else {
				// 이날 치가 달성되지 않았고, 속한 태스크의 수도 0이 아님
				// 즉, 이날은 달성 실패
				return 'X';
			}
		} else {
			// 어쩐지 조회되지 않았다. 에러를 뜻하는 e 반환
			return 'e';
		}
	}

	@Override
	public List<PlanSmallBydate> getPlanBydate(Map<String, String> vo) {
		
		Long grandSeq = Long.valueOf(vo.get("grandPlanSeq"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		try {
			
			Calendar start = Calendar.getInstance();
			Calendar end = Calendar.getInstance();
			
			Date tempstart = sdf.parse(vo.get("startDate"));
			Date tempend = sdf.parse(vo.get("endDate"));
			
			start.setTime(tempstart);
			end.setTime(tempend);
			
			List<PlanSmallBydate> smalls = pSR.findByPlanmid_Plangrand_GrandplanSeqAndSmallplanDateBetween(grandSeq, start, end).stream().map(
					m->{
						
						PlanSmallBydate small = new PlanSmallBydate();
						small.setDate(sdf.format(m.getSmallplanDate().getTimeInMillis()));
						small.setCounts(m.getSmallplanTdtask());
						return small;
						}
					).collect(Collectors.toList());
			
			return smalls;
		} catch (ParseException e1) {
			e1.printStackTrace();
			return null;
		}
	}

}
