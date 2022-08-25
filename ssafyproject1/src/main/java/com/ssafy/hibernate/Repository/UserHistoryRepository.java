package com.ssafy.hibernate.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.UserHistory;


@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long>{
	List<UserHistory> findByUser_UserUid(String uid);

	@Override
	@EntityGraph("UserHistoryWithUser")
	List<UserHistory> findAll();
}
