package com.ssafy.hibernate.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.hibernate.dto.MatchPschallDto;
import com.ssafy.hibernate.entity.MatchPschall;

public interface MatchPschallService {

	public List<MatchPschall> findAll();
	public List<MatchPschallDto> findByUser_UserUid(String uid);
	public List<MatchPschallDto> findByMatchUser_UserUid(String uid);
	public MatchPschall save(MatchPschall matchPschall);
	public void deleteByUser_UserUidAndMatchUser_UserUid(String userUid, String matchUserUid);
	public Optional<MatchPschall> findByUser_UserUidAndMatchUser_UserUid(String userUid, String matchUserUid);
	public void deleteById(Long seq);
	public Optional<MatchPschall> findByPschallSeq(Long seq);
}
