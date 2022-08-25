package com.ssafy.hibernate.service;

import java.util.Calendar;
import java.util.List;

import com.ssafy.hibernate.dto.HomeDto;

public interface HomeService {
	public List<HomeDto> getHome(String uid, Calendar date);
}
