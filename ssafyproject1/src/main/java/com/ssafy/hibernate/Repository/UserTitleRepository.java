package com.ssafy.hibernate.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.UserTitle;

@Repository
public interface UserTitleRepository extends JpaRepository<UserTitle, Long>{
	@EntityGraph("UserTitleWithUserAndListTitle")
	List<UserTitle> findByUser_UserUid(String uid);

	@Override
	@EntityGraph("UserTitleWithUserAndListTitle")
	List<UserTitle> findAll();

	@EntityGraph("UserTitleWithUserAndListTitle")
	Optional<UserTitle> findByUser_UserUidAndListTitle_TitleSeq(String uid, Long titleSeq);
}
