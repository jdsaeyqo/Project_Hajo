package com.ssafy.hibernate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.ListTitleRepository;
import com.ssafy.hibernate.Repository.MatchPschallRepository;
import com.ssafy.hibernate.dto.MatchPschallDto;
import com.ssafy.hibernate.entity.MatchPschall;

@Service
public class MatchPschallServiceImpl implements MatchPschallService {

	@Autowired
	MatchPschallRepository matchPschallRepository;

	@Autowired
	ListTitleRepository listTitleRepository;

	@Override
	public List<MatchPschall> findAll() {
		// TODO Auto-generated method stub
		return matchPschallRepository.findAll();
	}

	@Override
	public List<MatchPschallDto> findByUser_UserUid(String uid) {
		ArrayList<MatchPschallDto> pschalllist = new ArrayList<>();

		matchPschallRepository.findByUser_UserUid(uid).forEach(e -> {
			MatchPschallDto matchPschallDto = new MatchPschallDto();
			matchPschallDto.setPschallSeq(e.getPschallSeq());
			matchPschallDto.setUserUid(e.getUser().getUserUid());
			matchPschallDto.setMatchUserUid(e.getMatchUser().getUserUid());
			matchPschallDto.setMatchUserNickname(e.getMatchUser().getUserNickname());
			matchPschallDto.setMatchUserExp(e.getMatchUser().getUserExp());
			matchPschallDto.setMatchUserTitle(
					listTitleRepository.findById(e.getMatchUser().getTitleSeq()).get().getTitleName());
			matchPschallDto.setGrandplanTitle(e.getPlanGrand().getGrandplanTitle());
			matchPschallDto.setMatchGrandplanTitle(e.getMatchPlanGrand().getGrandplanTitle());
			matchPschallDto.setMatchPeriod(e.getMatchPeriod());

			System.out.println("\n\n\n\n\n\n\n" + matchPschallDto + "\n\n\n\n\n\n");

			pschalllist.add(matchPschallDto);
		});

		return pschalllist;
	}

	@Override
	@Transactional
	public MatchPschall save(MatchPschall matchPschall) {
		matchPschallRepository.save(matchPschall);
		return matchPschall;
	}

	@Override
	@Transactional
	public void deleteByUser_UserUidAndMatchUser_UserUid(String userUid, String matchUserUid) {
		matchPschallRepository.deleteByUser_UserUidAndMatchUser_UserUid(userUid, matchUserUid);
	}

	@Override
	public Optional<MatchPschall> findByUser_UserUidAndMatchUser_UserUid(String userUid, String matchUserUid) {
		return matchPschallRepository.findByUser_UserUidAndMatchUser_UserUid(userUid, matchUserUid);
	}

	@Override
	public List<MatchPschallDto> findByMatchUser_UserUid(String uid) {
		ArrayList<MatchPschallDto> pschalllist = new ArrayList<>();

		matchPschallRepository.findByMatchUser_UserUid(uid).forEach(e -> {
			MatchPschallDto matchPschallDto = new MatchPschallDto();
			matchPschallDto.setPschallSeq(e.getPschallSeq());
			matchPschallDto.setUserUid(e.getMatchUser().getUserUid());
			matchPschallDto.setMatchUserUid(e.getUser().getUserUid());
			matchPschallDto.setMatchUserNickname(e.getUser().getUserNickname());
			matchPschallDto.setMatchUserExp(e.getUser().getUserExp());
			matchPschallDto.setMatchUserTitle(
					listTitleRepository.findById(e.getUser().getTitleSeq()).get().getTitleName());
			matchPschallDto.setGrandplanTitle(e.getMatchPlanGrand().getGrandplanTitle());
			matchPschallDto.setMatchGrandplanTitle(e.getPlanGrand().getGrandplanTitle());
			matchPschallDto.setMatchPeriod(e.getMatchPeriod());

			System.out.println("\n\n\n\n\n\n\n" + matchPschallDto + "\n\n\n\n\n\n");

			pschalllist.add(matchPschallDto);
		});

		return pschalllist;
	}

	@Override
	public void deleteById(Long seq) {
		matchPschallRepository.deleteByPschallSeq(seq);

	}

	@Override
	public Optional<MatchPschall> findByPschallSeq(Long seq) {
		return matchPschallRepository.findByPschallSeq(seq);
	}

}
