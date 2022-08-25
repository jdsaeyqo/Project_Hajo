package com.ssafy.hibernate.rest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hibernate.dto.MatchNowDto;
import com.ssafy.hibernate.dto.MatchPbchallDto;
import com.ssafy.hibernate.dto.MatchPschallDto;
import com.ssafy.hibernate.dto.PlanListDto;
import com.ssafy.hibernate.dto.UserMatchDTO;
import com.ssafy.hibernate.dto.UserRankDto;
import com.ssafy.hibernate.entity.MatchNow;
import com.ssafy.hibernate.entity.MatchPbchall;
import com.ssafy.hibernate.entity.MatchPschall;
import com.ssafy.hibernate.entity.PlanGrand;
import com.ssafy.hibernate.entity.User;
import com.ssafy.hibernate.entity.UserHistory;
import com.ssafy.hibernate.service.MatchNowService;
import com.ssafy.hibernate.service.MatchPbchallService;
import com.ssafy.hibernate.service.MatchPschallService;
import com.ssafy.hibernate.service.PlanGrandService;
import com.ssafy.hibernate.service.UserHistoryService;
import com.ssafy.hibernate.service.UserService;

/*전체적으로 jwt payload를 활용해서 uid 처리하는부분이 있어야한다....*/

@RestController
@RequestMapping("/match")
public class MatchController {

	// 유저가 받은 챌린지 관련 기능이 있는 matchPschallService(pschall = personal challenge)
	@Autowired
	MatchPschallService matchPschallService;

	// 유저가 올린 챌린지 관련 기능이 있는 matchPbchallService(pbchall = public challenge)
	@Autowired
	MatchPbchallService matchPbchallService;

	@Autowired
	UserService userService;

	@Autowired
	PlanGrandService planGrandService;

	@Autowired
	MatchNowService matchNowService;

	@Autowired
	EntityManager em;

	@Autowired
	UserHistoryService userHistoryService;

	// 다른 사람들이 올린 도전장 리스트를 보는 기능.
	@GetMapping("/pbchall")
	public List<MatchPbchallDto> getMatchPbchalls() {
		return matchPbchallService.findAll();
	}

	// 특정 사람이 올린 글 리스트를 보는 기능.
	@GetMapping("/pbchall/{uid}")
	public List<MatchPbchallDto> getMatchPbchallsByUser_UserUid(@PathVariable("uid") String uid) {
		return matchPbchallService.findByUser_UserUid(uid);
	}

	// 다른사람들이 볼 수 있는 도전장 등록
	@PostMapping("/pbchall")
	public ResponseEntity<Map<String, Object>> postMatchPbchalls(@RequestBody Map<String, Object> vo) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String userUid = vo.get("userUid").toString();
		Long grandplanSeq = Long.valueOf(vo.get("grandplanSeq").toString());
		int matchPeriod = Integer.parseInt(vo.get("matchPeriod").toString());

		User user = userService.findById(userUid).get();
		PlanGrand planGrand = planGrandService.findbyId(grandplanSeq).get();

		if (user.getUserUid().equals(planGrand.getUser().getUserUid()) && !planGrand.getGrandplanIsmatch()) {
			// 대플랜 ismatch true로 바꿔주고
			planGrand.setGrandplanIsmatch(true);
			planGrandService.save(planGrand);

			MatchPbchall matchPbchall = new MatchPbchall();
			matchPbchall.setUser(user);
			matchPbchall.setPlanGrand(planGrand);
			matchPbchall.setMatchPeriod(matchPeriod);

			matchPbchallService.save(matchPbchall);
			resultMap.put("message", "success");
			status = HttpStatus.OK;
		} else {
			resultMap.put("message", "fail");
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(resultMap, status);

	}
	
