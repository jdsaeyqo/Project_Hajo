package com.ssafy.hibernate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.ListTitleRepository;
import com.ssafy.hibernate.Repository.MatchPbchallRepository;
import com.ssafy.hibernate.dto.MatchPbchallDto;
import com.ssafy.hibernate.entity.MatchPbchall;
import com.ssafy.hibernate.entity.PlanGrand;
import com.ssafy.hibernate.entity.User;

@Service
public class MatchPbchallServiceImpl implements MatchPbchallService{

	@Autowired
	MatchPbchallRepository matchPbchallRepository;
	
	@Autowired
	ListTitleRepository listTitleRepository;

	@Override
	public List<MatchPbchallDto> findAll() {
		// TODO Auto-generated method stub
		ArrayList<MatchPbchallDto> list = new ArrayList<>();
		matchPbchallRepository.findAll().forEach(e->{
			User u = e.getUser();
			PlanGrand p = e.getPlanGrand();
			MatchPbchallDto m = new MatchPbchallDto(u,p);
			m.setPbchallSeq(e.getPbchallSeq());
			m.setMatchUserTitle(listTitleRepository.findById(u.getTitleSeq()).get().getTitleName());
			
			list.add(m);
		});
		
		return list;
	}

	@Override
	public List<MatchPbchallDto> findByUser_UserUid(String uid) {
		ArrayList<MatchPbchallDto> list = new ArrayList<>();
		matchPbchallRepository.findByUser_UserUid(uid).forEach(e->{
			User u = e.getUser();
			PlanGrand p = e.getPlanGrand();
			MatchPbchallDto m = new MatchPbchallDto(u,p);
			m.setPbchallSeq(e.getPbchallSeq());
			m.setMatchUserTitle(listTitleRepository.findById(u.getTitleSeq()).get().getTitleName());
			
			list.add(m);
		});
		return list;
	}

	@Override
	public MatchPbchall save(MatchPbchall matchPbchall) {
		return matchPbchallRepository.save(matchPbchall);
	}

	@Override
	public Optional<MatchPbchall> findByUser_UserUidAndPlanGrand_GrandplanSeq(String uid, Long grandplanSeq) {
		return matchPbchallRepository.findByUser_UserUidAndPlanGrand_GrandplanSeq(uid, grandplanSeq);
	}

	@Override
	public void deleteByPlanGrand_GrandplanSeq(Long grandplanSeq) {
		matchPbchallRepository.deleteByPlanGrand_GrandplanSeq(grandplanSeq);

	}

	@Override
	public Optional<MatchPbchall> findByPbchallSeq(Long pbchallSeq) {
		return matchPbchallRepository.findById(pbchallSeq);
	}

	@Override
	public void deleteById(Long pbchallSeq) {
		matchPbchallRepository.deleteById(pbchallSeq);
	}

}
