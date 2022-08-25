package com.ssafy.hibernate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.MatchNowRepository;
import com.ssafy.hibernate.dto.MatchNowDto;
import com.ssafy.hibernate.entity.MatchNow;

@Service
public class MatchNowServiceImpl implements MatchNowService{
	@Autowired
	MatchNowRepository matchNowRepository;

	@Override
	public List<MatchNowDto> findAll() {
		ArrayList<MatchNowDto> matches = new ArrayList<>();
		matchNowRepository.findAll().forEach(e->{
			MatchNowDto matchNowDto = new MatchNowDto();
			matchNowDto.setNowSeq(e.getNowSeq());
			matchNowDto.setGrandplanSeq(e.getNowSeq());
			matchNowDto.setUserUid(e.getUser().getUserUid());
			matchNowDto.setUserNickname(e.getUser().getUserNickname());
			matchNowDto.setGrandplanSeq(e.getPlanGrand().getGrandplanSeq());
			matchNowDto.setGrandplanTitle(e.getPlanGrand().getGrandplanTitle());
			matchNowDto.setPlanStatus(e.getPlanStatus());
			matchNowDto.setMatchUserUid(e.getMatchUser().getUserUid());
			matchNowDto.setMatchUserNickname(e.getMatchUser().getUserNickname());
			matchNowDto.setMatchGrandplanSeq(e.getMatchPlanGrand().getGrandplanSeq());
			matchNowDto.setMatchPlanStatus(e.getMatchPlanStatus());
			matchNowDto.setMatchStartdate(e.getMatchStartdate());
			matchNowDto.setMatchEnddate(e.getMatchEnddate());
			matchNowDto.setMatchResult(e.getMatchResult());
			matchNowDto.setUserProgress(e.getUserProgress());
			matchNowDto.setMatchUserProgress(e.getMatchUserProgress());

			matches.add(matchNowDto);
		});


		return matches;

	}

	@Override
	public List<MatchNowDto> findByUser_UserUid(String uid) {
		ArrayList<MatchNowDto> matches = new ArrayList<>();
		matchNowRepository.findByUser_UserUid(uid).forEach(e->{
			MatchNowDto matchNowDto = new MatchNowDto();
			matchNowDto.setNowSeq(e.getNowSeq());
			matchNowDto.setGrandplanSeq(e.getNowSeq());
			matchNowDto.setUserUid(e.getUser().getUserUid());
			matchNowDto.setUserNickname(e.getUser().getUserNickname());
			matchNowDto.setGrandplanSeq(e.getPlanGrand().getGrandplanSeq());
			matchNowDto.setGrandplanTitle(e.getPlanGrand().getGrandplanTitle());
			matchNowDto.setPlanStatus(e.getPlanStatus());
			matchNowDto.setMatchUserUid(e.getMatchUser().getUserUid());
			matchNowDto.setMatchUserNickname(e.getMatchUser().getUserNickname());
			matchNowDto.setMatchGrandplanSeq(e.getMatchPlanGrand().getGrandplanSeq());
			matchNowDto.setMatchPlanStatus(e.getMatchPlanStatus());
			matchNowDto.setMatchStartdate(e.getMatchStartdate());
			matchNowDto.setMatchEnddate(e.getMatchEnddate());
			matchNowDto.setMatchResult(e.getMatchResult());
			matchNowDto.setUserProgress(e.getUserProgress());
			matchNowDto.setMatchUserProgress(e.getMatchUserProgress());

			matches.add(matchNowDto);
		});


		return matches;
	}

	@Override
	public MatchNow save(MatchNow matchNow) {
		return matchNowRepository.save(matchNow);
	}

	@Override
	public Optional<MatchNow> findById(Long seq) {
		return matchNowRepository.findById(seq);
	}

	@Override
	public void deleteById(Long seq) {
		matchNowRepository.deleteById(seq);
	}
}
