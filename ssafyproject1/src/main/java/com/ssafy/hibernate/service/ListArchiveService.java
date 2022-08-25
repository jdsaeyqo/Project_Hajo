package com.ssafy.hibernate.service;

import java.util.List;

import com.ssafy.hibernate.dto.ListArchiveDto;
import com.ssafy.hibernate.entity.ListArchive;

public interface ListArchiveService {
	public List<ListArchiveDto> findAll();
}
