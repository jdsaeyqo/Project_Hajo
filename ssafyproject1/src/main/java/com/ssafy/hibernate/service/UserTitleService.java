package com.ssafy.hibernate.service;

import java.util.List;

import com.ssafy.hibernate.dto.UserTitleDTO;
import com.ssafy.hibernate.entity.UserTitle;

public interface UserTitleService {
	public List<UserTitleDTO> findAll();
	public List<UserTitleDTO> findByUser_UserUid(String uid);
	UserTitleDTO findByUser_UserUidAndListTitle_TitleSeq(String uid, Long titleSeq);
	public UserTitle save(UserTitle userTitle);
}
