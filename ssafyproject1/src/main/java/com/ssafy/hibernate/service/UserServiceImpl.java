 package com.ssafy.hibernate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.ListTitleRepository;
import com.ssafy.hibernate.Repository.UserRepository;
import com.ssafy.hibernate.dto.UserMatchDTO;
import com.ssafy.hibernate.dto.UserRankDto;
import com.ssafy.hibernate.entity.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ListTitleRepository listTitleRepository;

	@Override
	@Transactional
	public List<User> findAll(){
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(e->users.add(e));
		return users;
	}

	@Override
	@Transactional
	public Optional<User> findById(String useruid){
		Optional<User> user = userRepository.findById(useruid);
		return user;
	}

	@Override
	@Transactional
	public void deleteById(String useruid) {
		userRepository.deleteById(useruid);
	}

	@Override
	@Transactional
	public User save(User user) {
		userRepository.save(user);
		return user;
	}

	@Override
	@Transactional
	public void updateById(String useruid, User user) {
		Optional<User> e = userRepository.findById(useruid);

		if(e.isPresent()) {
			e.get().setUserEmail(user.getUserEmail());
			e.get().setUserNickname(user.getUserNickname());
			e.get().setUserImg(user.getUserImg());
			e.get().setUserPoint(user.getUserPoint());
			userRepository.save(user);
		}
	}

	@Override
	@Transactional
	public Optional<User> findByUserEmail(String email){
		return userRepository.findByUserEmail(email);
	}

	@Override
	public List<UserMatchDTO> findByUserOnmatch(boolean userOnmatch) {
		List<User> matchuserlist = userRepository.findByUserOnmatch(true);
		List<UserMatchDTO> resultlist = new ArrayList<>();

		for(User u : matchuserlist) {
			UserMatchDTO userMatchDTO = new UserMatchDTO(u);
			userMatchDTO.setTitleName(listTitleRepository.findById(u.getTitleSeq()).get().getTitleName());
			resultlist.add(userMatchDTO);
		}

		return resultlist;
	}

	@Override
	public List<UserRankDto> findByRank() {
		// TODO Auto-generated method stub
		return userRepository.findByRank();
	}

	@Override
	public UserMatchDTO findByUserUid(String useruid) {

		System.out.println("\n\n\n\n\nuid : "+useruid+"\n\n\n\n\n\n\n");
		Optional<User> user = userRepository.findById(useruid);
		if(user.isPresent()) {
			UserMatchDTO userMatchDTO = new UserMatchDTO(user.get());
			userMatchDTO.setTitleName(listTitleRepository.findById(user.get().getTitleSeq()).get().getTitleName());
			return userMatchDTO;
		}else
			return null;
	}



}
