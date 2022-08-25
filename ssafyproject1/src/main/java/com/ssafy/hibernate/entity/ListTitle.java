package com.ssafy.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "LIST_TITLE_TB")
@Data
public class ListTitle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TITLE_SEQ")
	private Long titleSeq;

	@Column(name="TITLE_NAME")
	private String titleName;
}
