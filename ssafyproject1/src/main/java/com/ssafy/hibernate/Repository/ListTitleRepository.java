package com.ssafy.hibernate.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.hibernate.entity.ListTitle;

@Repository
public interface ListTitleRepository extends JpaRepository<ListTitle, Long>{

}
