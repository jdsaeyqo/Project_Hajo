package com.ssafy.hibernate.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.UserArchive;

@Repository
@Transactional
public interface UserArchiveRepository extends JpaRepository<UserArchive, Long>{
	@EntityGraph("UserArchiveWithUserAndListArchive")
	public List<UserArchive> findByUser_UserUid(String userUid);
}
