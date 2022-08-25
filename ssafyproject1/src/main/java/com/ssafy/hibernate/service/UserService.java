package com.ssafy.hibernate.service;


import java.util.List;
import java.util.Optional;

import com.ssafy.hibernate.dto.UserMatchDTO;
import com.ssafy.hibernate.dto.UserRankDto;
import com.ssafy.hibernate.entity.User;

public interface UserService {
	public List<User> findAll();

	public Optional<User> findById(String useruid);

	public void deleteById(String useruid);

	public User save(User user);

	public void updateById(String useruid, User user);

	public Optional<User> findByUserEmail(String email);

	public List<UserMatchDTO> findByUserOnmatch(boolean userOnmatch);

	public UserMatchDTO findByUserUid(String useruid);

	public List<UserRankDto> findByRank();
}
