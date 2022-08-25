package com.ssafy.hibernate.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.hibernate.entity.UserBlock;

public interface UserBlockService {
	List<String> findByUserUid(String userUid);
	Optional<UserBlock> findByUser_UserUidAndBlockUser_UserUid(String userUid, String blockUid);
	void deleteByUser_UserUidAndBlockUser_UserUid(String userUid, String blockUid);
	UserBlock save(UserBlock userBlock);
}
