package com.ssafy.hibernate.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.MatchNowRepository;
import com.ssafy.hibernate.Repository.PlanGrandRepository;
import com.ssafy.hibernate.Repository.PlanMidRepository;
import com.ssafy.hibernate.Repository.PlanSmallRepository;
import com.ssafy.hibernate.Repository.PlanTaskRepository;
import com.ssafy.hibernate.Repository.UserRepository;
import com.ssafy.hibernate.dto.HomeSubDto;
import com.ssafy.hibernate.dto.PlanTaskBydate;
import com.ssafy.hibernate.dto.PlanTaskDto;
import com.ssafy.hibernate.entity.MatchNow;
import com.ssafy.hibernate.entity.PlanGrand;
import com.ssafy.hibernate.entity.PlanMid;
import com.ssafy.hibernate.entity.PlanSmall;
import com.ssafy.hibernate.entity.PlanTask;
import com.ssafy.hibernate.entity.User;

@Service
public class PlanTaskServiceImpl implements PlanTaskService {
	@Autowired
	private PlanGrandRepository pGR;

	@Autowired
	private PlanMidRepository pMR;

	@Autowired
	private PlanSmallRepository pSR;

	@Autowired
	private PlanTaskRepository pTR;

	@Autowired
	private UserRepository uR;

	@Autowired
	private MatchNowRepository mnR;

	@Override
	public List<PlanTask> findAll() {
		List<PlanTask> plantasks = new ArrayList<>();
		pTR.findAll().forEach(e -> plantasks.add(e));
		return plantasks;
	}

	@Override
	public PlanTaskDto findbyId(Long taskSeq) {
		Optional<PlanTask> plantask = pTR.findById(taskSeq);

		PlanTaskDto plantaskdto = new PlanTaskDto();
		plantaskdto.setTaskSeq(plantask.get().getTaskSeq());
		plantaskdto.setPlansmallSeq(plantask.get().getPlansmall().getSmallplanSeq());
		plantaskdto.setTitle(plantask.get().getTaskTitle());
		plantaskdto.setIsDone(plantask.get().getTaskIsdone());
		plantaskdto.setMemo(plantask.get().getTaskMemo());
		plantaskdto.setImg(plantask.get().getTaskImg());
		plantaskdto.setAlramtime(plantask.get().getTaskAlarmtime());

		return plantaskdto;
	}

	@Override
	public int deleteById(Long taskSeq) {
		Optional<PlanTask> task = pTR.findById(taskSeq);

		if (task.isPresent()) {
			if (task.get().getPlansmall().getPlanmid().getPlangrand().getGrandplanIsmatch()) {
				// 이 태스크가 속한 대플랜이 매칭 중이라 삭제 불가
				return 1;
			} /*
				 * else if (task.get().getTaskIsdone()) { // 이 태스크가 완료 상태이므로 삭제 불가 return 2; }
				 */ else {
				pTR.deleteById(taskSeq);

				User user = task.get().getPlansmall().getPlanmid().getPlangrand().getUser();
				user.setUserTttask(user.getUserTttask() - 1);
				uR.save(user);

				return 0;
			}
		} else {
			return 3;
		}
	}

	@Override
	public int save(PlanTaskDto plantaskdto, PlanSmall plansmall) {
		if (plansmall.getPlanmid().getPlangrand().getGrandplanIsmatch()) {
			// 매칭 중이라 태스크 추가를 못해
			return 1;
		} /*
			 * else if (plansmall.getSmallplanIsdone()) { // 그 소플랜 완료 상태라 태스크 추가 못해 return
			 * 2; }
			 */else {
			PlanTask plantask = new PlanTask();

			plantask.setPlansmall(plansmall);
			plantask.setTaskTitle(plantaskdto.getTitle());
			plantask.setTaskIsdone(false);
			plantask.setTaskMemo(plantaskdto.getMemo());
			plantask.setTaskImg(plantaskdto.getImg());
			plantask.setTaskAlarmtime(plantaskdto.getAlramtime());

			pTR.save(plantask);

			User user = plansmall.getPlanmid().getPlangrand().getUser();
			user.setUserTttask(user.getUserTttask() + 1);
			
			if(plansmall.getSmallplanDate().equals(Calendar.getInstance())) {
				user.setUserDltask(user.getUserDltask() + 1);
			}
			
			uR.save(user);

			return 0;
		}
	}

	@Override
	public int updateById(Long taskSeq, PlanTask plantask) {

		/*
		 * if(plantask.getPlansmall().getPlanmid().getPlangrand().getGrandplanIsmatch())
		 * { // 이 태스크가 속한 대플랜이 매칭 중이라서 업데이트 불가 return 1; } else if
		 * (plantask.getTaskIsdone()) { // 이 태스크가 완료 상태라서 업데이트 불가 return 2; } else {
		 */// 제약 조건에 위배되지 않으므로 업데이트 가능
		pTR.saveAndFlush(plantask);
		return 0;
		/* } */

	}

	@Override
	public List<PlanTaskDto> findBySmallplan(Long seq) {
		List<PlanTaskDto> plantaskdto = pTR.findByPlansmall_SmallplanSeq(seq).stream().map(m -> {
			PlanTaskDto dto = new PlanTaskDto();
			dto.setTaskSeq(m.getTaskSeq());
			dto.setPlansmallSeq(m.getPlansmall().getSmallplanSeq());
			dto.setTitle(m.getTaskTitle());
			dto.setIsDone(m.getTaskIsdone());
			dto.setMemo(m.getTaskMemo());
			dto.setImg(m.getTaskImg());
			dto.setAlramtime(m.getTaskAlarmtime());

			return dto;
		}).collect(Collectors.toList());

		return plantaskdto;
	}

