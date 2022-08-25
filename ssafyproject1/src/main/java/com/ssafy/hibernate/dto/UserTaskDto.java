package com.ssafy.hibernate.dto;

import com.ssafy.hibernate.entity.User;

import lombok.Data;

@Data
public class UserTaskDto {
	//user Total task
	private int userTttask;
	//user Total done task
	private int userTdtask;
	//user Daily task
	private int userDltask;
	//user Daily done task
	private int userDdtask;
	
	public UserTaskDto(User u) {
		this.userTttask=u.getUserTttask();
		this.userTdtask=u.getUserTdtask();
		this.userDltask=u.getUserDltask();
		this.userDdtask=u.getUserDdtask();
	}
}
