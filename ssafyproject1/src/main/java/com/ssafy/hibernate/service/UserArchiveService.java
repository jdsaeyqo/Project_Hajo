package com.ssafy.hibernate.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.hibernate.dto.UserArchiveDto;
import com.ssafy.hibernate.entity.UserArchive;

public interface UserArchiveService {
	List<UserArchiveDto> findByUserUid(String userUid);
	
	void setArchives(String userUid);

	Optional<UserArchive> findbyId(Long userArchiveSeq);
	
	UserArchive save(UserArchive userArchive);
}
