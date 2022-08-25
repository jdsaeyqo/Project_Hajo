package com.ssafy.hibernate.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.dto.UserRankDto;
import com.ssafy.hibernate.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	Optional<User> findByUserEmail(String email);
	List<User> findByUserOnmatch(boolean userOnmatch);
	@Query(name="user_rank", nativeQuery = true)
	public List<UserRankDto> findByRank();
}
