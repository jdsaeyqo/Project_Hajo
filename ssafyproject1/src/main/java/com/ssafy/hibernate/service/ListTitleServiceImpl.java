package com.ssafy.hibernate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.ListTitleRepository;
import com.ssafy.hibernate.entity.ListTitle;

@Service
public class ListTitleServiceImpl implements ListTitleService{

	@Autowired
	ListTitleRepository listTitleRepository;

	@Override
	@Transactional
	public List<ListTitle> findAll() {
		List<ListTitle> titles = new ArrayList<>();
		listTitleRepository.findAll().forEach(e->titles.add(e));
		return titles;
	}

	@Override
	@Transactional
	public ListTitle save(ListTitle listTitle) {
		listTitleRepository.save(listTitle);
		return listTitle;
	}

	@Override
	@Transactional
	public Optional<ListTitle> findById(Long titleSeq){
		Optional<ListTitle> listTitle = listTitleRepository.findById(titleSeq);
		return listTitle;
	}

}