	//도전장 받아들여서 경쟁전에 들어가는 acceptMatchPschalls 
		@PostMapping("/pbchall/accept")
		public ResponseEntity<Map<String, Object>> acceptMatchPbchalls(@RequestBody Map<String, Object> vo){
			Map<String, Object> resultMap = new HashMap<>();
			HttpStatus status = null;
			Long pbchallSeq = Long.valueOf(vo.get("pbchallSeq").toString());
			String matchUserUid = vo.get("matchUserUid").toString();
			Long matchPlanGrandSeq = Long.valueOf(vo.get("matchPlanGrandSeq").toString());
			Optional<MatchPbchall> o = matchPbchallService.findByPbchallSeq(pbchallSeq);
			Optional<User> u = userService.findById(matchUserUid);
			Optional<PlanGrand> p = planGrandService.findbyId(matchPlanGrandSeq);
			if(o.isPresent() && u.isPresent() && p.isPresent()) {
				MatchPbchall matchPbchall = o.get();
				User matchUser = u.get();
				PlanGrand matchPlanGrand = p.get();
				Calendar startdate = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
				Calendar enddate = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
				startdate.add(Calendar.DATE, 1);
				enddate.add(Calendar.DATE, matchPbchall.getMatchPeriod()+1);
				
				MatchNow matchNow = new MatchNow();
				matchNow.setUser(matchPbchall.getUser());
				matchNow.setPlanGrand(matchPbchall.getPlanGrand());
				matchNow.setPlanStatus("-");
				matchNow.setMatchUser(matchUser);
				matchNow.setMatchPlanGrand(matchPlanGrand);
				matchNow.setMatchPlanStatus("-");
				matchNow.setMatchStartdate(startdate);
				matchNow.setMatchEnddate(enddate);
				matchNow.setMatchResult("-");
				matchNow.setUserProgress("");
				matchNow.setMatchUserProgress("");
				
				
				MatchNow matchOpponent = new MatchNow();
				matchOpponent.setUser(matchUser);
				matchOpponent.setPlanGrand(matchPlanGrand);
				matchOpponent.setPlanStatus("-");
				matchOpponent.setMatchUser(matchPbchall.getUser());
				matchOpponent.setMatchPlanGrand(matchPbchall.getPlanGrand());
				matchOpponent.setMatchPlanStatus("-");
				matchOpponent.setMatchStartdate(startdate);
				matchOpponent.setMatchEnddate(enddate);
				matchOpponent.setMatchResult("-");
				matchOpponent.setUserProgress("");
				matchOpponent.setMatchUserProgress("");
				
				
				matchPlanGrand.setGrandplanIsmatch(true);
				planGrandService.save(matchPlanGrand);
				em.clear();
				
				PlanGrand planGrand = matchPbchall.getPlanGrand();
				planGrand.setGrandplanIsmatch(true);
				planGrandService.save(planGrand);
				em.clear();
				
				matchNowService.save(matchNow);
				em.clear();
				matchNowService.save(matchOpponent);
				em.clear();
				
				//매칭 넘어갔으니 pschall 삭제
				System.out.println("매칭 삭제 : " + pbchallSeq);
				matchPbchallService.deleteById(pbchallSeq);
				
				resultMap.put("message", "success");
				status = HttpStatus.OK;
			} else {
				System.out.println("오류 발생!!");
				resultMap.put("message", "fail");
				status = HttpStatus.BAD_REQUEST;
			}
			
			return new ResponseEntity<>(resultMap, status);
		}

	// 자기가 올린 도전장 삭제. jwt활용해서 수정해야함
	@DeleteMapping("/pbchall/{grandplanSeq}")
	public ResponseEntity<Map<String, Object>> deleteMatchPbchalls(@PathVariable("grandplanSeq") Long grandplanSeq) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
		matchPbchallService.deleteByPlanGrand_GrandplanSeq(grandplanSeq);
		em.clear();
		PlanGrand p = planGrandService.findbyId(grandplanSeq).get();
		p.setGrandplanIsmatch(false);
		planGrandService.save(p);
		em.clear();
		
