package com.ssafy.hibernate.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.UserTitleRepository;
import com.ssafy.hibernate.dto.UserTitleDTO;
import com.ssafy.hibernate.entity.UserTitle;

@Service
public class UserTitleServiceImpl implements UserTitleService{

	@Autowired
	UserTitleRepository userTitleRepository;

	@Override
	@Transactional
	public List<UserTitleDTO> findAll() {
		ArrayList<UserTitleDTO> titles = new ArrayList<>();
		userTitleRepository.findAll().forEach(e->{
			UserTitleDTO userTitleDTO = new UserTitleDTO(e.getUserTitleSeq(),e.getUser().getUserUid(), e.getListTitle().getTitleSeq(), e.getListTitle().getTitleName());
			if(e.getUser().getTitleSeq() == e.getListTitle().getTitleSeq())
				userTitleDTO.setEquipped(true);
			titles.add(userTitleDTO);
		});

		return titles;
	}

	@Override
	public List<UserTitleDTO> findByUser_UserUid(String uid) {
		ArrayList<UserTitleDTO> titles = new ArrayList<>();
		userTitleRepository.findByUser_UserUid(uid).forEach(e->{
			UserTitleDTO userTitleDTO = new UserTitleDTO(e.getUserTitleSeq(),e.getUser().getUserUid(), e.getListTitle().getTitleSeq(), e.getListTitle().getTitleName());
			if(e.getUser().getTitleSeq() == e.getListTitle().getTitleSeq())
				userTitleDTO.setEquipped(true);
			titles.add(userTitleDTO);
		});
		Collections.sort(titles, new Comparator<UserTitleDTO>() {
			@Override
			public int compare(UserTitleDTO dto1, UserTitleDTO dto2) {
				if(dto1.getTitleSeq()>dto2.getTitleSeq())
					return 1;
				else if(dto1.getTitleSeq()<dto2.getTitleSeq())
					return -1;
				else
					return 0;
			}
		});
		return titles;
	}

	@Override
	public UserTitleDTO findByUser_UserUidAndListTitle_TitleSeq(String uid, Long titleSeq) {
		UserTitleDTO result = null;
		Optional<UserTitle> search =  userTitleRepository.findByUser_UserUidAndListTitle_TitleSeq(uid, titleSeq);
		if(search.isPresent()) {
			UserTitle e = search.get();
			result = new UserTitleDTO(e.getUserTitleSeq(),e.getUser().getUserUid(), e.getListTitle().getTitleSeq(), e.getListTitle().getTitleName());
		}
		return result;
	}

	@Override
	public UserTitle save(UserTitle userTitle) {
		userTitleRepository.save(userTitle);
		return userTitle;
	}

}
