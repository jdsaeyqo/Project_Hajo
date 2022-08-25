package com.ssafy.hibernate.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hibernate.Repository.PlanTaskRepository;
import com.ssafy.hibernate.dto.PlanTaskBydate;
import com.ssafy.hibernate.dto.PlanTaskDto;
import com.ssafy.hibernate.entity.PlanSmall;
import com.ssafy.hibernate.entity.PlanTask;
import com.ssafy.hibernate.service.PlanSmallService;
import com.ssafy.hibernate.service.PlanTaskService;

@RestController
@RequestMapping("/plantasks")
public class PlanTaskController {
	@Autowired
	PlanSmallService pSS;

	@Autowired
	PlanTaskService pTS;

	@Autowired
	PlanTaskRepository pTR;

	// http://아이피/plantasks
	// 태스크 전체 조회 (get)
	@GetMapping("")
	public List<PlanTask> getPlanTask(){
		return pTS.findAll();
	}

	// http://아이피/plantasks/{태스크시퀀스}
	// 태스크 시퀀스로 하나만 골라서 조회 (get)
	@GetMapping("/{seq}")
	public PlanTaskDto getPlanTask(@PathVariable("seq") Long seq) {
		PlanTaskDto plantaskdto = pTS.findbyId(seq);
		return plantaskdto;
	}

	// http://아이피/plantasks
	// 태스크 추가 (post)
	@PostMapping("")
	public int addPlanTask(@RequestBody PlanTaskDto plantaskdto) {

		// 소플랜 안의 태스크 수를 갱신해준다
		PlanSmall plansmall = pSS.findbyId(plantaskdto.getPlansmallSeq()).get();
		plansmall.setSmallplanTttask(plansmall.getSmallplanTttask()+1);
		pSS.updateById(plantaskdto.getPlansmallSeq(), plansmall);

		return pTS.save(plantaskdto, plansmall);
	}

	// http://아이피/plantasks
	// 태스크 수정 (put)
	@PutMapping("")
	public int updatePlanGrand(@RequestBody PlanTaskDto plantaskdto) {
		Optional<PlanTask> plantaskTemp = pTR.findById(plantaskdto.getTaskSeq());

		if(plantaskTemp.isPresent()) {
			PlanTask plantask = pTR.findById(plantaskdto.getTaskSeq()).get();

			// 단점... 빈칸으로 바꾸는건 어떻게하지? 예를 들어 알람 시간을 아예 삭제해버리고 싶거나...
			// 이래서 변경하는 것에 대한 타입을 받아야 했나봄
			// 타입을 안 받고 처리하는 방법 중 하나 --> 메모/알람/이미지 삭제 버튼을 따로 두기
			// 또 하나 --> 컬럼별로 불가능한 값을 정해두고, 그 값으로 들어왔으면 null로 바꾸기

			if(plantaskdto.getTitle()!=null) {
				// 제목이 null이 아닐 경우, 그것을 수정했다는 의미이므로
				plantask.setTaskTitle(plantaskdto.getTitle());
			}
			if(plantaskdto.getMemo()!=null) {
				// 메모가 null이 아닐 경우, 그것을 수정했다는 의미이므로
				plantask.setTaskMemo(plantaskdto.getTitle());
			}
			if(plantaskdto.getImg()!=null) {
				// 이미지가 null이 아닐 경우, 그것을 수정했다는 의미이므로
				plantask.setTaskImg(plantaskdto.getImg());
			}
			if(plantaskdto.getAlramtime()!=null) {
				// 알람 시간이 null이 아닐 경우, 그것을 수정했다는 의미이므로
				plantask.setTaskAlarmtime(plantaskdto.getAlramtime());
			}

			return pTS.updateById(plantaskdto.getTaskSeq(), plantask);

		} else {
			return 3;
		}

	}

	// http://아이피/plantasks/{태스크시퀀스}
	// 태스크 삭제 (delete)
	@DeleteMapping("/{seq}")
	public int deletePlanTask(@PathVariable("seq") Long seq) {
		return pTS.deleteById(seq);
	}

	// http://아이피/plantasks/check/{태스크시퀀스}
	// 태스크 달성 완료 체크
	@PutMapping("/check/{seq}")
	public Map<String, String> checkPlanTask(@PathVariable("seq") Long seq) {
		Map<String, String> vo = new HashMap<>();
		vo.put("msg",pTS.checkPlanTask(seq));
		return vo;
	}
	
	// http://아이피/plantasks/bydate
	@PostMapping("/bydate")
	public PlanTaskBydate getPlanBydate(@RequestBody Map<String, String> vo) {
		return pTS.getPlanBydate(vo);
	}
	
	
	
}
