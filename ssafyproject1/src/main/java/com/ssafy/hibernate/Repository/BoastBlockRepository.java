package com.ssafy.hibernate.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.BoastBlock;
@Repository
@Transactional
public interface BoastBlockRepository extends JpaRepository<BoastBlock, Long>{
	

	

}
