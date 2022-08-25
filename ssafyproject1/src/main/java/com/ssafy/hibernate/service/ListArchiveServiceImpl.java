package com.ssafy.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.ListArchiveRepository;
import com.ssafy.hibernate.Repository.ListTitleRepository;
import com.ssafy.hibernate.dto.ListArchiveDto;
import com.ssafy.hibernate.entity.ListArchive;

@Service
public class ListArchiveServiceImpl implements ListArchiveService{

	@Autowired
	ListArchiveRepository listArchiveRepository;
	
	@Autowired
	ListTitleRepository listTitleRepository;
	
	@Override
	public List<ListArchiveDto> findAll() {
		ArrayList<ListArchiveDto> list = new ArrayList<>();
		
		listArchiveRepository.findAll().forEach(e->{
			ListArchiveDto l = new ListArchiveDto(e);
			if(e.getArchiveRewardtype().equals("title")) {
				l.setRewardName(listTitleRepository.findById(e.getArchiveRewardseq()).get().getTitleName());
			}
			list.add(l);
		});
		
		
		return list;
	}
	
}
