package com.ssafy.hibernate.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.UserBlock;

@Repository
@Transactional
public interface UserBlockRepository extends JpaRepository<UserBlock, Long>{

	@EntityGraph("UserBlockWithUserAndUser")
	List<UserBlock> findByUser_UserUid(String uid);
	
	@EntityGraph("UserBlockWithUserAndUser")
	Optional<UserBlock> findByUser_UserUidAndBlockUser_UserUid(String userUid, String blockUid);
	
	@EntityGraph("UserBlockWithUserAndUser")
	void deleteByUser_UserUidAndBlockUser_UserUid(String userUid, String blockUid);
	
}
