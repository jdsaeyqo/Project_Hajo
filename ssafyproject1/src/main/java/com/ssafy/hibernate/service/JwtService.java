package com.ssafy.hibernate.service;

import java.util.Map;


public interface JwtService {

	public String createJwt(String userUid);
	public String getJwt();
	public String getUserUid(String jwt) throws Exception;
	public boolean isUsable(String jwt);
}
