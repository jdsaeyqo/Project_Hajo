package com.ssafy.hibernate.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.ListTitleRepository;
import com.ssafy.hibernate.Repository.UserHistoryRepository;
import com.ssafy.hibernate.Repository.UserRepository;
import com.ssafy.hibernate.dto.UserHistoryDTO;
import com.ssafy.hibernate.entity.User;
import com.ssafy.hibernate.entity.UserHistory;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {

	@Autowired
	private UserHistoryRepository userHistoryRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ListTitleRepository listTitleRepository;

	@Override
	@Transactional
	public List<UserHistoryDTO> findAll() {
		List<UserHistoryDTO> histories = userHistoryRepository.findAll().stream().map(
				m->{
					UserHistoryDTO userHistoryDTO = new UserHistoryDTO();
					userHistoryDTO.setHistorySeq(m.getHistorySeq());
					userHistoryDTO.setUserUid(m.getUser().getUserUid());
					userHistoryDTO.setMatchUserNickname(m.getMatchUserNickname());
					userHistoryDTO.setMatchUserExp(m.getMatchUserExp());
					userHistoryDTO.setMatchUserTitleName(listTitleRepository.findById(m.getMatchUserTitle()).get().getTitleName());
					userHistoryDTO.setMatchResult(m.getMatchResult());

					return userHistoryDTO;
				}
		).collect(Collectors.toList());

		return histories;
	}

	@Override
	public UserHistory save(UserHistory userhistory) {
		// userhistory에 있는 userUid로 user정보를 가져와야한다.
		Optional<User> user = userRepository.findById(userhistory.getUser().getUserUid());

		// user정보를 가져와서 userhistory의 user에 set한다.
		userhistory.setUser(user.get());
		userHistoryRepository.save(userhistory);
		return userhistory;
	}

	@Override
	public List<UserHistory> findByUser_UserUid(String uid) {
		return userHistoryRepository.findByUser_UserUid(uid);
	}

}
