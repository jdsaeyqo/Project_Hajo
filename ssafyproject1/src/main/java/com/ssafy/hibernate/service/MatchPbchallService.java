package com.ssafy.hibernate.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.hibernate.dto.MatchPbchallDto;
import com.ssafy.hibernate.entity.MatchPbchall;

public interface MatchPbchallService {
	public List<MatchPbchallDto> findAll();
	public List<MatchPbchallDto> findByUser_UserUid(String uid);
	public MatchPbchall save(MatchPbchall matchPbchall);
	public Optional<MatchPbchall> findByUser_UserUidAndPlanGrand_GrandplanSeq(String uid, Long grandplanSeq);
	public Optional<MatchPbchall> findByPbchallSeq(Long pbchallSeq);
	public void deleteByPlanGrand_GrandplanSeq(Long grandplanSeq);
	public void deleteById(Long pbchallSeq);
}
