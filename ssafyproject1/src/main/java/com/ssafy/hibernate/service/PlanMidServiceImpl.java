package com.ssafy.hibernate.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.PlanGrandRepository;
import com.ssafy.hibernate.Repository.PlanMidRepository;
import com.ssafy.hibernate.dto.PlanUpdateDto;
import com.ssafy.hibernate.entity.PlanGrand;
import com.ssafy.hibernate.entity.PlanMid;

@Service
public class PlanMidServiceImpl implements PlanMidService {
	@Autowired
	private PlanMidRepository pMR;

	@Autowired
	private PlanGrandRepository pGR;
	
	@Autowired
	EntityManager em;

	// 중플랜 전체 찾기
	@Override
	public List<PlanMid> findAll() {
		List<PlanMid> planmids = new ArrayList<>();
		pMR.findAll().forEach(e->planmids.add(e));
		return planmids;
	}

	// 중플랜 아이디로 중플랜 조회하기
	@Override
	public Optional<PlanMid> findbyId(Long midplanSeq) {
		Optional<PlanMid> planmid = pMR.findById(midplanSeq);
		return planmid;
	}

	// 대플랜 아이디로 해당하는 중플랜 목록 조회하기
	@Override
	public List<Long> findbyPlangrand_GrandplanSeq(Long seq){

		// 보낸 대플랜 아이디에 따른 중플랜 목록을 뽑아옴
		List<PlanMid> planmid = pMR.findByPlangrand_GrandplanSeq(seq);

		// 이제 그 중플랜 목록에서 중플랜 아이디만 뽑아서 저장함
		List<Long> planmidSeq = new ArrayList<>();

		for(PlanMid temp:planmid) {
			planmidSeq.add(temp.getMidplanSeq());
		}

		// 중플랜 아이디만 저장되어 있는 거 반환함
		return planmidSeq;

	}

	@Override
	public int deleteById(Long midplanSeq) {
		Optional<PlanMid> midplan = pMR.findById(midplanSeq);

		if(midplan.isPresent()) {
			/*
			 * if(midplan.get().getPlangrand().getGrandplanIsmatch()) { // 이 중플랜이 속한 대플랜이 매칭
			 * 중이므로 삭제 불가 return 1; } else
			 *//*
				 * if(midplan.get().getMidplanIsdone()) { // 이 중플랜이 완료 상태이므로 삭제 불가 return 2; }
				 * else {
				 */
				// 해당 사항 없으므로 삭제 가능
				pMR.deleteById(midplanSeq);
				return 0;
				/* } */
		} else {
			return 3;
		}
	}

	// 중플랜 추가
	@Override
	public PlanMid save(PlanMid planmid) {
		//planmid에 있는 grandplanSeq로 plangrand 정보를 받아와서 채운다
		Optional<PlanGrand> plangrand = pGR.findById(planmid.getPlangrand().getGrandplanSeq());

		//가져온 plangrand 정보를 PlanMid 테이블에 채운다
		planmid.setPlangrand(plangrand.get());
		
		//다 만들었으니까 등록한다
		pMR.saveAndFlush(planmid);
		
		em.clear();
		
		return planmid;
	}

	// 중플랜 일반수정
	@Override
	public void updateById(Long midplanSeq, PlanMid planmid) {
		Optional<PlanMid> e = pMR.findById(midplanSeq);

		if(e.isPresent()) {
			e.get().setMidplanTitle(planmid.getMidplanTitle());
			e.get().setMidplanDesc(planmid.getMidplanDesc());
			pMR.save(planmid);
		}

	}

	// 중플랜 사용자 요청에 따라 수정
	@Override
	public int updateByDto(PlanUpdateDto updatedto) {
		Optional<PlanMid> e = pMR.findById(updatedto.getPlanSeq());

		if(e.isPresent()) {
			/*
			 * if(e.get().getPlangrand().getGrandplanIsmatch()) { // 이 중플랜이 속한 대플랜이 매칭 중이라서
			 * 안됨 return 1; } else
			 *//*if(e.get().getMidplanIsdone()) {
				// 이 중플랜이 완료되어 있어서 안됨
				return 2;
			} else {*/
				// 제약조건이 없으므로 가능
				switch(updatedto.getUpdateType()) {
				case "title":
					e.get().setMidplanTitle(updatedto.getUpdateContent()); break;
				case "desc":
					e.get().setMidplanDesc(updatedto.getUpdateContent()); break;
				case "startdate":
					Calendar start = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA);
					SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
					try {
						Date date = sdfStart.parse(updatedto.getUpdateContent());
						start.setTime(date);
						e.get().setMidplanStartdate(start);
					} catch (ParseException e1) {
						e1.printStackTrace();
					} break;
				case "enddate":
					Calendar end = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA);
					SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
					try {
						Date date = sdfEnd.parse(updatedto.getUpdateContent());
						end.setTime(date);
						e.get().setMidplanEnddate(end);
					} catch (ParseException e1) {
						e1.printStackTrace();
					} break;
				case "color":
					try {
						e.get().setMidplanColor(updatedto.getUpdateContent()); break;
					} catch(NumberFormatException e1) {
						e1.printStackTrace();
					}
				}
				pMR.save(e.get());
				return 0;
				/* } */
		} else {
			return 3;
		}

	}

}