		resultMap.put("message", "success");
		status = HttpStatus.OK;
		}
		catch(Exception e) {
			e.printStackTrace();
			resultMap.put("message", "fail");
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<>(resultMap, status);

	}

	// 특정 유저가 받은 도전장 리스트를 보는 기능.
	@GetMapping("/pschall/{uid}")
	public List<MatchPschallDto> getMatchPschalls(@PathVariable("uid") String uid) {
		return matchPschallService.findByUser_UserUid(uid);
	}

	// 내가 쓴 도전장 리스트를 보는 기능.
	@GetMapping("/pschall/send/{uid}")
	public List<MatchPschallDto> getMyMatchPschalls(@PathVariable("uid") String uid) {
		return matchPschallService.findByMatchUser_UserUid(uid);
	}

	// 도전장 생성. 다만 유효성 검증이 필요해보인다. grandplanSeq주인이 userUid맞는지?같은
	@PostMapping("/pschall")
	public ResponseEntity<Map<String, Object>> postMatchPschalls(@RequestBody Map<String, Object> vo) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String userUid = vo.get("userUid").toString();
		Long grandplanSeq = Long.valueOf(vo.get("grandplanSeq").toString());
		String matchUserUid = vo.get("matchUserUid").toString();
		Long matchGrandplanSeq = Long.valueOf(vo.get("matchGrandplanSeq").toString());
		int matchPeriod = Integer.parseInt(vo.get("matchPeriod").toString());

		User user = userService.findById(userUid).get();
		User matchUser = userService.findById(matchUserUid).get();
		PlanGrand planGrand = planGrandService.findbyId(grandplanSeq).get();
		PlanGrand matchPlanGrand = planGrandService.findbyId(matchGrandplanSeq).get();

		// planGrand가 user 소유가 맞으면서, 선택한 플랜이 매칭중이지 않고, userUid에 해당되는 유저에게 상대가 도전장을 보낸것이
		// 없을 경우
		if (user.getUserUid().equals(planGrand.getUser().getUserUid()) && !planGrand.getGrandplanIsmatch()
				&& matchUser.getUserUid().equals(matchPlanGrand.getUser().getUserUid())
				&& !matchPlanGrand.getGrandplanIsmatch()
				&& !matchPschallService.findByUser_UserUidAndMatchUser_UserUid(userUid, matchUserUid).isPresent()) {

			MatchPschall matchPschall = new MatchPschall();
			matchPschall.setUser(user);
			matchPschall.setMatchUser(matchUser);
			matchPschall.setPlanGrand(planGrand);
			matchPschall.setMatchPlanGrand(matchPlanGrand);
			matchPschall.setMatchPeriod(matchPeriod);

			matchPschallService.save(matchPschall);
			resultMap.put("message", "success");
			status = HttpStatus.OK;
		} else {
			System.out.println("오류 발생!!");
			resultMap.put("message", "fail");
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(resultMap, status);
	}
	
	//도전장 받아들여서 경쟁전에 들어가는 acceptMatchPschalls 
	@PostMapping("/pschall/accept")
	public ResponseEntity<Map<String, Object>> acceptMatchPschalls(@RequestBody Map<String, Object> vo){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		Long pschallSeq = Long.valueOf(vo.get("pschallSeq").toString());
		Optional<MatchPschall> o = matchPschallService.findByPschallSeq(pschallSeq);
		if(o.isPresent()) {
			MatchPschall matchPschall = o.get();
			Calendar startdate = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
			Calendar enddate = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
			startdate.add(Calendar.DATE, 1);
			enddate.add(Calendar.DATE, matchPschall.getMatchPeriod()+1);
			
			MatchNow matchNow = new MatchNow();
			matchNow.setUser(matchPschall.getUser());
			matchNow.setPlanGrand(matchPschall.getPlanGrand());
			matchNow.setPlanStatus("-");
			matchNow.setMatchUser(matchPschall.getMatchUser());
			matchNow.setMatchPlanGrand(matchPschall.getMatchPlanGrand());
			matchNow.setMatchPlanStatus("-");
			matchNow.setMatchStartdate(startdate);
			matchNow.setMatchEnddate(enddate);
			matchNow.setMatchResult("-");
			matchNow.setUserProgress("");
			matchNow.setMatchUserProgress("");
			
			
			MatchNow matchOpponent = new MatchNow();
			matchOpponent.setUser(matchPschall.getMatchUser());
			matchOpponent.setPlanGrand(matchPschall.getMatchPlanGrand());
			matchOpponent.setPlanStatus("-");
			matchOpponent.setMatchUser(matchPschall.getUser());
			matchOpponent.setMatchPlanGrand(matchPschall.getPlanGrand());
			matchOpponent.setMatchPlanStatus("-");
			matchOpponent.setMatchStartdate(startdate);
			matchOpponent.setMatchEnddate(enddate);
			matchOpponent.setMatchResult("-");
			matchOpponent.setUserProgress("");
			matchOpponent.setMatchUserProgress("");
			
			PlanGrand planGrand = matchPschall.getMatchPlanGrand();
			planGrand.setGrandplanIsmatch(true);
			planGrandService.save(planGrand);
			em.clear();
			
			PlanGrand matchPlanGrand = matchPschall.getPlanGrand();
			matchPlanGrand.setGrandplanIsmatch(true);
			planGrandService.save(matchPlanGrand);
			em.clear();
			
			matchNowService.save(matchNow);
			em.clear();
			matchNowService.save(matchOpponent);
			em.clear();
			
			//매칭 넘어갔으니 pschall 삭제
			System.out.println("매칭 삭제 : " + pschallSeq);
			matchPschallService.deleteById(pschallSeq);
			
			resultMap.put("message", "success");
			status = HttpStatus.OK;
		} else {
			System.out.println("오류 발생!!");
			resultMap.put("message", "fail");
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<>(resultMap, status);
	}

	// 도전장 삭제. 받는사람 uid, 보낸사람 uid로 조회해서 삭제하는 기능
	@DeleteMapping("/pschall/{pschallSeq}")
	public ResponseEntity<Map<String, Object>> deleteMatchPschalls(@PathVariable("pschallSeq") Long pschallSeq) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if (matchPschallService.findByPschallSeq(pschallSeq).isPresent()) {
			matchPschallService.deleteById(pschallSeq);
			resultMap.put("message", "success");
			status = HttpStatus.OK;
		} else {
			resultMap.put("message", "fail");
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(resultMap, status);
	}

	// 랭킹정보를 가져오는 메소드
	@GetMapping("/ranking")
	public List<UserRankDto> ranking() {
		return userService.findByRank();
	}

	// onmatch가 true인 사람들의 리스트를 출력
	@GetMapping("/onmatch")
	public List<UserMatchDTO> getMatchUser() {
		return userService.findByUserOnmatch(true);
	}

	// 특정 유저의 UserMatchDTO 출력
	@GetMapping("/onmatch/{uid}")
	public UserMatchDTO getMatchUser(@PathVariable("uid") String uid) {
		return userService.findByUserUid(uid);
	}

	// 경쟁 참여설정 변경
	@PutMapping("/onmatch")
	public ResponseEntity<Map<String, Object>> updateOnmatch(@RequestBody Map<String, Object> vo) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		String userUid = vo.get("userUid").toString();
		Optional<User> user = userService.findById(userUid);
		if (user.isPresent()) {
			User updateuser = user.get();
			updateuser.setUserOnmatch(!updateuser.getUserOnmatch());
			userService.updateById(userUid, updateuser);

			resultMap.put("message", "success");
			status = HttpStatus.OK;
		} else {
			resultMap.put("message", "fail");
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<>(resultMap, status);
	}

	/* matchnow에 관련된 메소드들 */

	//모든 유저의 진행중인 경쟁전을 가져오는 getMatchList
	@GetMapping("/now")
	public List<MatchNowDto> getMatchList() {
		return matchNowService.findAll();
	}

	//특정유저의 진행중인 경쟁전을 가져오는 getMatchListByUid
	@GetMapping("/now/{uid}")
	public List<MatchNowDto> getMatchListByUid(@PathVariable("uid") String uid) {
		return matchNowService.findByUser_UserUid(uid);
	}

	//경쟁전 성사되었을때 그것을 등록하는 postMatchList
	@PostMapping("/now")
	public ResponseEntity<Map<String, Object>> postMatchList(@RequestBody MatchNowDto matchNowDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MatchNow matchNow = new MatchNow();
			matchNow.setUser(userService.findById(matchNowDto.getUserUid()).get());
			matchNow.setPlanGrand(planGrandService.findbyId(matchNowDto.getGrandplanSeq()).get());
			matchNow.setPlanStatus(matchNowDto.getPlanStatus());
			matchNow.setMatchUser(userService.findById(matchNowDto.getMatchUserUid()).get());
			matchNow.setMatchPlanGrand(planGrandService.findbyId(matchNowDto.getMatchGrandplanSeq()).get());
			matchNow.setMatchPlanStatus(matchNowDto.getMatchPlanStatus());
			matchNow.setMatchStartdate(matchNowDto.getMatchStartdate());
			matchNow.setMatchEnddate(matchNowDto.getMatchEnddate());
			matchNow.setMatchResult(matchNowDto.getMatchResult());
			matchNow.setUserProgress(matchNowDto.getUserProgress());
			matchNow.setMatchUserProgress(matchNowDto.getMatchUserProgress());

			matchNowService.save(matchNow);
			resultMap.put("message", "success");
			status = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "fail");
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(resultMap, status);
	}

	
	//특정 유저의 경쟁전에 참여하지 않은(+완료되지 않은) 대플랜 리스트를 가져오는 getAvailablePlan
	@GetMapping("/plan/{uid}")
	public List<PlanListDto> getAvailablePlan(@PathVariable("uid") String uid) {
		return planGrandService.getMatchPlan(uid);
	}
	
	
	//경쟁전 결과를 업데이트 해주는 resultUpdate
	@PutMapping("/result")
	public ResponseEntity<Map<String, Object>> resultUpdate(@RequestBody Map<String, Object> vo){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		Long nowSeq = Long.valueOf(vo.get("nowSeq").toString());
		String roulette = vo.get("roulette").toString();

		Optional<MatchNow> o = matchNowService.findById(nowSeq);
		if(o.isPresent()) {
			MatchNow matchNow = o.get();

			//result값에 따라 처리를 해준다.
			String result = matchNow.getMatchResult();
			User user = matchNow.getUser();
			User matchuser = matchNow.getMatchUser();
			UserHistory userHistory = new UserHistory();
			if(result.equals("W")) {
				user.setUserWin(user.getUserWin()+1);
				user.setUserPoint(user.getUserPoint()+100);
				user.setUserCpoint(user.getUserCpoint()+100);
				user.setUserExp(user.getUserExp()+100);
			}else if(result.equals("L")) {

				user.setUserLose(user.getUserLose()+1);
				user.setUserPoint(user.getUserPoint()-50);
				user.setUserCpoint(user.getUserCpoint()-50);
				user.setUserExp(user.getUserExp()+10);
			}else if(result.equals("D")) {

				user.setUserDraw(user.getUserDraw()+1);
				user.setUserPoint(user.getUserPoint()-50);
				user.setUserCpoint(user.getUserCpoint()-50);
				user.setUserExp(user.getUserExp()+10);
			}else if(result.equals("WW")) {

				user.setUserWinwin(user.getUserWinwin()+1);
				user.setUserPoint(user.getUserPoint()+150);
				user.setUserCpoint(user.getUserCpoint()+150);
				user.setUserExp(user.getUserExp()+150);
			}else {
				resultMap.put("message", "fail");
				status = HttpStatus.BAD_REQUEST;
				return new ResponseEntity<>(resultMap, status);
			}
			userHistory.setUser(user);
			userHistory.setMatchUserNickname(matchuser.getUserNickname());
			userHistory.setMatchUserExp(matchuser.getUserExp());
			userHistory.setMatchUserTitle(matchuser.getTitleSeq());
			userHistory.setMatchResult(result);

			if(roulette.equals("O"))
					user.setUserPoint(user.getUserPoint()+50);

			userHistoryService.save(userHistory);
			em.clear();

			userService.updateById(user.getUserUid(), user);
			em.clear();

			matchNowService.deleteById(nowSeq);
			em.clear();

			PlanGrand planGrand = matchNow.getPlanGrand();
			planGrand.setGrandplanIsmatch(false);

			planGrandService.save(planGrand);

			resultMap.put("message", "success");
			status = HttpStatus.OK;

		}else {
			resultMap.put("message", "fail");
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<>(resultMap, status);
	}

}
