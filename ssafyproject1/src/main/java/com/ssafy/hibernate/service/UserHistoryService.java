package com.ssafy.hibernate.service;

import java.util.List;

import com.ssafy.hibernate.dto.UserHistoryDTO;
import com.ssafy.hibernate.entity.UserHistory;

public interface UserHistoryService {
	public List<UserHistory> findByUser_UserUid(String uid);

	public List<UserHistoryDTO> findAll();

	public UserHistory save(UserHistory userhistory);
}
