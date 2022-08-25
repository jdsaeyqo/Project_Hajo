package com.ssafy.hibernate.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.hibernate.entity.ListTitle;

public interface ListTitleService {
	public List<ListTitle> findAll();

	public ListTitle save(ListTitle listTitle);

	Optional<ListTitle> findById(Long titleSeq);
}
