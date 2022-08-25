package com.ssafy.hibernate.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.hibernate.dto.MatchNowDto;
import com.ssafy.hibernate.entity.MatchNow;

public interface MatchNowService {
	public List<MatchNowDto> findAll();

	public List<MatchNowDto> findByUser_UserUid(String uid);

	public Optional<MatchNow> findById(Long seq);

	public MatchNow save(MatchNow matchNow);

	public void deleteById(Long seq);

}
