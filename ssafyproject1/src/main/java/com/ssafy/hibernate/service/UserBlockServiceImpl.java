package com.ssafy.hibernate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.UserBlockRepository;
import com.ssafy.hibernate.entity.UserBlock;

@Service
public class UserBlockServiceImpl implements UserBlockService{

	@Autowired
	UserBlockRepository userBlockRepository;
	
	@Override
	public List<String> findByUserUid(String userUid) {
		ArrayList<String> result = new ArrayList<>();
		userBlockRepository.findByUser_UserUid(userUid).forEach(e->{
			result.add(e.getBlockUser().getUserUid());
		});

		return result;
	}

	@Override
	public UserBlock save(UserBlock userBlock) {
		return userBlockRepository.save(userBlock);
	}

	@Override
	public Optional<UserBlock> findByUser_UserUidAndBlockUser_UserUid(String userUid, String blockUid) {
		return userBlockRepository.findByUser_UserUidAndBlockUser_UserUid(userUid,blockUid);
	}

	@Override
	public void deleteByUser_UserUidAndBlockUser_UserUid(String userUid, String blockUid) {
		userBlockRepository.deleteByUser_UserUidAndBlockUser_UserUid(userUid, blockUid);
		
	}

}