	@Override
	public List<HomeSubDto> findForHome(Long seq) {
		List<HomeSubDto> homesubdto = pTR.findByPlansmall_SmallplanSeq(seq).stream().map(m -> {
			HomeSubDto dto = new HomeSubDto();
			dto.setTaskSeq(m.getTaskSeq());
			dto.setTaskTitle(m.getTaskTitle());
			dto.setIsDone(m.getTaskIsdone());
			return dto;
		}).collect(Collectors.toList());
		return homesubdto;
	}

	@Override
	public String checkPlanTask(Long seq) {
		// 태스크 isdone 업데이트
		PlanTask plantask = pTR.findById(seq).get();
		plantask.setTaskIsdone(true);
		pTR.save(plantask);
		System.out.println("태스크isdone");

		// 소플랜 TDT 변경
		PlanSmall plansmall = plantask.getPlansmall();
		int TDT = plansmall.getSmallplanTdtask() + 1;
		plansmall.setSmallplanTdtask(TDT);
		System.out.println("소플랜 TDT");

		// 유저 테이블의 TDT, DDT 업데이트 필요
		User user = plansmall.getPlanmid().getPlangrand().getUser();
		user.setUserTdtask(user.getUserTdtask() + 1);
		user.setUserDdtask(user.getUserDdtask() + 1);
		uR.save(user);
		System.out.println("유저 DDT TDT");

		// 소플랜 TTT와 TDT 비교, 소플랜 완료 체크
		if (TDT >= plansmall.getSmallplanTttask()) {
			// TDT가 TTT보다 크거나 같으면, 이 소플랜은 모두 달성된 것
			// 소플랜 isdone 업데이트
			plansmall.setSmallplanIsdone(true);
			pSR.save(plansmall);
			System.out.println("소플랜 이즈던");

			// 중플랜 TDS 변경
			PlanMid planmid = plansmall.getPlanmid();
			int TDS = planmid.getMidplanTdsplan() + 1;
			planmid.setMidplanTdsplan(TDS);
			System.out.println("중플랜 TDS");

			// 매치나우 테이블의 플랜 스테이터스 바꿔주기
			// 내 상대의 매치나우 테이블의 매치 플랜 스테이터스 바꿔주기
			Long grandplanSeq = planmid.getPlangrand().getGrandplanSeq();
			if(mnR.existsByPlanGrand_GrandplanSeq(grandplanSeq)) {
				MatchNow myMatchnow = mnR.findByUser_UserUidAndPlanGrand_GrandplanSeq(user.getUserUid(),
						planmid.getPlangrand().getGrandplanSeq()).get();
				myMatchnow.setPlanStatus("O");
				mnR.save(myMatchnow);
				MatchNow matchnow = mnR.findByMatchUser_UserUidAndMatchPlanGrand_GrandplanSeq(user.getUserUid(),
						planmid.getPlangrand().getGrandplanSeq()).get();
				matchnow.setMatchPlanStatus("O");
				mnR.save(matchnow);
			}

			// 중플랜 TTS와 TDS 비교, 중플랜 완료 체크
			if (TDS >= planmid.getMidplanTtsplan()) {
				// TDS가 TTS보다 크거나 같으면, 이 중플랜은 모두 달성된 것
				// 소플랜 TDS, isdone 업데이트
				planmid.setMidplanIsdone(true);
				pMR.save(planmid);

				// 대플랜 TDM 변경
				PlanGrand plangrand = planmid.getPlangrand();
				int TDM = plangrand.getGrandplanTdmplan() + 1;
				plangrand.setGrandplanTdmplan(TDM);

				// 대플랜 TDM과 TTM 비교, 대플랜 완료 체크
				if (TDM >= plangrand.getGrandplanTtmplan()) {
					// TDM이 TTM보다 크거나 같으면, 이 대플랜은 모두 달성된 것
					// 대플랜 TDM, isdone 업데이트
					plangrand.setGrandplanIsdone(true);
					pGR.save(plangrand);
					return "태스크, 소플랜, 중플랜, 대플랜 완료 확인";

				} else {
					// 대플랜 TDM 업데이트
					pGR.save(plangrand);
					return "태스크, 소플랜, 중플랜 완료 확인";
				}

			} else {
				// 중플랜 TDS 업데이트
				pMR.save(planmid);
				return "태스크, 소플랜 완료 확인";
			}

		} else {
			// 소플랜 TDT 업데이트
			pSR.save(plansmall);
			return "태스크 완료 확인";
		}

	}

	@Override
	public PlanTaskBydate getPlanBydate(Map<String, String> vo) {
		Long grandSeq = Long.valueOf(vo.get("grandPlanSeq"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

		try {
			Date tempdate = sdf.parse(vo.get("date"));
			Calendar date = Calendar.getInstance();
			date.setTime(tempdate);

			PlanSmall small = pSR.findByPlanmid_Plangrand_GrandplanSeqAndSmallplanDate(grandSeq, date).get();

			Long smallSeq = small.getSmallplanSeq();

			List<String> tasknames = pTR.findByPlansmall_SmallplanSeqAndTaskIsdoneTrue(smallSeq).stream().map(m -> {
				return m.getTaskTitle();
			}).collect(Collectors.toList());

			PlanTaskBydate plantask = new PlanTaskBydate();
			plantask.setGrandPlanName(small.getPlanmid().getPlangrand().getGrandplanTitle());
			plantask.setMidPlanName(small.getPlanmid().getMidplanTitle());
			plantask.setTasks(tasknames);

			return plantask;

		} catch (ParseException e1) {
			e1.printStackTrace();
			return null;
		}

	}

}
