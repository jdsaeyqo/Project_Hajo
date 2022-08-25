package com.ssafy.hibernate.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hibernate.Repository.UserRepository;
import com.ssafy.hibernate.dto.UserHistoryDTO;
import com.ssafy.hibernate.entity.UserHistory;
import com.ssafy.hibernate.service.ListTitleService;
import com.ssafy.hibernate.service.UserHistoryService;

@RestController
@RequestMapping("/histories")
public class UserHistoryController {

	@Autowired
	UserHistoryService userHistoryService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ListTitleService listTitleService;

	@GetMapping("")
	public List<UserHistoryDTO> getHistories(){
		return userHistoryService.findAll();
	}

	@PostMapping("")
	public UserHistory addUserHistory(@RequestBody UserHistory userhistory) {

		//저장
		userHistoryService.save(userhistory);
		return userhistory;
	}

	@GetMapping("/{uid}")
	public List<UserHistoryDTO> getHistoriesbyuid(@PathVariable("uid") String uid){
		List<UserHistoryDTO> histories = userHistoryService.findByUser_UserUid(uid).stream().map(
				m->{
					UserHistoryDTO userHistoryDTO = new UserHistoryDTO();
					userHistoryDTO.setHistorySeq(m.getHistorySeq());
					userHistoryDTO.setUserUid(m.getUser().getUserUid());
					userHistoryDTO.setMatchUserNickname(m.getMatchUserNickname());
					userHistoryDTO.setMatchUserExp(m.getMatchUserExp());
					userHistoryDTO.setMatchUserTitleName(listTitleService.findById(m.getMatchUserTitle()).get().getTitleName());
					userHistoryDTO.setMatchResult(m.getMatchResult());

					return userHistoryDTO;
				}
		).collect(Collectors.toList());


		return histories;
	}
}
